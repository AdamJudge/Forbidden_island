package mechanics.actions;

import java.util.ArrayList;

import elements.cards.Card;
import elements.pawns.Messenger;
import players.Player;
import mechanics.TurnController;

public class GiveCardController {

	private static GiveCardController giveCardController = null;
	private TurnController turnController;
	
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
			for(Player otherPlayer : turnController.getPlayers()) {
				if (otherPlayer != player) {
					validPlayers.add(otherPlayer);
				}
			}
			return validPlayers;
		}
		else {
			ArrayList<Player> validPlayers = new ArrayList<Player>();
			for(Player otherPlayer : turnController.getPlayers()) {
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
		
	public void setup(TurnController turnController) {
		this.turnController = turnController;
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
