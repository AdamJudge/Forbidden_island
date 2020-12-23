package mechanics.actions;

import java.util.ArrayList;

import elements.cards.Card;
import elements.pawns.Messenger;
import players.Player;
import players.PlayerList;

/**
 * GiveCardController (Singleton, MVC)
 * 
 * Controller to handle the action of giving cards to another player
 * 
 * @author Catherine Waechter
 * @version 1.0 
 * 	Methods were originally in ActionController
 * 
 * Date Created: 23/12/20
 * Last Modified: 23/12/20
 *
 */
public class GiveCardController {

	private static GiveCardController giveCardController = null;
	
	/**
	 * getGiveCardCheck
	 * 
	 * 	check which players someone can give a card to
	 * 
	 * @param player
	 * @return list of valid players
	 */
	public ArrayList<Player> getGiveCardCheck(Player player){
		if(player.getPawn() instanceof Messenger) {
			ArrayList<Player> validPlayers = new ArrayList<Player>();
			for(Player otherPlayer : PlayerList.getInstance().getPlayers()) {
				if (otherPlayer != player) {
					validPlayers.add(otherPlayer);
				}
			}
			return validPlayers;
		}
		else {
			ArrayList<Player> validPlayers = new ArrayList<Player>();
			for(Player otherPlayer : PlayerList.getInstance().getPlayers()) {
				if(otherPlayer == player) {
					// skip current player
				}
				else if (otherPlayer.getPawn().getTile() == player.getPawn().getTile()) {
					validPlayers.add(otherPlayer);
				}
			}
			return validPlayers;
		}
	}

	/**
	 * giveCard 
	 * 
	 * @param playerFrom - player giving the card
	 * @param playerTo	- player getting the card
	 * @param card	- card to be given
	 */
	public void giveCard(Player playerFrom, Player playerTo, Card card) {
		playerFrom.getHand().takeCard(card);
		playerTo.getHand().addCard(card);
	}
	
	/**
	 * handSize
	 * 	Get the number of cards in a player's hand
	 * @param player
	 * @return number of cards in the player's hand
	 */
	public int handSize(Player player) {
		return player.getHand().getCards().size();
	}
		
	/**
	 * getInstance
	 * 	get singleton instance of GiveCardController
	 * @return giveCardController (singleton instance)
	 */
	public static GiveCardController getInstance() {
		if(giveCardController== null) { 
			giveCardController= new GiveCardController();
		}
		return giveCardController;
	}
}
