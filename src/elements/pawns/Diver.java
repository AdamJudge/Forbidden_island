package elements.pawns;

import elements.board.Tile;
import elements.board.Board;
import elements.board.TileNames;

/**
 * Diver class
 * 	Represents the diver pawn (Black)
 *  Rule clarifications : https://boardgamegeek.com/thread/524604/diver-questions/page/1
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	Added toInitialTile
 * 
 *  Date created: 26/10/20
 *  Last modified: 03/12/20
 */
public class Diver extends Pawn {

	// override 
	// swimCheck
	// moveCheck
	
	
	
	
	public Diver() {
		currentTile = null;
	}
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (iron gate)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.IRON_GATE) {
				move(tile);
				break;
			}
		}
	}
	
}
