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
 * @version 2.0
 * 	Board is now a singleton class
 * 	added getAllTiles and getReminingTiles methods
 * 
 * Date created: 26/10/20
 * Last modified: 23/11/20
 */
public class Board {
	private static Board board = null;
	private Set<Tile> allTiles;
	
	/**
	 * getAllTiles
	 * 	returns all tiles (even if removed)
	 * @return
	 */
	public Set<Tile> getAllTiles(){
		return allTiles;
	}
	
	/**
	 * getRemainingTiles 
	 * 	returns tiles that are still on the board (not removed)
	 * @return 
	 */
	public Set<Tile> getRemainingTiles(){
		Set<Tile> remainingTiles = new HashSet<Tile>();
		for (Tile tile : allTiles) {
			if(tile.getStatus() != TileStatus.REMOVED) {
				remainingTiles.add(tile);
			}
		}
		return remainingTiles;
	}
	
	/**
	 * getInstance
	 * 	returns single instance of board
	 * @return board
	 */
	public static Board getInstance() {
		if(board == null) {
			board = new Board();
		}
		return board;
	}
	
	/**
	 * Board Constructor
	 * 	Creates all tiles in the board
	 */
	public Board() {
		//Set<Tile> tiles;			// set of tiles in the board
		// NB: tiles can be a set because the order is determined by the position field of each tile
		
		this.allTiles = new HashSet<Tile>(); // create empty set of tiles
		
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
			this.allTiles.add(new Tile(name, positions.get(this.allTiles.size())));
		}
	}
}
