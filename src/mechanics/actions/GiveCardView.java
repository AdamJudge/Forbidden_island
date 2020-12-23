package mechanics.actions;

import players.Player;
import mechanics.TurnController;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import mechanics.Scan;

import java.util.ArrayList;

import elements.cards.Card;

/**
 * GiveCardView (Singleton, MVC)
 *
 *  View to display action of giving a card to another player
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.2
 * 	Cleaned up doAction with private methods
 * 
 * Creation Date: 22/10/20
 * Last Modified: 19/12/20
 */
public class GiveCardView  {

	private static GiveCardView giveCardView = null;
	private GiveCardController controller;
	private TurnController turnController;
	
	private Scan user; 
	
	/**
	 * doAction
	 * 
	 * Display players a card can be given to, and ask user to pick
	 * Get card to be given
	 * Give the card to the other player
	 * Display resulting hands
	 * 
	 * @param currentPlayer
	 * @return true if action was carried out, false if cancelled 
	 */
	public boolean doAction(Player currentPlayer) {
		
		// Get player to give a card to 
		Player playerToGive = getPlayerToGive(currentPlayer);
		if (playerToGive == null) {	// User asked to cancel the action
			return false;
		}
		
		// Notify player if the player they are giving a card to has a full hand
		boolean proceed = fullHandCheck(playerToGive);
		if(proceed == false) { // User asked to cancel the action
			return false;
		}

		// able to proceed
		// get card to be given to playerToGive
		Card selectedCard = getCardToGive(currentPlayer, playerToGive);
		if(selectedCard == null) { // User asked to cancel the action
			return false;
		}
		
		controller.giveCard(currentPlayer, playerToGive, selectedCard);

		System.out.println(currentPlayer + " hand: " + turnController.getHand(currentPlayer));
		System.out.println(playerToGive + " hand: " + turnController.getHand(playerToGive));
		
		return true;
	}

	/**
	 * getPlayerToGive
	 * 
	 * Check players that can be given a card by currentPlayer
	 * Ask user for input
	 * 
	 * @param currentPlayer
	 * @return player who will be receiving a card
	 */
	private Player getPlayerToGive(Player currentPlayer) {
		System.out.println("Who would you like to give a card to? (Enter 0 to cancel and pick another action)");
		
		ArrayList<Player> possiblePlayers = controller.getGiveCardCheck(currentPlayer);
		ViewDisplayTools.printPlayerList(possiblePlayers, currentPlayer);
		
		int userNum=ViewInputTools.numbers(user, 0, possiblePlayers.size());
		
		if(userNum == 0) {
			return null;	// return null player if none selected
		}
		
		return possiblePlayers.get(userNum-1); // selected player
	}
	
	
	/**
	 * fullHandCheck
	 * 
	 * checks if the selected player's hand is full, and get confirmation that they still want to exchange a card
	 * 
	 * @param playerToGive
	 * @return true if user confirms, false if they want to cancel
	 */
	private boolean fullHandCheck(Player playerToGive) {
		if(controller.handSize(playerToGive) == 5) {
			System.out.println(playerToGive + "'s hand is full, they will need to discard a card.");
			System.out.println(playerToGive + "'s hand: " + turnController.getHandCards(playerToGive));
			System.out.println("Proceed anyway? [y/n]");
			
			boolean userAns = ViewInputTools.yesNo(user);
			return userAns; // can proceed if user allows
		}
		else {
			return true; // can proceed
		}
	}
	
	/**
	 * getCardToGive
	 * 
	 * ask user to select the card they want to give
	 * 
	 * @param currentPlayer
	 * @param playerToGive
	 * @return	card to be given
	 */
	private Card getCardToGive(Player currentPlayer, Player playerToGive) {
		System.out.println("Which card would you like to give " + playerToGive + " ?");
		
		ArrayList<Card> cardsToGive = new ArrayList<>();
		cardsToGive.addAll(turnController.getHandCards(currentPlayer));
		
		ViewDisplayTools.printCardList(cardsToGive);
		
		int userNum=ViewInputTools.numbers(user, 0, cardsToGive.size());
		
		if(userNum == 0) {
			return null;
		}
		return cardsToGive.get(userNum-1);
	}
	
	
	
	/**
	 * getInstance
	 * 	get singleton instance of GiveCardView
	 * @param controller - controller associated with the view
	 * @return giveCardView (singleton instance)
	 */
	public static GiveCardView getInstance() {
		if(giveCardView == null) { 
			giveCardView = new GiveCardView();
		}
		return giveCardView;
	}
	
	/**
	 * setup
	 * 
	 * assign scanner, turnController and giveCardController
	 * 
	 * @param user
	 * @param turnController
	 * @param giveCardController
	 */
	public void setup(Scan user, TurnController turnController, GiveCardController giveCardController) {
		this.user = user;
		this.turnController = turnController;
		this.controller = giveCardController;
	}
	
}
