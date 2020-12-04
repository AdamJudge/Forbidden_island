/**
 * Class Name: GiveCardView
 *
 *  View to display action of giving a card
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.2
 * 	adjusted to suit ActionView 
 * 
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
 */

package mechanics.actions;

import players.Player;
import setup.ParseNumberInputs;
import mechanics.TurnController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import players.Player;
import elements.cards.Card;
import elements.pawns.Messenger;

public class GiveCardView  extends ActionView{

	private static GiveCardView giveCardView = null;
	private TurnController controller;

	private Scanner user;
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		
		this.user = user;

		// messenger exception
		
		ArrayList<Player> possiblePlayers = controller.getGiveCardCheck(currentPlayer);

		int limit = possiblePlayers.size();
		System.out.println("Who would you like to give a card to? (Enter 0 to cancel and pick another action)");
		printPlayerList(possiblePlayers, currentPlayer);
		int userNum=ParseNumberInputs.main(user, 0, limit);
		if(userNum == 0) {
			return false;
		}
		
		Player playerToGive = possiblePlayers.get(userNum-1);
		
		System.out.println("Which card would you like to give " + playerToGive + " ?");
		ArrayList<Card> cardsToGive = new ArrayList<>();
		cardsToGive.addAll(controller.getHandCards(currentPlayer));
		printCardList(cardsToGive);
		userNum=ParseNumberInputs.main(user, 0, cardsToGive.size());
		Card selectedCard = cardsToGive.get(userNum-1);
		
		controller.giveCard(currentPlayer, playerToGive, selectedCard);

		System.out.println(currentPlayer + " hand: " + controller.getHand(currentPlayer));
		System.out.println(playerToGive + " hand: " + controller.getHand(playerToGive));
		
		return true;
	}

	
	/**
	 * getInstance
	 * 	get singleton instance of GiveCardView
	 * @param controller - controller associated with the view
	 * @return giveCardView (singleton instance)
	 */
	public static GiveCardView getInstance(TurnController controller) {
		if(giveCardView == null) { 
			giveCardView = new GiveCardView(controller);
		}
		return giveCardView;
	}
	
	/**
	 * GiveCardView Constructor
	 * @param controller	 - turn controller 
	 */
	private GiveCardView(TurnController controller) {
		this.controller = controller;
	}
	
}
