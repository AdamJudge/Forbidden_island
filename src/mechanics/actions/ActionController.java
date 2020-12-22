package mechanics.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.pawns.Messenger;
import elements.pawns.Navigator;
import elements.treasures.Treasure;
import elements.treasures.TreasureNames;
import mechanics.GamePlay;
import mechanics.TurnController;
import mechanics.cardActions.HelicopterView;
import mechanics.cardActions.SandbagsView;
import players.Player;

/**
 * ActionController
 * 	controller to carry out actions as requested by action views
 * @author Catherine Waechter
 * @version 1.3
 * 	ClaimTreasure now discards cards
 * 	Fixed disappearing messenger bug
 * 
 * Date created: 17/12/20
 * Last modified: 19/12/20
 *
 */
public class ActionController {

	
	static ActionController actionController = null;
	private TurnController turnController;
	private MoveView moveView;
	
	
	/**
	 * getInstance
	 * 	get singleton instance of ActionController
	 * @return actionController (singleton instance)
	 */
	public static ActionController getInstance() {
		if(actionController == null) { 
			actionController = new ActionController();
		}
		return actionController;
	}
	
	public void setupController(TurnController turnController, MoveView moveView) {		
		this.turnController = turnController;
		this.moveView = moveView ;
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
			
			if(((TreasureCard)card).getTreasureType().getName().equals(potentialTreasure.getName())) {
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
	 * 	capture the given treasure and discard 4 cards of that treasure type
	 * 
	 * @param player - player capturing the treasure
	 * @param treasure - treasure to be captured
	 */
	public void claimTreasure(Player player, Treasure treasure) {
		treasure.captureTreasure();
		int discardedCount = 0;
		for(Card card : player.getHand().getCards()) {
			if(discardedCount == 4) {
				break;
			}
			if(((TreasureCard)card).getTreasureType() == treasure) {
				player.getHand().discardCard(card);
				discardedCount++;
			}
		}
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
	
	public void giveCard(Player playerFrom, Player playerTo, Card card) {
		playerFrom.getHand().takeCard(card);
		playerTo.getHand().addCard(card);
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
	public ArrayList<Tile> getShoreupCheck(Player player){	
		return player.getPawn().shoreupCheck();
	}
	

	public void doSwim(ArrayList<Player> players) {
		// check that all players can swim somewhere first
		
		for(Player player : players) {
			player.getPawn().swimCheck();  // if anyone can't swim, observer will update.
		}
		
		if (GamePlay.getInstance().getGameOver()) {
			return;
		}
		
		for(Player player : players) {
			ArrayList<Tile> possibleTiles = player.getPawn().swimCheck();
			moveView.doSwim(player, possibleTiles);
		}

	}
	
	/**
	 * moveOtherCheck
	 * 	For navigator to move other players
	 * @param playerToMove
	 * @param navigatorPlayer
	 * @param destination
	 * @return tile the other player is now on
	 */
	public ArrayList<Tile> getMoveOtherCheck(Player playerToMove, Player navigatorPlayer) {
		if(navigatorPlayer.getPawn() instanceof Navigator) {
			return ((Navigator)navigatorPlayer.getPawn()).moveOtherCheck(playerToMove.getPawn());	
		}
		return null;
	}
	
	/**
	 * move
	 * 	Move player's pawn to the destination
	 * @param player
	 * @param destination
	 * @return
	 */
	public Tile move(Player player, Tile destination) { 
		player.getPawn().move(destination);
		return player.getPawn().getTile();
	}
	
	/**
	 * shoreup
	 * 	Shore up a given tile
	 * @param tile
	 */
	public void shoreup(Tile tile) { 
		tile.shoreup();
	}
	
	
	public void playCard(Card card, Player player) {
		if (((TreasureCard)card).getCardType() == TreasureCardTypes.HELICOPTER) {
			HelicopterView.getInstance().play(card, player);
		}
		else if(((TreasureCard)card).getCardType() == TreasureCardTypes.SANDBAGS) {
			SandbagsView.getInstance().play(player, card);
		}
	}
}
