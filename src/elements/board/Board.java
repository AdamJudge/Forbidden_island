package elements.board;

import java.util.Set;


import java.util.HashSet;
import java.util.Collections;
import java.text.Format;
import java.util.ArrayList;

/**
 * Board class
 * 
 * Creates a board made up of Tile objects
 * 
 * @author Catherine Waechter
 * @version 2.1
 * 	added very basic print (will need to improve)
 * 
 * Date created: 26/10/20
 * Last modified: 03/12/20
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
	private Board() {
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
	
	private ArrayList<Tile> sortTiles(){
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(allTiles);
		
		// bubble sort algorithm
		for (int i=0 ; i< 24 ; i++) {
			for( int j=0; j<24 - 1; j++){
				if(sortedTiles.get(j).compareTo(sortedTiles.get(j+1) ) > 0 ) {
					Tile tempTile = sortedTiles.get(j+1);
					sortedTiles.set(j+1, sortedTiles.get(j));
					sortedTiles.set(j, tempTile);
				}
			}
		}
		
		return sortedTiles;
	}
	
	public String toString() {	// TODO improve this!! suggestion : have "print tile" function
		String boardString;
		ArrayList<Tile> tiles = sortTiles();
		
		//boardString = "\t\t\t\t\t\t|  \t" + tiles.get(0) + "\t|\t" +  tiles.get(1) + "\t|\n";
		boardString = String.format("\t\t\t\t\t\t\t\t| \t %20s \t|\t %20s \t|\n", tiles.get(0), tiles.get(1));
		boardString += String.format("\t\t\t\t|\t %20s  \t|\t %20s \t|\t %20s \t|\t %20s\t |\n", tiles.get(2), tiles.get(3), tiles.get(4), tiles.get(5));
		boardString += String.format("|\t %20s  \t|\t %20s \t|\t %20s \t|\t %20s \t|\t %20s \t |\t %20s \t|\n", tiles.get(6), tiles.get(7), tiles.get(8), tiles.get(9), tiles.get(10), tiles.get(11));
		boardString += String.format("|\t %20s  \t|\t %20s \t|\t %20s \t|\t %20s \t|\t %20s \t |\t %20s \t|\n", tiles.get(12), tiles.get(13), tiles.get(14), tiles.get(15), tiles.get(16), tiles.get(17));
		boardString += String.format("\t\t\t\t|\t %20s  \t|\t %20s \t|\t %20s \t|\t %20s\t |\n", tiles.get(18), tiles.get(19), tiles.get(20), tiles.get(21));
		boardString += String.format("\t\t\t\t\t\t\t\t| \t %20s \t|\t %20s \t|\n", tiles.get(22), tiles.get(23));
		
		return boardString;
	}
}
