package elements.pawns;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import observers.Subject;

import java.util.Set;
import java.util.ArrayList;


/**
 * Pawn Class
 *  represents a pawn element
 *    
 * @author Catherine Waechter, Adam Judge
 * @version 2.4
 * 	Fixed moveCheck (wasn't outputting the correct tiles)
 *	Fixed shoreupCheck 
 *	Added Observer Notification
 *
 *	Date created: 26/10/20
 *	Last modified: 04/12/20
 */
public abstract class Pawn extends Subject{

	protected Tile currentTile; // tile the pawn is on
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (gold gate)
	 */
	public abstract void toInitialTile();
	
	/**
	 * moveCheck
	 * 	returns the list of tiles that the pawn is allowed to move to
	 * 	All pawn types can move to adjacent tiles
	 * 
	 * @param allTiles - all tiles on the board
	 * @return validTiles
	 */
	public ArrayList<Tile> moveCheck(){
		return getAdjacent();
	}
	
	/**
	 * getAdjacent
	 * 	returns list of tiles adjacent to the pawn
	 * 	Only for use by pawns
	 * 
	 * @return
	 */
	protected ArrayList<Tile> getAdjacent(){
		ArrayList<Tile> validTiles = new ArrayList<Tile>();
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		for( Tile tile : remainingTiles) {
			if(tile.getX() == currentTile.getX()) {
				if(tile.getY() == (currentTile.getY() + 1) || tile.getY() == (currentTile.getY() - 1)) {
					if(tile.getStatus() != TileStatus.REMOVED) {
						validTiles.add(tile);
					}
				}
			}
			else if(tile.getY() == currentTile.getY()) {
				if(tile.getX() == (currentTile.getX() + 1) || tile.getX() == (currentTile.getX() - 1)) {
					if(tile.getStatus() != TileStatus.REMOVED) {
						validTiles.add(tile);
					}
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
		ArrayList<Tile> validTiles =  getAdjacent();		
		for (int i = validTiles.size()-1; i>=0; i--) {
			Tile tile = validTiles.get(i);
			if (tile.getStatus() != TileStatus.FLOODED) {
				validTiles.remove(tile);
			}
		}
		return validTiles;
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
		if (validTiles.size() == 0) { 
			//Can't swim
			notifyAllObservers();
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
