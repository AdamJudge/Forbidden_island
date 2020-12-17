/**
 * Class Name: GiveCardView
 *
 *  View to display action of giving a card
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.1
 * 	adjusted for ActionController
 * 
 * Creation Date: 22/10/20
 * Last Modified: 17/12/20
 */

package mechanics.actions;

import players.Player;
import setup.ParseLetterInputs;
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
	private ActionController controller;
	private TurnController turnController;
	
	private Scanner user;
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		
		this.user = user;
		
		ArrayList<Player> possiblePlayers = controller.getGiveCardCheck(currentPlayer);

		int limit = possiblePlayers.size();
		System.out.println("Who would you like to give a card to? (Enter 0 to cancel and pick another action)");
		printPlayerList(possiblePlayers, currentPlayer);
		int userNum=ParseNumberInputs.main(user, 0, limit);
		if(userNum == 0) {
			return false;
		}
		
		Player playerToGive = possiblePlayers.get(userNum-1);
		
		if(turnController.handSize(playerToGive) == 5) {
			System.out.println(playerToGive + "'s hand is full, they will need to discard a card. Proceed anyway? [y/n]");
			String userAns = "x";
			while (!userAns.equals("y") && !userAns.equals("n")) {
				userAns = ParseLetterInputs.main(user);
				if(userAns.equals("n")) {
					return false;
				}
				else if(userAns.equals("y")) {
					break;
				}
				System.out.println("Please enter \"y\" or \"n\""); // TODO maybe a parse y/n inputs function? 
			}

		}
		
		System.out.println("Which card would you like to give " + playerToGive + " ?");
		ArrayList<Card> cardsToGive = new ArrayList<>();
		cardsToGive.addAll(turnController.getHandCards(currentPlayer));
		printCardList(cardsToGive);
		userNum=ParseNumberInputs.main(user, 0, cardsToGive.size());
		if(userNum == 0) {
			return false;
		}
		Card selectedCard = cardsToGive.get(userNum-1);
		
		controller.giveCard(currentPlayer, playerToGive, selectedCard);

		System.out.println(currentPlayer + " hand: " + turnController.getHand(currentPlayer));
		System.out.println(playerToGive + " hand: " + turnController.getHand(playerToGive));
		
		return true;
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
	
	public void setController(TurnController turnController, ActionController actionController) {
		this.turnController = turnController;
		this.controller = actionController;
	}
	
}
