package mechanics;

import java.util.ArrayList;

import players.Player;
import elements.board.Tile;
import elements.cards.*;
import mechanics.actions.*;
import mechanics.cardActions.PlayCardView;
import mechanics.cardActions.CardActionController;

/**
 * TurnView (Singleton)
 * 	View to display turn events
 * @author Catherine Waechter
 * @version 1.6
 * 	adjusted for ActionController
 * 
 * Date Created:  03/12/20
 * Last Modified: 22/12/20
 */
public class TurnView {

	private static TurnView turnView = null;
	private TurnController controller;
	private CardActionController cardController;
	private Player currentPlayer;
	private Scan user;
	
	/**
	 * run the turn view
	 * 
	 * @param player
	 * @param user
	 */
	public void run(Player player) {
		boolean statusOK = !controller.gameOver();
		if (!statusOK) {		
			return;
		}
		
		System.out.println(controller.getBoard());
	
		currentPlayer = player;
		
		System.out.println("It's your turn " + currentPlayer + "!");
		System.out.println("Your cards are : " + controller.getHand(currentPlayer));
		System.out.println("Your " + player.getPawn() + " is on " + controller.getCurrentTile(player));
		
		statusOK = doActions();
		if (!statusOK) {
			return;
		}
		
		// TODO play card? 
		
		boolean playable = drawTreasureCards();
		if(playable) {
			System.out.println("You drew a playable card! Would you like to play it? [y/n]");
			if(ViewInputTools.yesNo(user)) {
				PlayCardView.getInstance().doAction(player);
			}
		}
		statusOK = !controller.gameOver();
		if(!statusOK) {
			return;
		}
				
		statusOK = drawFloodCards();
		if (!statusOK) {
			return;
		}
		
		controller.pilotReset(player); // checks if the player is a pilot, so can be called regardless
	}
		
	
	/**
	 * doActions
	 * Carry out 3 actions 
	 * 	return early if requested by user
	 * 	no action used if user cancels
	 * @return true if actions were done successfully. false if game has ended
	 */
	private boolean doActions() {
		int actionCount = 3;	// player starts turn with 3 actions
		int actionReturn;		// value returned from action	
		
		// TODO It might be good to have this check after notifying observers??
		if (controller.gameOver()) {	// Check if game is over before asking for next action
			return false;
		}
		
		while(actionCount > 0) {
			
			actionReturn = selectAction();	// select and carry out action
			
			if(actionReturn == 1) {			// action returns 1 if the action was carried out, 0 if it was cancelled or unsuccessful
				actionCount--;
			}
			else if(actionReturn == -1) {	// action returns -1 to move to next turn
				return true;
			}
			if (controller.gameOver()) {	// Check if game is over before asking for next action
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * drawFloodCards
	 * 	draw enough flood cards for the current water level
	 * 	display new status of relevant tiles
	 * 
	 * @return true if drawing was carried out successfully. false if game has ended
	 */
	private boolean drawFloodCards() {
		Card cardDrawn;
		for (int i = 0; i< controller.getNbrCards(); i++) {
			cardDrawn = controller.drawFloodCards();
			if (GamePlay.getInstance().getGameOver()) {
				return false;
			}
			System.out.println("Flood card drawn: " + cardDrawn);
			Tile tileFlooded = controller.getFloodCardTile(cardDrawn);
			System.out.println(tileFlooded + " status: " + controller.getTileStatus(tileFlooded));
		}
		return true;
	}
	
	/**
	 * drawTreasureCards
	 * 	draw 2 treasure cards and display updated hand
	 * 	@returns true if any card drawn is playable
	 */
	private boolean drawTreasureCards() {
		Card cardDrawn;
		boolean playable = false;
		for(int i = 0; i<2; i++) {
			cardDrawn = controller.drawTreasureCard(currentPlayer);
			if(cardController.playable(cardDrawn)){ 	
				playable = true;
			}
			System.out.println(currentPlayer + " drew a " + cardDrawn + " card.");
		}
		System.out.println("Your cards are : " + controller.getHand(currentPlayer));
		return playable;
	}
	
	/**
	 * doDiscard
	 * 	Called by controller if a player's hand becomes full
	 * 
	 * @param player
	 */
	public boolean doDiscard(Player player){
		System.out.println(player + "'s hand is full! ");
		System.out.println(player + "'s current hand is: " + controller.getHand(player));
		
		if(cardController.playable(controller.getHandCards(player))){ // If any cards in the player's hand are playable
			System.out.println("Would you like to play a card before you discard? [y/n]");
			if(ViewInputTools.yesNo(user)) {
				PlayCardView.getInstance().doAction(player);
				if(controller.gameOver()) {
					return false;
				}
				System.out.println("You no longer need to discard a card!");
				return true;
			}
		}
		
		System.out.println("Please select a card to discard: ");
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.addAll(controller.getHandCards(player));
		ViewDisplayTools.printCardList(cards);
		
		int cardNum = ViewInputTools.numbers(user, 1, 6);

		controller.discard(player, cards.get(cardNum-1));
		return true;
	}
	
	/**
	 * selectAction
	 * 	Allow user to select desired action. calls each action
	 * @param user
	 * @return whether an action was used (0 - no, 1 - yes, -1 - cancel further actions)
	 */
	private int selectAction() {
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
			if( MoveView.getInstance().doAction(currentPlayer)) {
				return 1;
			}
			break;
		case 2:
			if( ShoreupView.getInstance().doAction(currentPlayer)) {
				return 1;
			}
			break;
		case 3: 
			int actionResult = 0;
			if(GiveCardView.getInstance().doAction(currentPlayer)) {
				actionResult = 1;
			}
			return actionResult;
		case 4:
			if(ClaimTreasureView.getInstance().doAction(currentPlayer)) {
				return 1;
			}
		case 5:
			return -1;
		case 6:
			if(PlayCardView.getInstance().doAction(null)) {
				return 1;				
			}
 		}
		return 0; 
	}
	
	
    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setup(Scan user, TurnController turnController, CardActionController cardController) {
    	this.user = user;
    	this.controller = turnController;
    	this.cardController = cardController;
    }
    
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
	
	
}
