package elements.pawns;

import java.util.Set;
import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;

/**
 * Pilot class
 * 	Represents the pilot pawn (Blue)
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	adjusted to have currentTile field. removed allTiles parameter from all check methods, changed return to ArrayList
 *  Date created: 26/10/20
 *  Last modified: 23/11/20
 */
public class Pilot extends Pawn {

	// field : has_flown (?)
	private boolean has_flown;
	
	public boolean has_flown() {
		return has_flown;
	}
	
	@Override
	public ArrayList<Tile> moveCheck(){
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		ArrayList<Tile> validTiles = new ArrayList<Tile>();		
		
		if(has_flown) {
			validTiles = super.moveCheck();
		}
		else {
			validTiles.addAll(remainingTiles);
		}
		return validTiles;
	}
	
	
	// override check_move
	// override move
	
	public Pilot() {
		currentTile = null;
		has_flown = false;
	}
	
}
