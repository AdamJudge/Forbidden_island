package elements.board;

import java.util.Set;


import java.util.HashSet;
import java.util.Collections;
import java.text.Format;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import elements.treasures.*;
import players.PlayerList;
import players.Player;

/**
 * Board class
 * 
 * Creates a board made up of Tile objects
 * 
 * @author Catherine Waechter
 * @version 3.0
 * 	Board now contains set of treasures
 * 
 * Date created: 26/10/20
 * Last modified: 08/12/20
 */
public class Board {
	private static Board board = null;
	private Set<Tile> allTiles;
	private ArrayList<Tile> sortedTiles;
	private Map<TreasureNames, Treasure> allTreasures;
	
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
		// Create the 4 treasures
		allTreasures = new HashMap<TreasureNames, Treasure>();
		for(TreasureNames name : TreasureNames.values()) {
			allTreasures.put(name, new Treasure(name));
		}
		
		this.allTiles = new HashSet<Tile>(); // create empty set of tiles
		// NB: tiles can be a set because the order is determined by the position field of each tile
		
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
			this.allTiles.add(new Tile(name, positions.get(this.allTiles.size()), allTreasures));
		}
		
		sortedTiles = sortTiles();
	}
	
	private ArrayList<Tile> sortTiles(){
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		sorted.addAll(allTiles);
		
		// bubble sort algorithm
		for (int i=0 ; i< 24 ; i++) {
			for( int j=0; j<24 - 1; j++){
				if(sorted.get(j).compareTo(sorted.get(j+1) ) > 0 ) {
					Tile tempTile = sorted.get(j+1);
					sorted.set(j+1, sorted.get(j));
					sorted.set(j, tempTile);
				}
			}
		}
		
		return sorted;
	}
	
	public Map<TreasureNames, Treasure> getTreasures(){
		return allTreasures;
	}
	
	private String printTileName(int index) {
		if(sortedTiles.get(index).getStatus() == TileStatus.REMOVED) {
			return " ";
		}
		else if(sortedTiles.get(index).getStatus() == TileStatus.FLOODED) {
			return "**" + sortedTiles.get(index).toString() + "**";
		}
		else return sortedTiles.get(index).toString();
	}
	
	private String printTileTreasure(int index) {
		Treasure treasure;
	
		if(sortedTiles.get(index).getStatus() == TileStatus.REMOVED) {
			return " ";
		}
		treasure = sortedTiles.get(index).getTreasure();
		if (treasure == null) {
			return " ";
		}
		else { 
			System.out.println(treasure.toString());
			return treasure.toString();}
	}
	
	// Get shorthand of pawn name for board print
	private String printPawn(int index) {
		String pawnsString = " ";
		for (Player player : PlayerList.getInstance().getPlayers()) {
			if(player.getPawn().getTile() == sortedTiles.get(index)) {
				pawnsString += player.getPawn().toString().substring(0,3).toUpperCase() + " ";
			}
		}
		return pawnsString;
	}
	
	private String printDifficulty() {
		return WaterLevel.getInstance().getDifficulty();
	}
	private int printWaterLevel() {
		return WaterLevel.getInstance().getLevel();
	}
	
	public String toString() {	// TODO improve this!! suggestion : have "print tile" function
		String boardString;
		
		boardString =  String.format("\t  ____________________________                 _____________________________________________________\n");
		boardString += String.format("\t |                            |               |        %18s|        %18s|\n" , printTileTreasure(0), printTileTreasure(1));
		boardString += String.format("\t |   Difficulty:%12s  |               |                          |                          |\n",printDifficulty());
		boardString += String.format("\t |  Water Level:\t  %2d  |               | %22s   | %22s   |\n", printWaterLevel(), printTileName(0), printTileName(1));
		boardString += String.format("\t |____________________________|               | %22s   | %22s   |\n", printPawn(0), printPawn(1));
		boardString += String.format("\t\t\t\t\t\t      |                          |                          |\n");
		boardString += String.format("\t\t\t    __________________________|__________________________|__________________________|__________________________\n");
		boardString += String.format("\t\t\t   |        %18s|        %18s|        %18s|        %18s|\n", printTileTreasure(2), printTileTreasure(3), printTileTreasure(4), printTileTreasure(5));
		boardString += String.format("\t\t\t   |                          |                          |                          |                          |\n");
		boardString += String.format("\t\t\t   | %22s   | %22s   | %22s   | %22s   |\n", printTileName(2), printTileName(3), printTileName(4), printTileName(5));
		boardString += String.format("\t\t\t   | %22s   | %22s   | %22s   | %22s   |\n", printPawn(2), printPawn(3), printPawn(4), printPawn(5));
		boardString += String.format("                           |                          |                          |                          |                          |                              \n");
		boardString += String.format(" __________________________|__________________________|__________________________|__________________________|__________________________|__________________________\n");
		boardString += String.format("|        %18s|        %18s|        %18s|        %18s|        %18s|        %18s|\n", printTileTreasure(6), printTileTreasure(7), printTileTreasure(8), printTileTreasure(9), printTileTreasure(10), printTileTreasure(11));
		boardString += String.format("|                          |                          |                          |                          |                          |                          |\n");
		boardString += String.format("| %22s   | %22s   | %22s   | %22s   | %22s   | %22s   |\n", printTileName(6), printTileName(7), printTileName(8), printTileName(9), printTileName(10), printTileName(11));
		boardString += String.format("| %22s   | %22s   | %22s   | %22s   | %22s   | %22s   |\n", printPawn(6), printPawn(7), printPawn(8), printPawn(9), printPawn(10), printPawn(11));
		boardString += String.format("|                          |                          |                          |                          |                          |                          |\n");
		boardString += String.format("|__________________________|__________________________|__________________________|__________________________|__________________________|__________________________|\n");
		boardString += String.format("|        %18s|        %18s|        %18s|        %18s|        %18s|        %18s|\n", printTileTreasure(12), printTileTreasure(13), printTileTreasure(14), printTileTreasure(15), printTileTreasure(16), printTileTreasure(17));
		boardString += String.format("|                          |                          |                          |                          |                          |                          |\n");
		boardString += String.format("| %22s   | %22s   | %22s   | %22s   | %22s   | %22s   |\n", printTileName(12), printTileName(13), printTileName(14), printTileName(15), printTileName(16), printTileName(17));
		boardString += String.format("| %22s   | %22s   | %22s   | %22s   | %22s   | %22s   |\n", printPawn(12), printPawn(13), printPawn(14), printPawn(15), printPawn(16), printPawn(17));
		boardString += String.format("|                          |                          |                          |                          |                          |                          |\n");
		boardString += String.format("|__________________________|__________________________|__________________________|__________________________|__________________________|__________________________|\n");
		boardString += String.format("\t\t\t   |        %18s|        %18s|        %18s|        %18s|\n", printTileTreasure(18), printTileTreasure(19), printTileTreasure(20), printTileTreasure(21));
		boardString += String.format("\t\t\t   |                          |                          |                          |                          |\n");
		boardString += String.format("\t\t\t   | %22s   | %22s   | %22s   | %22s   |\n", printTileName(18), printTileName(19), printTileName(20), printTileName(21));
		boardString += String.format("\t\t\t   | %22s   | %22s   | %22s   | %22s   |\n", printPawn(18), printPawn(19), printPawn(20), printPawn(21));
		boardString += String.format("\t\t\t   |                          |                          |                          |                          |\n");
		boardString += String.format("\t\t\t   |__________________________|__________________________|__________________________|__________________________|\n");
		boardString += String.format("\t\t\t\t\t\t      |        %18s|        %18s|\n", printTileTreasure(22), printTileTreasure(23));
		boardString += String.format("\t\t\t\t\t\t      |                          |                          |\n");
		boardString += String.format("\t\t\t\t\t\t      | %22s   | %22s   |\n", printTileName(22), printTileName(23));
		boardString += String.format("\t\t\t\t\t\t      | %22s   | %22s   |\n", printPawn(22), printPawn(23));
		boardString += String.format("\t\t\t\t\t\t      |                          |                          |\n");
		boardString += String.format("\t\t\t\t\t\t      |__________________________|__________________________|\n");

		return boardString;
	}
}
