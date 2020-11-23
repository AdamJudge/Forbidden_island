package elements.pawns;

import java.util.Set;

import elements.board.Tile;
import elements.board.TileStatus;

/**
 * Explorer class
 * 	Represents the explorer pawn (Green)
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 *  Date created: 26/10/20
 *  Last modified: 09/11/20
 */
public class Explorer extends Pawn {
	/**
	 * moveCheck
	 * 	based on the Pawn moveCheck, but adds diagonal tiles
	 * @param allTiles
	 * @return set of valid tiles
	 */
	@Override
	public Set<Tile> moveCheck(Set<Tile> allTiles){
		Set<Tile> validTiles = super.moveCheck(allTiles); // tiles that are valid for the base pawn are also valid for the explorer
		
		// add diagonal tiles to the set of valid tiles
		for (Tile tile : allTiles) {
			if(currentTile.getY() + 1 == tile.getY()) {		// row of tiles below the pawn
				if(currentTile.getX() + 1 == tile.getX() || currentTile.getX() - 1 == tile.getX()) { 	// diagonal tiles
					validTiles.add(tile);
				}
			}
			else if(currentTile.getY() - 1 == tile.getY()) {		// row of tiles above the pawn
				if(currentTile.getX() + 1 == tile.getX() || currentTile.getX() - 1 == tile.getX()) { 	// diagonal tiles
					validTiles.add(tile);
				}
			}
		}
		
		return validTiles;
	}
	
	/**
	 * shoreupCheck
	 * 	shoreup rules and move rules are the same for the explorer, so use the move method
	 * 		then check tile status.
	 * @param allTiles
	 * @return set of valid tiles
	 */
	@Override
	public Set<Tile> shoreupCheck(Set<Tile> allTiles){			
		Set<Tile> validTiles = moveCheck(allTiles);
		for (Tile tile : validTiles) {
			if (tile.getStatus() != TileStatus.FLOODED) {
				tile.remove();
			}
		}
		return validTiles;
	}
	
	public Explorer() {
		currentTile = null;
	}
}
