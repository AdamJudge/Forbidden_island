package mechanics;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;

import elements.board.Board;
import elements.board.Tile;
import elements.board.WaterLevel;
import players.Player;
import players.PlayerList;
import elements.cards.*;
import elements.pawns.Messenger;
import elements.pawns.Pilot;
import players.Hand;
import elements.treasures.Treasure;
import elements.treasures.TreasureNames;

/**
 * TurnController
 * 	Controller to implement turn as MVC
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	Put all action related methods into ActionCotroller
 *
 *	Date created: 03/12/20
 *	Last modified: 17/12/20
 */
public class TurnController {
	
	private static TurnController turnController = null;
	//private Turn model;	// TODO what is the model here, It's not actually turn
	private TurnView view;
	
	/**
	 * getInstance
	 * 	get singleton instance of TurnController
	 * @return turnController (singleton instance)
	 */
	public static TurnController getInstance() {
		if(turnController == null) { 
			turnController = new TurnController();
		}
		return turnController;
	}
	
	public void setView(TurnView view) {
		this.view = view;
	}
	
	public void pilotReset(Player player){
		if(player.getPawn() instanceof Pilot) {
			((Pilot)player.getPawn()).resetHasFlown();
		}
	}
	
	public void doDiscard() {
		
		for(Player player : PlayerList.getInstance().getPlayers()) {
			if(player.getHand().getCards().size() == 6) {
				view.doDiscard(player);
				break;
			}
		}
	}
	
	
	
	/**
	 * getPlayers
	 * 	@return list of players in the game
	 */
	public ArrayList<Player> getPlayers(){
		return PlayerList.getInstance().getPlayers();
	}
	

	public int handSize(Player player) {
		return player.getHand().getCards().size();
	}
	
	public void discard(Player player, Card card) {
		player.getHand().discardCard(card);
	}
	
	/**
	 * getHand
	 * @param player
	 * @return player's hand
	 */
	public Hand getHand(Player player){
		return player.getHand();
	}
	
	public List<Card> getHandCards(Player player){
		return player.getHand().getCards();
	}
	
	/**
	 * drawFloodCards
	 * 	Draw flood cards according to water level
	 */
	public Set<Card> drawFloodCards() {			// TODO should this be more abstract? 
		Set<Card> cardsDrawn = new HashSet<Card>();
		for(int i=0; i< WaterLevel.getInstance().getNbrCards(); i++) {
			cardsDrawn.add(FloodDeck.getInstance().draw()); 	// cards drawn will automatically flood
		}
		return cardsDrawn;
	} 
	
	/**
	 * drawTreasureCards
	 * 	draw two treasure cards. Add to player's hand
	 * @param player
	 */
	public Card drawTreasureCard(Player player) {	// TODO should this be more abstract? 
		Card card;
		card = TreasureDeck.getInstance().draw();
		if(((TreasureCard)card).getCardType() != TreasureCardTypes.WATERSRISE) {		
			player.getHand().addCard(card);
		}
		return card;
	} 
	
}
