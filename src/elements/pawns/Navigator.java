package elements.pawns;

import java.util.ArrayList;

import elements.board.Tile;


/**
 * Navigator class
 * 	Represents the navigator pawn (Yellow)
 * 
 * @author Catherine Waechter
 * @version 2.0
 * removed allTiles parameter from all check methods, changed return to ArrayList
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
	
	public Navigator() {
		currentTile = null;
	}
}
