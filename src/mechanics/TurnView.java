package mechanics;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import setup.ParseNumberInputs;
import setup.SetupController;
import players.Player;
import players.PlayerList;
import elements.board.Board;
import elements.board.Tile;
import elements.cards.*;
import elements.board.WaterLevel;
import mechanics.actions.*;

/**
 * TurnView
 * 	View to display turn events
 * @author Catherine Waechter
 * @version 1.3
 * 	Added pilot reset, and fixed null card issue
 * 
 * Date Created:  03/12/20
 * Last Modified: 12/12/20
 */
public class TurnView {

	private static TurnView turnView = null;
	private TurnController controller;
	private Player currentPlayer;
	private Scanner user;
	
	/**
	 * getInstance
	 * @return turnView - singleton instance of TurnView
	 */
	public static TurnView getInstance() {
		if (turnView==null) {
			turnView =new TurnView();
		}
		return turnView;
	}
	
	/**
	 * run the turn view
	 * 
	 * @param player
	 * @param user
	 * @throws IOException 
	 */
	public void run(Player player, Scanner user) throws IOException  {
		System.out.println(Board.getInstance());

		this.user = user;
	
		currentPlayer = player;
		System.out.println("It's your turn " + player + "!");
		System.out.println("Your cards are : " + controller.getHand(player));
		System.out.println("Your " + player.getPawn() + " is on " + player.getPawn().getTile());
		int actionCount = 3;
		int actionReturn;
		while(actionCount > 0) {
			actionReturn = selectAction(user);
			if(actionReturn == 1) {
				actionCount--;
			}
			else if(actionReturn == -1) {
				actionCount = 0;
			}
		}
		
		// TODO should still be able to play cards in here (especially when hand becomes full!)
		for(int i = 0; i<2; i++) {
			Card cardDrawn = controller.drawTreasureCard(player);
			System.out.println(player + " drew a " + cardDrawn + " card.");
			if(controller.handSize(player) == 6) {
				doDiscard(user, currentPlayer);
			}
		}
		
		Set<Card> cardsDrawn = controller.drawFloodCards();
		System.out.println("Flood cards drawn: " + cardsDrawn);
		// TODO could be nice to print new status of relevant tiles, but not completely necessary since board is printed
		// TODO draw one card at a time to print out each card when it's drawn instead of all together
		
		System.out.println("Your cards are : " + controller.getHand(player));
		
		controller.pilotReset(player); // checks if the player is a pilot, so can be called regardless
	}
	
	public void doSwim(Player player, ArrayList<Tile> possibleTiles) throws IOException {
		System.out.println("The tile " + player + " was on sank!");
		System.out.println("Where would you like to swim? ");
		ActionView.printTileList(possibleTiles);
		
		int userNum = ParseNumberInputs.main(user, 1, possibleTiles.size());
		controller.move(player, possibleTiles.get(userNum-1));
		
	}
	
	private void doDiscard(Scanner user, Player player) throws IOException {
		System.out.println(player + "'s hand is full! Please select a card to discard: ");
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(controller.getHandCards(player));
		ActionView.printCardList(cards);
		
		int cardNum = ParseNumberInputs.main(user, 1, 6);
		
		controller.discard(player, cards.get(cardNum-1));
	}
	
	/**
	 * selectAction
	 * 	Allow user to select desired action. calls each action
	 * @param user
	 * @return whether an action was used (0 - no, 1 - yes, -1 - cancel further actions)
	 * @throws IOException
	 */
	private int selectAction(Scanner user) throws IOException {
		System.out.println("Select action : ");
		System.out.println("[1]: Move");
		System.out.println("[2]: Shore-up a tile");
		System.out.println("[3]: Give someone a card");
		System.out.println("[4]: Claim a treasure");
		System.out.println("[5]: Finish turn without another action");
		System.out.println("[6]: Play a card (Any Player)");
		int actionNum = ParseNumberInputs.main(user, 1, 6);				/// ---------------------- TODO why does parse inputs have a main? -------------------------
		// TODO should we also maybe allow for discard at end of turn regardless?
		
		switch(actionNum) {
		case 1:
			if( MoveView.getInstance(controller).doAction(currentPlayer, user)) {
				return 1;
			}
			break;
		case 2:
			if( ShoreupView.getInstance(controller).doAction(currentPlayer, user)) {
				return 1;
			}
			break;
		case 3: 
			int actionResult = 0;
			if(GiveCardView.getInstance(controller).doAction(currentPlayer, user)) {
				actionResult = 1;
			}
			for(Player player : controller.getPlayers()) {
				if (controller.handSize(player) == 6) {
					doDiscard(user, player);
				}
			}
			return actionResult;
		case 4:
			if(ClaimTreasureView.getInstance(controller).doAction(currentPlayer, user)) {
				return 1;
			}
		case 5:
			return -1;
		case 6:
			System.out.println("Which player?");
			int iter = 0;
			for (Player p:PlayerList.getInstance().getPlayers()) {
				iter+=1;
				System.out.println("["+iter+"]: " + p.getName());
			}
			int playerNum = ParseNumberInputs.main(user, 1, iter);
			System.out.println(PlayerList.getInstance().getPlayers().get(playerNum-1).toString());
			
			if(PlayCardView.getInstance(controller).doAction(PlayerList.getInstance().getPlayers().get(playerNum-1), user)) {
				return 1;				
			}

			/// TODO why does parse inputs have a main? 
		}
		
		return 0; 
	}
	

    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setupView(TurnController controller) {
    	this.controller = controller;
    	ClaimTreasureView.getInstance(controller);
    	GiveCardView.getInstance(controller);
    	MoveView.getInstance(controller);
    	ShoreupView.getInstance(controller);
    }
    
	
}
