package elements.pawns;

import elements.board.Position;
import elements.board.Tile;
import elements.board.TileNames;
import elements.board.TileStatus;

import java.util.Set;
import java.util.HashSet;


/**
 * Pawn Class
 *  represents a pawn element
 *  
 *  ------------------- doesn't implement swimming yet because I need to check rules -------------------------
 *  ------------------------------- shore up doesn't really make sense? --------------------------------------
 *  
 * @author Catherine Waechter
 * @version 1.0
 *
 */
public abstract class Pawn {





	private Position position; // pawn's position
	
	/**
	 * moveCheck
	 * 	returns the set of tiles that the pawn is allowed to move to
	 * 	All pawn types can move to adjacent tiles
	 * 
	 * @param allTiles - all tiles on the board
	 * @return validTiles
	 */
	Set<Tile> moveCheck(Set<Tile> allTiles){
		Set<Tile> validTiles = new HashSet<Tile>();
		
		for( Tile tile : allTiles) {
			if(tile.getX() == position.getX() +1 || tile.getX() == position.getX() - 1 || tile.getY() == position.getY() +1 || tile.getY() == position.getY() -1) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
			}
		}
		return validTiles;
	}
	
	/**
	 * move
	 * 	move pawn to a destination tile
	 * @param destination
	 */
	void move(Tile destination) {
		position.setPosition(destination.getX(), destination.getY());
	}
	
	/**
	 * shoreupCheck
	 * 	returns the set of tiles that the pawn is allowed to shore up
	 * 
	 * @param allTiles
	 * @return
	 */
	Set<Tile> shoreupCheck(Set<Tile> allTiles){
		Set<Tile> validTiles =  this.moveCheck(allTiles);						// the basic 
		for (Tile tile : validTiles) {
			if (tile.getStatus() != TileStatus.FLOODED) {
				tile.remove();
			}
		}
		return validTiles;
	}
	
	/**
	 * shoreup 
	 * 	pawn shores up a tile 
	 * 	------------------------- does this make sense?? -----------------------------------------------
	 * @param tile
	 */
	void shoreup(Tile tile) {		// should this actually be here...? 
		tile.shoreup();
	}
	
	
	
	// ArrayList<Tile> swim_check()
	Set<Tile> swimCheck(Set<Tile> allTiles){
		Set<Tile> validTiles = new HashSet<Tile>();
		for (Tile tile : allTiles) {
			if(tile.getX() == position.getX() && tile.getY() == position.getY() && tile.getStatus() == TileStatus.REMOVED) {
				validTiles = this.moveCheck(allTiles);				
			}
		}
		return validTiles;
	}











	// bool swim(Tile destination)
	
}
