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

package mechanics.cardActions;

import mechanics.Scan;
import mechanics.ViewDisplayTools;

import java.util.ArrayList;

import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import mechanics.TurnController;
import mechanics.ViewInputTools;
import players.Player;

public class PlayCardView  {		  

	private static PlayCardView playCardView = null;
	private CardActionController cardController;
	private TurnController turnController;
	private Scan user;
	
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

	public void setup(Scan user, TurnController turnController, CardActionController cardController) {
		this.user = user;
		this.turnController = turnController;
		this.cardController = cardController;
	}
	
	/**
	 * doAction 
	 * @param player - null if player needs to be selected
	 * @return false (never uses an action)
	 */
	public boolean doAction(Player player) { 	// TODO make void? 
		
		if(player == null) {
			player = getPlayer();
		}
		
		TreasureCardTypes cardType = getCardType(player);
		if(cardType == null) {
			return false;
		}
		
		playCard(player, cardType);

		return false;
	}
	
	/**
	 * getPlayer
	 * 	get the player who wants to play a card
	 * @return player who will play a card
	 */
	private Player getPlayer() {
		System.out.println("Which player?");
		ArrayList<Player> validPlayers = turnController.getPlayers();
		ViewDisplayTools.printPlayerList(validPlayers, null); // playing cards doesn't have a current player
		
		int playerNum = ViewInputTools.numbers(user, 1, validPlayers.size());
		
		return  validPlayers.get(playerNum-1);
		
	}
	
	/**
	 * playCard
	 * 	play a card of the required type
	 * 
	 * @param player
	 * @param cardType
	 */
	private void playCard(Player player, TreasureCardTypes cardType) {
		for(int i = 0; i< turnController.getHandCards(player).size(); i++) {
			Card card = turnController.getHandCards(player).get(i);
			if (((TreasureCard)card).getCardType() == cardType) {
				cardController.playCard(card, player);
			}
		}
	}
	
	/**
	 * getCardType
	 * 	get card type the player wants to play
	 * 
	 * @param player
	 * @return card type to play. null if none can be played
	 */
	private TreasureCardTypes getCardType(Player player) {
		
		int hIndex = 0, sIndex = 0, userIndex = 0;
		ArrayList<Card> playerCards = turnController.getHandCards(player);
		
		if(!cardController.playable(playerCards)) {
			System.out.println(player + " has no cards to play!");
			return null;
		}
		
		System.out.println("Which card do you want to play " + player +"? (0 to cancel)");
		for (Card card: playerCards) {
		
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

	public void tearDown() {
		playCardView=null;
	}
	
}

