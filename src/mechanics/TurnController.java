package mechanics;

import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.board.WaterLevel;
import players.Player;
import players.PlayerList;
import elements.cards.*;
import elements.pawns.Engineer;
import elements.pawns.Navigator;
import elements.pawns.Pilot;
import players.Hand;

/**
 * TurnController (Singleton, MVC)
 * 
 * 	Controller to implement turn as MVC
 *  Contains main functionality needed in a turn, excluding actions
 *  	Drawing/discarding cards
 *  	Get players, tiles etc for view
 *  	check game over status
 *  
 *  Note: model is all the elements and players
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
	private TurnView view;
	
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
	
	/**
	 * drawFloodCard
	 * 	Draw a flood card
	 */
	public Card drawFloodCard() {			
		return FloodDeck.getInstance().draw(); 	// cards drawn will automatically flood
	} 
	
	/**
	 * doDiscard
	 * 	Called by a hand when it is full
	 * 	Need to go through player list to find the hand that is full (hand doesn't know which player it belongs to)
	 * 	Then call view to prompt that player to discard a card
	 */
	public void doDiscard() {
		for(Player player : PlayerList.getInstance().getPlayers()) {
			if(player.getHand().getCards().size() == 6) { 	// find the full hand
				view.doDiscard(player);
				break;
			}
		}
	}
	
	/**
	 * gameOver
	 * @return	true if game is over
	 */
	public boolean gameOver() {
		return GamePlay.getInstance().getGameOver();
	}
	
	/**
	 * pilotReset
	 * 	reset pilot's flight status at end of turn
	 * @param player
	 */
	public void pilotReset(Player player){
		if(player.getPawn() instanceof Pilot) {
			((Pilot)player.getPawn()).resetHasFlown();
		}
	}
	
	/**
	 * discard
	 * @param player  	- player discarding the card
	 * @param card  	- card to be discarded
	 */
	public void discard(Player player, Card card) {
		player.getHand().discardCard(card); 
	}
	
	/**
	 * isEngineer
	 * @param player
	 * @return true if player's pawn is the engineer
	 */
	public boolean isEngineer(Player player) {
		return (player.getPawn() instanceof Engineer);
	}
	
	/**
	 * isNavigator
	 * @param player
	 * @return true if the player's pawn is the navigator
	 */
	public boolean isNavigator(Player player) {
		return (player.getPawn() instanceof Navigator);
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// 		"get" methods to allow view to display key aspects of the model
	/////////////////////////////////////////////////////////////////////////////
	/**
	 * getPlayers
	 * 	@return list of players in the game
	 */
	public ArrayList<Player> getPlayers(){
		return PlayerList.getInstance().getPlayers();
	}
	
	/**
	 * getHand
	 * @param player
	 * @return player's hand
	 */
	public Hand getHand(Player player){
		return player.getHand();
	}
	
	/**
	 * getHandCards
	 * @param player
	 * @return list of cards in the player's hand
	 */
	public ArrayList<Card> getHandCards(Player player){
		return player.getHand().getCards();
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
	
	/**
	 * getCurrentTile
	 * @param player
	 * @return tile the player's pawn is on
	 */
	public Tile getCurrentTile(Player player) {
		return player.getPawn().getTile();
	}
	
	/**
	 * getTileStatus
	 * @param tile
	 * @return tile's status
	 */
	public TileStatus getTileStatus(Tile tile) {
		return tile.getStatus();
	}
	
	/**
	 * getBoard
	 * @return board instance
	 */
	public Board getBoard() {
		return Board.getInstance();
	}
	
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
	
	/**
	 * setView
	 * assign turn view
	 * @param view
	 */
	public void setView(TurnView view) {
		this.view = view;
	}
	
}
