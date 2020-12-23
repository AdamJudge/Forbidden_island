package mechanics.cardActions;

import java.util.ArrayList;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.cards.FloodCard;
import elements.cards.FloodDiscard;
import elements.pawns.Pawn;
import elements.pawns.Pilot;
import mechanics.GamePlay;
import mechanics.actions.ActionController;
import players.Player;
import players.PlayerList;

public class CardActionController {
	
	private static CardActionController cardController = null;
	private ActionController actionController;
	
	/**
	 * getInstance
	 * 	get singleton instance of CardActionController
	 * @return cardController (singleton instance)
	 */
	public static CardActionController getInstance() {
		if(cardController == null) { 
			cardController = new CardActionController();
		}
		return cardController;
	}
	
	public boolean tryLeave() {
		GamePlay.getInstance().tryLeave();
		//If unable to leave
		if (!GamePlay.getInstance().canLeave()) {
			return false;
		}
		return true;
	}
	
	public boolean pilotFlown(Player player) {
		Pawn pawn = player.getPawn();
		if(pawn instanceof Pilot) {
			return ((Pilot)pawn).getHasFlown();
		}
		else return false;
	}
	
	public void pilotFixFlown(Player player, boolean flown) {
		Pawn pawn = player.getPawn();
		if(!flown && pawn instanceof Pilot) {
			((Pilot)pawn).resetHasFlown();
		}
	}
	
	public ArrayList<Tile> getHelicopterTiles(Player player){
		Pawn pawn = player.getPawn();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(remainingTiles);
		sortedTiles.remove(pawn.getTile());	// pawn can't fly to its current tile
		return sortedTiles;
	}
	
	/**
	 * getFloodedTiles
	 * 	returns list of tiles that can be shored up
	 * @return floodedTiles
	 */
	public ArrayList<Tile> getFloodedTiles(){
		ArrayList<Tile> floodedTiles = new ArrayList<Tile>();
		
		for(Tile tile : Board.getInstance().getRemainingTiles()) {
			if(tile.getStatus() == TileStatus.FLOODED) {
				floodedTiles.add(tile);
			}
		}
		
		return floodedTiles;
	}
	
	/**
	 * floodTile
	 * 	if tile is normal, flood the tile and add card to discard pile
	 * 	if tile is flooded, remove tile, and remove card (ie don't add to discard)
	 * @param card
	 */
	public void floodTile(FloodCard card) {
		
		Tile tile = card.getTile();
		
		
		if(tile.getStatus() == TileStatus.NORMAL) {
			tile.flood();
			FloodDiscard.getInstance().addCard(card);
		}
		
		
		
		else if(tile.getStatus() == TileStatus.FLOODED) {
			
			
			tile.remove();
			ArrayList<Player> playersToSwim = new ArrayList<Player>();
			for (Player player : PlayerList.getInstance().getPlayers()) {
				if(player.getPawn().getTile() == tile) {
					playersToSwim.add(player);
				}
			}
			if(playersToSwim.size() != 0) {
				actionController.doSwim(playersToSwim);
			}
		}
	}
	
	public void setup(ActionController actionController) {
		this.actionController = actionController;
	}
	
	
}
