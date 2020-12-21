package mechanics;

import java.util.ArrayList;
import java.util.List;

import elements.board.Tile;
import elements.board.TileStatus;
import elements.board.WaterLevel;
import players.Player;
import players.PlayerList;
import elements.cards.*;
import elements.pawns.Pilot;
import players.Hand;

/**
 * TurnController
 * 	Controller to implement turn as MVC
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	Put all action related methods into ActionController
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
	 * drawFloodCard
	 * 	Draw a flood card
	 */
	public Card drawFloodCards() {			
		return FloodDeck.getInstance().draw(); 	// cards drawn will automatically flood
	} 
	
	/**
	 * getNbrCards
	 * @return number of flood cards to be drawn for current water level
	 */
	public int getNbrCards() {
		return WaterLevel.getInstance().getNbrCards();
	}
	
	/**
	 * getFloodCardTile
	 * 	
	 * @param card
	 * @return tile corresponding to card
	 */
	public Tile getFloodCardTile(Card card) {
		if(card instanceof FloodCard) {
			return ((FloodCard)card).getTile();
		}
		else return null;
	}
	
	public TileStatus getTileStatus(Tile tile) {
		return tile.getStatus();
	}
	
	/**
	 * drawTreasureCard
	 * 	draw a treasure cards. Add to player's hand
	 * @param player
	 */
	public Card drawTreasureCard(Player player) {	
		Card card;
		card = TreasureDeck.getInstance().draw();
		if(((TreasureCard)card).getCardType() != TreasureCardTypes.WATERSRISE) {		
			player.getHand().addCard(card);
		}
		return card;
	} 
	
}
