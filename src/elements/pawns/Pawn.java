package elements.pawns;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;

import java.util.Set;
import java.util.ArrayList;


/**
 * Pawn Class
 *  represents a pawn element
 *    
 * @author Catherine Waechter
 * @version 2.1 - private field is a tile, not a position. check methods no longer take in parameters, and return ArrayLists, not Sets
 *
 *	Date created: 26/10/20
 *	Last modified: 23/11/20
 */
public abstract class Pawn {

	protected Tile currentTile; // tile the pawn is on
	
	/**
	 * moveCheck
	 * 	returns the set of tiles that the pawn is allowed to move to
	 * 	All pawn types can move to adjacent tiles
	 * 
	 * @param allTiles - all tiles on the board
	 * @return validTiles
	 */
	public ArrayList<Tile> moveCheck(){
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		for( Tile tile : remainingTiles) {
			if(tile.getX() == currentTile.getX() +1 || tile.getX() == currentTile.getX() - 1 || tile.getY() == currentTile.getY() +1 || tile.getY() == currentTile.getY() -1) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
			}
		}
		return validTiles;
	}
	
	/**
	 * move
	 * 	move pawn to a destination tile (used for swimming as well)
	 * @param destination
	 */
	public void move(Tile destination) {
		currentTile = destination;
	}
	
	/**
	 * shoreupCheck
	 * 	returns the set of tiles that the pawn is allowed to shore up
	 * 
	 * @param allTiles
	 * @return
	 */
	public ArrayList<Tile> shoreupCheck(){
		ArrayList<Tile> validTiles =  this.moveCheck();					
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
	public void shoreup(Tile tile) {		// should this actually be here...? 
		tile.shoreup();
	}
	
	
	/**
	 * swimCheck 
	 * 	returns the set of tiles the pawn is allowed to swim to. checks status of tile the pawn is on
	 * 
	 * @param allTiles
	 * @return
	 */
	public ArrayList<Tile> swimCheck(){
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		if(currentTile.getStatus() == TileStatus.REMOVED) {
			validTiles = moveCheck();				
		}
		return validTiles;
	}

	/**
	 * getTile 
	 * 	returns the tile the pawn is on
	 * @return currentTile
	 */
	public Tile getTile() {
		return currentTile;
	}
	
	/**
	 * toString
	 * Prints name of the pawn (ie the pawn type)
	 */
	@Override
	public String toString() {
		return (this.getClass().getSimpleName());
	}
}
