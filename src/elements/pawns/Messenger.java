package elements.pawns;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;

/**
 * Messenger class
 * 	Represents the messenger pawn (Silver)
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	Added toInitialTile
 * 
 *  Date created: 26/10/20
 *  Last modified: 03/12/20
 */
public class Messenger extends Pawn {

	// normal pawn (?)
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (silver gate)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.SILVER_GATE) {
				move(tile);
				break;
			}
		}
	}
	
	public Messenger() {
		currentTile = null;
	}
}
