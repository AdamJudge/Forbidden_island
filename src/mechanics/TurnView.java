package mechanics;

import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;

import players.Player;
import players.PlayerList;
import elements.board.Board;
import elements.cards.*;
import mechanics.actions.*;

/**
 * TurnView
 * 	View to display turn events
 * @author Catherine Waechter
 * @version 1.5
 * 	adjusted for ActionController
 * 
 * Date Created:  03/12/20
 * Last Modified: 17/12/20
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
	 */
	public void run(Player player, Scanner user) {
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
		}
		
		Set<Card> cardsDrawn = controller.drawFloodCards();
		System.out.println("Flood cards drawn: " + cardsDrawn);
		// TODO could be nice to print new status of relevant tiles, but not completely necessary since board is printed
		// TODO draw one card at a time to print out each card when it's drawn instead of all together
		
		System.out.println("Your cards are : " + controller.getHand(player));
		
		controller.pilotReset(player); // checks if the player is a pilot, so can be called regardless
	}
	
	
	
	public void doDiscard(Player player) {
		System.out.println(player + "'s hand is full! Please select a card to discard: ");
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(controller.getHandCards(player));
		ViewDisplayTools.printCardList(cards);
		
		int cardNum = ViewInputTools.numbers(user, 1, 6);

		controller.discard(player, cards.get(cardNum-1));
	}
	
	/**
	 * selectAction
	 * 	Allow user to select desired action. calls each action
	 * @param user
	 * @return whether an action was used (0 - no, 1 - yes, -1 - cancel further actions)
	 */
	private int selectAction(Scanner user) {
		System.out.println("Select action : ");
		System.out.println("[1]: Move");
		System.out.println("[2]: Shore-up a tile");
		System.out.println("[3]: Give someone a card");
		System.out.println("[4]: Claim a treasure");
		System.out.println("[5]: Finish turn without another action");
		System.out.println("[6]: Play a card (Any Player)");
		
		int actionNum = ViewInputTools.numbers(user, 1, 6);
		
		switch(actionNum) {
		case 1:
			if( MoveView.getInstance().doAction(currentPlayer, user)) {
				return 1;
			}
			break;
		case 2:
			if( ShoreupView.getInstance().doAction(currentPlayer, user)) {
				return 1;
			}
			break;
		case 3: 
			int actionResult = 0;
			if(GiveCardView.getInstance().doAction(currentPlayer, user)) {
				actionResult = 1;
			}
			return actionResult;
		case 4:
			if(ClaimTreasureView.getInstance().doAction(currentPlayer, user)) {
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
			
			int playerNum = ViewInputTools.numbers(user, 1, iter);
			
			System.out.println(PlayerList.getInstance().getPlayers().get(playerNum-1).toString());
			
			if(PlayCardView.getInstance(controller).doAction(PlayerList.getInstance().getPlayers().get(playerNum-1), user)) {
				return 1;				
			}
 
		}
		
		return 0; 
	}
	
	public void setScanner(Scanner user) {
		this.user = user;
	}

    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setupView(TurnController turnController, ActionController actionController) {
    	this.controller = turnController;
    	ClaimTreasureView.getInstance().setController(actionController);
    	GiveCardView.getInstance().setController(turnController, actionController);
    	MoveView.getInstance().setController(turnController, actionController);
    	ShoreupView.getInstance().setController(actionController);
    }
    
	
}
