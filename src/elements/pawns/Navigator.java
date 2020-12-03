package elements.pawns;

import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;


/**
 * Navigator class
 * 	Represents the navigator pawn (Yellow)
 * 
 * @author Catherine Waechter
 * @version 2.1
 * added toInitialTile
 * 
 *  Date created: 26/10/20
 *  Last modified: 23/11/20
 */
public class Navigator extends Pawn {

	/**
	 * moveOtherCheck
	 * 	Returns set of tiles another pawn can move to (only adjacent tiles) 
	 * ------------------------------------- NOTE: can do this twice for one action, but will need to be checked again after moving once ------------------!!
	 * @param otherPawn - pawn we want the navigator to move
	 * @param allTiles 	
	 * @return set of valid tiles for the other pawn 
	 */
	public ArrayList<Tile> moveOtherCheck(Pawn otherPawn) {
		ArrayList<Tile> validTiles = otherPawn.moveCheck(); 	// only adjacent tiles, regardless of pawn type
		return validTiles;
	}
	
	/**
	 * moveOther
	 * 	moves given pawn to a destination tile
	 * @param otherPawn
	 * @param destination
	 */
	public void moveOther(Pawn otherPawn, Tile destination) {
		otherPawn.move(destination);
	}
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (gold gate)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.GOLD_GATE) {
				move(tile);
				break;
			}
		}
	}

	public Navigator() {
		currentTile = null;
	}
}
