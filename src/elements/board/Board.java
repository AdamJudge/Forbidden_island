package elements.board;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Board class
 * 
 * Creates a board made up of Tile objects
 * 
 * @author Catherine Waechter
 * @version 1.0
 * Date created: 26/10/20
 * Last modified: 29/10/20
 */
public class Board {
	static private int nbr_tiles =24;	// number of tiles in the board
	private Set<Tile> tiles;
	/**
	 * Board Constructor
	 * 	Creates all tiles in the board
	 */
	public Board() {
		//Set<Tile> tiles;			// set of tiles in the board
		// NB: tiles can be a set because the order is determined by the position field of each tile
		
		this.tiles = new HashSet<Tile>(); // create empty set of tiles
		
		ArrayList<Position> positions = new ArrayList<Position>(); // create empty list of positions
		
		// set x / y for each position
		// check if the position is valid before creating it
		for (int i = 0; i<6; i++) {
			for (int j = 0; j<6; j++) {
				if(Position.validPosition(i, j)) {
					positions.add(new Position(i,j));
				}
			}
		}
		
		Collections.shuffle(positions); // shuffle positions - effectively shuffles the tiles
		
		// create tiles : name is in a set order, but position is randomised
		for (TileNames name : TileNames.values()) {
			this.tiles.add(new Tile(name, positions.get(this.tiles.size())));
		}
	}
}
