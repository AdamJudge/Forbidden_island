package elements.pawns;

import elements.board.Tile;
import elements.board.Board;
import elements.board.TileNames;
import elements.board.TileStatus;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * Diver class
 * 	Represents the diver pawn (Black)
 *  Rule clarifications : https://boardgamegeek.com/thread/524604/diver-questions/page/1
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	Added correct moveCheck
 * 
 *  Date created: 26/10/20
 *  Last modified: 09/12/20
 */
public class Diver extends Pawn {

	// override 
	// swimCheck

	public ArrayList<Tile> swimCheck(){
		ArrayList<Tile> prevRound = new ArrayList<Tile>();
		ArrayList<Tile> thisRound = new ArrayList<Tile>();
		ArrayList<Tile> nextRound = new ArrayList<Tile>();
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		
		thisRound.add(currentTile);
		
		for(int i = 0; i<6; i++) { // TODO check this value!
			// check if any tiles in this round can be landed on
			for(Tile tile : thisRound) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
			}
			// if prev search found any tiles, stop here
			if(validTiles.size() != 0) {
				break;
			}
			
			// if not, get next round
			for(Tile tile : thisRound) {
				prevRound.addAll(nextRound);
				nextRound.addAll(adjacentNewTiles(tile, prevRound));
			}
			prevRound = thisRound;
			thisRound = nextRound;
			nextRound = null;
			
		}
		
		return validTiles;
	}
	
	public ArrayList<Tile> moveCheck(){
		
		ArrayList<Tile> checkedTiles = new ArrayList<Tile>();
		ArrayList<Tile> checkTiles = new ArrayList<Tile>();
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		
		// TODO see if this can be done with smth that isn't arrayList
		
		checkTiles = adjacentNewTiles(currentTile, checkedTiles);
		
		while(!checkTiles.isEmpty()) {
			Tile tile = checkTiles.get(0);
			
			if(tile != currentTile) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
				if(tile.getStatus() != TileStatus.NORMAL) {
					checkTiles.addAll(adjacentNewTiles(tile, checkedTiles));
				}
			}

			checkTiles.remove(tile);
			checkedTiles.add(tile);
		}
		
		return validTiles;
	}
	
	private ArrayList<Tile> adjacentNewTiles(Tile centerTile, ArrayList<Tile> notNewTiles){
		ArrayList<Tile> adjacentNewTiles = new ArrayList<Tile>();
		Set<Tile> allTiles = Board.getInstance().getAllTiles();
		for( Tile tile : allTiles) {
			if(!notNewTiles.contains(tile)) {
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
