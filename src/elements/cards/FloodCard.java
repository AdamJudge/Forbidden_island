package elements.cards;

import elements.board.Tile;

/**
 * FloodCard class
 * 
 * Represents a flood card. A flood card has an associated tile, which is the one with the same name as the one on the card
 * 
 * @author Catherine Waechter
 * @version 2.1
 *  removed getName 
 * 
 * Date Created: 26/10/20
 * Last Modified: 19/12/20
 *
 */
public class FloodCard extends Card{
	
	private Tile tile; 	// associated tile
	
	/**
	 * getTile
	 * 	@return tile associated with the card
	 */
	public Tile getTile() {
		return tile;
	}
	
	/**
	 * FloodCard constructor
	 * 	sets tile associated with the card
	 * @param tile associated 
	 */
	public FloodCard(Tile tile) {
		this.tile = tile;
	}
	
	/**
	 * toString
	 * 	Returns the name of corresponding tile
	 */
	@Override
	public String toString() {
		return tile.toString();
	}
}
