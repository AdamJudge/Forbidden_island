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
 * @version 1.1
 *
 *	Date created: 03/12/20
 *	Last modified: 09/12/20
 */
public class TurnController {

	// TODO refactor this into multiple classes
	
	private static TurnController turnController = null;
	//private Turn model;	// TODO what is the model here, It's not actually turn
	private TurnView view;
	
	/**
	 * getInstance
	 * 	get singleton instance of TurnController
	 * @param view - view associated with controller
	 * @param model - model associated with controller 
	 * @return turnController (singleton instance)
	 */
	public static TurnController getInstance(TurnView view) {
		if(turnController == null) { 
			turnController = new TurnController(view);
		}
		return turnController;
	}
	
	public void pilotReset(Player player){
		if(player.getPawn() instanceof Pilot) {
			((Pilot)player.getPawn()).resetHasFlown();
		}
	}
	
	public void doSwim(ArrayList<Player> players) throws IOException {
		// check that all players can swim somewhere first
		
		for(Player player : players) {
			if(player.getPawn().swimCheck() == null) {
				view.doSwim(null, null);	// TODO Maybe not needed
				// TODO End game observer
				return;
			}
		}
		
		for(Player player : players) {
			ArrayList<Tile> possibleTiles = player.getPawn().swimCheck();
			view.doSwim(player, possibleTiles);
		}

	}
	
	/**
	 * TurnController Constructor
	 * 		Assigns model and view
	 * @param view
	 */
	private TurnController(TurnView view) {
		this.view = view;
	//	this.model = model;
	}
	
	/**
	 * getPlayers
	 * 	@return list of players in the game
	 */
	public ArrayList<Player> getPlayers(){
		return PlayerList.getInstance().getPlayers();
	}
	
	/**
	 * getClaimTreasureCheck
	 * 	Returns the treasure the given player is able to capture
	 * @param currentPlayer
	 * @return Treasure the player can capture, or null if none
	 */
	public Treasure getClaimTreasureCheck(Player currentPlayer) {
		int treasureCount = 0;
		Treasure potentialTreasure = null;
		
		// get treasure associated with tile the player's pawn is on
		potentialTreasure = currentPlayer.getPawn().getTile().getTreasure();
		
		// if no treasure associated, no treasure can be collected
		if(potentialTreasure == null) {
			return null;
		}
		
		// if player's pawn is on a tile with a treasure, count cards of that treasure type
		for(Card card : currentPlayer.getHand().getCards()) {
			if(((TreasureCard)card).getCardType() != TreasureCardTypes.TREASURE) {
				continue;
			}
			
			if(((TreasureCard)card).getTreasureType() == potentialTreasure) {
				treasureCount++;
			}
		}
		if(treasureCount >= 4) {
			return potentialTreasure;
		}
		else return null;
	}
	
	/**
	 * claimTreasure
	 * 	capture the given treasure
	 * @param treasure
	 */
	public void claimTreasure(Treasure treasure) {
		treasure.captureTreasure();
	}
	
	/**
	 * getUnclaimedTreasures
	 * 	returns a set of treasures which have not been claimed yet
	 * @return
	 */
	public Set<Treasure> getUnclaimedTreasures(){
		Set<Treasure> unclaimedTreasures = new HashSet<Treasure>();
		for(Map.Entry<TreasureNames, Treasure> entry : Board.getInstance().getTreasures().entrySet()) {
			if(!entry.getValue().isCaptured()) {
				unclaimedTreasures.add(entry.getValue());
			}
		}
		return unclaimedTreasures;
	}
	
	public void giveCard(Player playerFrom, Player playerTo, Card card) {
		playerFrom.getHand().takeCard(card);
		playerTo.getHand().addCard(card);
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
	
	public ArrayList<Player> getGiveCardCheck(Player player){
		if(player.getPawn() instanceof Messenger) {
			ArrayList<Player> validPlayers = getPlayers();
			validPlayers.remove(player);
			return validPlayers;
		}
		else {
			ArrayList<Player> validPlayers = new ArrayList<Player>();
			for(Player otherPlayer : getPlayers()) {
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
	 * getMoveCheck
	 * 	Get possible tiles the given player's pawn can move to 
	 * @param player whose pawn will move
	 * @return	list of possible tiles
	 */
	public ArrayList<Tile> getMoveCheck(Player player){
		return player.getPawn().moveCheck();
	}
	
	/**
	 * getShoreupCheck
	 * 	Get possible tiles the given player's can shore up
	 * @param player 
	 * @return	list of possible tiles
	 */
	public ArrayList<Tile> getShoreupCheck(Player player){	// TODO should be done in model ?
		return player.getPawn().shoreupCheck();
	}
	
	
	/**
	 * drawFloodCards
	 * 	Draw flood cards according to water level
	 */
	public Set<Card> drawFloodCards() throws IOException {			// TODO should this be more abstract? 
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
	public Card drawTreasureCard(Player player) throws IOException {	// TODO should this be more abstract? 
		Card card;
		card = TreasureDeck.getInstance().draw();
		if(card != null) {		// card returned is null if it was a waters rise card
			player.getHand().addCard(card);
		}
		return card;
	} 
	
	/**
	 * move
	 * 	Move player's pawn to the destination
	 * @param player
	 * @param destination
	 * @return
	 */
	public Tile move(Player player, Tile destination) { // TODO should this be more abstract? 
		player.getPawn().move(destination);
		return player.getPawn().getTile();
	}
	
	/**
	 * shoreup
	 * 	Shore up a given tile
	 * @param tile
	 * @return
	 */
	public Tile shoreup(Player player, Tile tile) { // TODO should this be more abstract? 
		player.getPawn().shoreup(tile);
		return player.getPawn().getTile();
	}
}
