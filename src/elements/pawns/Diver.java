package elements.pawns;

import elements.board.Tile;
import elements.board.Board;
import elements.board.TileNames;
import elements.board.TileStatus;

import java.util.ArrayList;

/**
 * Diver class
 * 	Represents the diver pawn (Black)
 *  Rule clarifications : https://boardgamegeek.com/thread/524604/diver-questions/page/1
 * 
 * @author Catherine Waechter
 * @version 2.1
 * 	Fixed bugs
 * 
 *  Date created: 26/10/20
 *  Last modified: 09/12/20
 */
public class Diver extends Pawn {

	/**
	 * swimCheck
	 * 
	 * Diver can swim to the nearest tile (flooded or normal). 
	 * Because the diver can turn on a removed or flooded tile (see rule clarification), we look at tiles in a diamond around the tile that sank
	 * 
	 * Check all tiles in "thisRound" to see if they're removed. if they are, add tiles adjacent to "thisRound" to "nextRound"
	 * If all tiles in "thisRound" are removed, "thisRound" becomes "prevRound", and "nextRound" becomes "thisRound", 
	 * extending the distance that the diver will swim. Visualisation for the second iteration below : (first has current tile as "thisRound")
	 * 
	 *	 						nextRound
	 * 				nextRound	thisRound	nextRound	
	 * nextRound	thisRound	prevRound	thisRound	nextRound
	 * 				nextRound	thisRound	nextRound
	 * 							nextRound
	 * 
	 * prevRound is used to avoid duplicates, as the previous round is also adjacent to the current round.
	 * 
	 */
	public ArrayList<Tile> swimCheck(){
		// note: can be done with sets but getNewAdjacent needs ArrayLists because moveCheck needs ArrayList
		ArrayList<Tile> prevRound = new ArrayList<Tile>();	// previous round, to avoid duplicates
		ArrayList<Tile> thisRound = new ArrayList<Tile>();	// round currently being tested
		ArrayList<Tile> nextRound = new ArrayList<Tile>();	// next round to be tested (one tile farther from the diver than thisRound
		ArrayList<Tile> validTiles = new ArrayList<Tile>(); // tiles the diver can land on 
		
		thisRound.add(currentTile);
		
		for(int i = 0; i<6; i++) { // TODO check this value!
			// check if any tiles in this round can be landed on
			for(Tile tile : thisRound) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
			}
			
			// if the search found any tiles, stop here
			if(validTiles.size() != 0) {
				break;
			}
			
			// if not, get next round
			for(Tile tile : thisRound) {
				if(nextRound == null) {		// add all doesn't work with a null list
					nextRound = adjacentNewTiles(tile, prevRound, nextRound);
				}
				else {
					nextRound.addAll(adjacentNewTiles(tile, prevRound, nextRound));
				}
			}
			prevRound = thisRound;
			thisRound = nextRound;
			nextRound = null;
			
		}
		if (validTiles.size()==0) { 	
			notifyAllObservers();
		}
		return validTiles;
	}
	
	/**
	 * moveCheck
	 * 	returns tiles the diver can move to
	 * 
	 * Diver can swim through flooded and removed tiles. 
	 * According to rule clarification, the diver can also turn while swimming through a tile
	 * 
	 * Algorithm explanation: 
	 * 
	 * We start by getting all adjacent tiles to the current tile. These are added to the checkTiles list
	 * The tiles in checkTiles need to be checked for their status
	 * 		- A normal or flooded tile can be landed on, so it is added to validTiles
	 * 		- A removed or flooded tile can be swam through, so we add adjacent tiles to it to checkTiles
	 * 
	 * We check for duplicates before adding to checkTiles (using the getNewAdjacent method). tiles already in checkTiles and in checkedTiles are not added
	 * Once a tile is checked, it is added to checkedTiles to avoid duplicates
	 */
	public ArrayList<Tile> moveCheck(){
		// note : checkTiles needs to be an ArrayList because of loop (can't changed a Collection we iterate, but can't extract objects from a set 
		ArrayList<Tile> checkedTiles = new ArrayList<Tile>();		// tiles we've already "checked"
		ArrayList<Tile> checkTiles = new ArrayList<Tile>();			// tiles to be "checked"
		ArrayList<Tile> validTiles = new ArrayList<Tile>();			// tiles that can be landed on
		// checking a tile = checking status, adding to valid tiles if the diver can land on it, checking adjacent tiles if diver can swim through it
		
		// TODO see if this can be done with smth that isn't arrayList. Should be doable with TreeSet if we can get Tile to be sortable? 
		
		checkTiles = adjacentNewTiles(currentTile, checkedTiles, checkTiles);	// initial list of tiles to check are adjacent to current tile
		checkedTiles.add(currentTile);
		
		while(!checkTiles.isEmpty()) {		// work with tile 0 until there are no more tiles to be checked
			Tile tile = checkTiles.get(0);
			
			if(tile.getStatus() != TileStatus.REMOVED) {	// tile can be landed on unless removed
				validTiles.add(tile);
			}
			if(tile.getStatus() != TileStatus.NORMAL) {		// can swim through a tile unless normal
				checkTiles.addAll(adjacentNewTiles(tile, checkedTiles, checkTiles));
			}
		
			checkTiles.remove(tile);	// tile has been checked
			checkedTiles.add(tile);
		}
		
		// terminates when the edge of the island is reached or the "edge" of the tiles that can be accessed are all normal
		
		return validTiles;
	}
	
	/**
	 * adjacentNewTiles
	 * 
	 * returns tiles adjacent to centerTile that are not in the duplicate lists
	 * returns tiles of any status
	 * 
	 * @param centerTile
	 * @param duplicates1
	 * @param duplicates2
	 * @return
	 */
	private ArrayList<Tile> adjacentNewTiles(Tile centerTile, ArrayList<Tile> duplicates1, ArrayList<Tile> duplicates2){
		ArrayList<Tile> adjacentNewTiles = new ArrayList<Tile>();
		
		boolean dup1 = false, dup2 = false;		// true if a tile is contained in either set. initialise to false
												// if the list is empty, no tile is not contained in the list
		
		for( Tile tile : Board.getInstance().getAllTiles()) { 
			
			// need these if statements because contains(tile) can't be called on an empty list 
			if(duplicates1 != null) {
				dup1 = duplicates1.contains(tile);
			}
			if(duplicates2 != null) {
				dup2 = duplicates2.contains(tile);
			}
			
			// if tile is not in a duplicate list, check position. add to adjacent tiles if adjacent to centerTile
			if( !dup1 && !dup2) {
				if(tile.getX() == centerTile.getX()) {
					if(tile.getY() == (centerTile.getY() + 1) || tile.getY() == (centerTile.getY() - 1)) {
						adjacentNewTiles.add(tile);
					}
				}
				else if(tile.getY() == centerTile.getY()) {
					if(tile.getX() == (centerTile.getX() + 1) || tile.getX() == (centerTile.getX() - 1)) {
						adjacentNewTiles.add(tile);
					}
				}
			}
		}
		
		return adjacentNewTiles;
	
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
