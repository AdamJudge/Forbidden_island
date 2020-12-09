package mechanics;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import setup.ParseNumberInputs;
import setup.SetupController;
import players.Player;
import players.PlayerList;
import elements.pawns.*;
import elements.board.Board;
import elements.board.Tile;
import elements.cards.*;
import elements.board.WaterLevel;
import mechanics.actions.*;

/**
 * TurnView
 * 	View to display turn events
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created:  03/12/20
 * Last Modified: 03/12/20
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
		try {TimeUnit.SECONDS.sleep(2);}
		catch (InterruptedException e) {}
		System.out.println(Board.getInstance());

		this.user = user;
	
		currentPlayer = player;
		System.out.println("It's your turn " + player + "!");
		System.out.println("Your cards are : " + controller.getHand(player));
		System.out.println("Your " + player.getPawn() + " is on " + player.getPawn().getTile());
		int actionCount = 3;
		boolean actionReturn;
		while(actionCount > 0) {
			// TODO Allow interrupt to play a card
			actionReturn = selectAction(user);
			if(actionReturn == true) {
				actionCount--;
			}
		}
		// TODO should still be able to play cards in here
		for(int i = 0; i<2; i++) {
			controller.drawTreasureCard(player);
			if(controller.handSize(player) == 6) {
				doDiscard(user, currentPlayer);
			}
		}
		
		controller.drawFloodCards();
		
		System.out.println("Your cards are : " + controller.getHand(player));

		// TODO get floodcards to return card name so it can be printed. Will also need to check status of tile to say if it was flooded or removed
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
	 * @return whether an action was used
	 * @throws IOException
	 */
	private boolean selectAction(Scanner user) throws IOException {
		System.out.println("Select action : "); // TODO print possible actions
		System.out.println("[1]: Move");
		System.out.println("[2]: Shore-up a tile");
		System.out.println("[3]: Give someone a card");
		System.out.println("[4]: Claim a treasure");
		System.out.println("[5]: Finish turn without another action");
		System.out.println("[6]: Play a card (Any Player)");
		int actionNum = ParseNumberInputs.main(user, 1, 6);				/// ---------------------- TODO why does parse inputs have a main? -------------------------
		// TODO need to check for hands with too many cards and prompt discard
		// also maybe allow for discard at end of turn regardless?
		
		switch(actionNum) {
		case 1:
			return MoveView.getInstance(controller).doAction(currentPlayer, user);
		case 2:
			return ShoreupView.getInstance(controller).doAction(currentPlayer, user);
		case 3: 
			boolean actionResult =  GiveCardView.getInstance(controller).doAction(currentPlayer, user);
			for(Player player : controller.getPlayers()) {
				if (controller.handSize(player) == 6) {
					doDiscard(user, player);
				}
			}
			return actionResult;
		case 4:
			return ClaimTreasureView.getInstance(controller).doAction(currentPlayer, user);
		case 5:
			break;
		case 6:
			System.out.println("Which player?");
			int iter = 0;
			for (Player p:PlayerList.getInstance().getPlayers()) {
				iter+=1;
				System.out.println("["+iter+"]: " + p.getName());
			}
			int playerNum = ParseNumberInputs.main(user, 1, iter);
			System.out.println(PlayerList.getInstance().getPlayers().get(playerNum-1).toString());
			return PlayCardView.getInstance(controller).doAction(PlayerList.getInstance().getPlayers().get(playerNum-1), user);
			/// ---------------------- TODO why does parse inputs have a main? -------------------------
			// TODO how do we get the number of actions to go down to 0? Could return 0,1,-1 instead of a boolean?
		}
		
		return true; // use false for cases where the player has not used an action
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
