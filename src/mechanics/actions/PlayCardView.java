/**
 * PlayCardView
 *
 * 	Display and get user input for playable cards
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.0
 * 	Rewritten to ensure only one instance of each card is displayed
 * 	Refactored into smaller methods
 * 
 * Creation Date: 22/10/20
 * Last Modified: 21/12/20
 */

package mechanics.actions;

import java.util.Scanner;

import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;

import mechanics.ViewInputTools;
import players.Player;

public class PlayCardView  {

	private static PlayCardView playCardView = null;
	private ActionController actionController;
	private Scanner user;
	
	/**
	 * getInstance
	 * 	get singleton instance of ShoreupView
	 * @param controller - controller associated with the view
	 * @return shoreupView (singleton instance)
	 */
	public static PlayCardView getInstance() {
		if(playCardView == null) { 
			playCardView = new PlayCardView();
		}
		return playCardView;
	}

	public void setController(ActionController actionController) {
		this.actionController = actionController;
	}
	
	public boolean doAction(Player player, Scanner user) { 
		this.user = user;
		
		TreasureCardTypes cardType = getCardType(player);
		if(cardType == null) {
			return false;
		}
		
		playCard(player, cardType);

		return false;
	}
	
	private void playCard(Player player, TreasureCardTypes cardType) {
		for(int i = 0; i<player.getHand().getCards().size(); i++) {
			Card card = player.getHand().getCards().get(i);
			if (((TreasureCard)card).getCardType() == cardType) {
				actionController.playCard(card, player, user);
			}
		}
	}
	
	private TreasureCardTypes getCardType(Player player) {
		System.out.println("Which card do you want to play " + player +"? (0 to cancel)");
		
		int hIndex = 0, sIndex = 0, userIndex = 0;
		
		for (Card card: player.getHand().getCards()) {
		
			if( card instanceof TreasureCard) { //It always is but to be safe 	// TODO General - check instances when casting
				
				if (((TreasureCard)card).getCardType() == TreasureCardTypes.HELICOPTER && hIndex == 0) {
					userIndex++;
					hIndex = userIndex;
					System.out.println("["+userIndex+"]: " + card);
				}
				
				else if(((TreasureCard)card).getCardType() == TreasureCardTypes.SANDBAGS && hIndex == 0) {
					userIndex++;
					sIndex = userIndex;
					System.out.println("["+userIndex+"]: " + card);
				}	
			}
		}
		
		if(userIndex == 0) {
			System.out.println("No cards to play!");
			return null;
		}
		
		int cardNum = ViewInputTools.numbers(user, 0, userIndex);
		
		if(cardNum == 0) {
			return null;
		}
		else if(cardNum == hIndex) {
			return TreasureCardTypes.HELICOPTER;
		}
		else if(cardNum == sIndex) {
			return TreasureCardTypes.SANDBAGS;
		}
		return null;
		
	}
	
}

