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

/**
 * PlayCardView (Singleton, MVC)
 *
 * 	View to display and get user input for playable cards
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.0
 * 	Rewritten to ensure only one instance of each card is displayed
 * 	Refactored into smaller methods
 * 
 * Creation Date: 22/10/20
 * Last Modified: 21/12/20
 */
public class PlayCardView  {		  

	private static PlayCardView playCardView = null;
	private CardActionController cardController;
	private TurnController turnController;
	private Scan user;
		
	/**
	 * doAction 
	 * @param player - null if player needs to be selected
	 * @return false (never uses an action)
	 */
	public boolean doAction(Player player) { 
		// player is the player who is going to play a card. null if not decided yet
		if(player == null) {
			player = getPlayer();	// get player who would like to play the card
		}
		
		TreasureCardTypes cardType = getCardType(player); // helicopter card or sandbags card
		if(cardType == null) {
			return false;
		}
		
		playCard(player, cardType);	

		return false;
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
		
		// Check that the player has at least one playable card
		if(!cardController.playable(playerCards)) {
			System.out.println(player + " has no cards to play!");
			return null;
		}
		
		System.out.println("Which card do you want to play " + player +"? (0 to cancel)");
		for (Card card: playerCards) {
			if( card instanceof TreasureCard) { //It always is but to be safe 	
				
				if (cardController.isHelicopter(card)&& hIndex == 0) { // check hIndex to avoid printing for multiple helicopter cards
					userIndex++;
					hIndex = userIndex;		// hIndex = user index associated with the helicopter card
					System.out.println("["+userIndex+"]: " + card);
				}
				
				else if(cardController.isSandbags(card) && sIndex == 0) { // check sIndex to avoid printing for multiple sandbags cards
					userIndex++;
					sIndex = userIndex;		// sIndex = user index associated with the sandbags card
					System.out.println("["+userIndex+"]: " + card);
				}	
			}
		}
		
		int cardNum = ViewInputTools.numbers(user, 0, userIndex);	// get user input
		
		if(cardNum == 0) {	// cancel
			return null;
		}
		else if(cardNum == hIndex) {
			return TreasureCardTypes.HELICOPTER;
		}
		else if(cardNum == sIndex) {
			return TreasureCardTypes.SANDBAGS;
		}
		return null;	// should not be reached
		
	}

	/**
	 * getPlayer
	 * 	get the player who wants to play a card
	 * @return player who will play a card
	 */
	private Player getPlayer() {
		System.out.println("Which player?");
		
		ArrayList<Player> validPlayers = turnController.getPlayers();
		
		ViewDisplayTools.printPlayerList(validPlayers, null); // null bc playing cards doesn't have a current player
		
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

	/**
	 * setup
	 * assign controllers
	 * @param user
	 * @param turnController
	 * @param cardController
	 */
	public void setup(Scan user, TurnController turnController, CardActionController cardController) {
		this.user = user;
		this.turnController = turnController;
		this.cardController = cardController;
	}
	
	/**
	 * tearDown
	 * used for testing
	 */
	public void tearDown() {
		playCardView=null;
	}
	
}

