package elements.pawns;

import java.util.Set;
import java.util.ArrayList;

import elements.board.Tile;
import elements.board.TileNames;
import elements.board.TileStatus;
import elements.board.Board;


/**
 * Explorer class
 * 	Represents the explorer pawn (Green)
 * 
 * @author Catherine Waechter
 * @version 2.1
 * 	Added printout for check functions
 * 
 *  Date created: 26/10/20
 *  Last modified: 03/12/20
 */
public class Explorer extends Pawn {
	/**
	 * moveCheck
	 * 	based on the Pawn moveCheck, but adds diagonal tiles
	 * @param allTiles
	 * @return set of valid tiles
	 */
	
	@Override
	public ArrayList<Tile> moveCheck(){
		ArrayList<Tile> validTiles = super.moveCheck(); // tiles that are valid for the base pawn are also valid for the explorer
		int startPrint = validTiles.size();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		// add diagonal tiles to the set of valid tiles
		for (Tile tile : remainingTiles) {
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
		checkPrint(validTiles, startPrint);
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
	public ArrayList<Tile> shoreupCheck(){			
		ArrayList<Tile> validTiles = moveCheck();
		int startPrint = validTiles.size();
		
		for (Tile tile : validTiles) {
			if (tile.getStatus() != TileStatus.FLOODED) {
				validTiles.remove(tile);
			}
		}
		checkPrint(validTiles, startPrint);
		
		return validTiles;
	}
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (copper gate)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.COPPER_GATE) {
				move(tile);
				break;
			}
		}
	}

	
	public Explorer() {
		currentTile = null;
	}
}
