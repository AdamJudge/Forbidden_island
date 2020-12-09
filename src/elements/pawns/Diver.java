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
	// moveCheck
	
	public ArrayList<Tile> moveCheck(){
		
		Set<Tile> checkedTiles = new HashSet<Tile>();
		ArrayList<Tile> checkTiles = new ArrayList<Tile>();
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		
		// TODO see if this can be done with smth that isn't arrayList
		
		// add adjacent to check (even removed)
		// 
		// while check tiles not empty
		// 	for tile in check tiles
		// 		if not removed, add to valid
		//		if not normal 	
		//			add adjacent to checktiles if not checked
		// 		
		//  remove from check to checked
		
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
	
	private ArrayList<Tile> adjacentNewTiles(Tile centerTile, Set<Tile> notNewTiles){
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
