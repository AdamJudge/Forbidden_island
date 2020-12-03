package elements.pawns;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;

/**
 * Engineer class
 * 	Represents the engineer pawn (Red)
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	Added toInitialTile
 * 
 *  Date created: 26/10/20
 *  Last modified: 03/12/20
 */
public class Engineer extends Pawn {

	// normal pawn
	public Engineer() {
		currentTile = null;
	}
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (bronze gate)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.BRONZE_GATE) {
				move(tile);
				break;
			}
		}
	}

}
