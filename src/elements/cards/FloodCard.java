package elements.cards;

import elements.board.TileNames;
import elements.board.Tile;

/**
 * FloodCard class
 * 
 * Represents a flood card
 * 
 * @author Catherine Waechter
 * @version 2.0
 *  changed field to be a tile, not a name. Added getTile
 * 
 * Date Created: 26/10/20
 * Last Modified: 25/11/20
 *
 */
public class FloodCard extends Card{
	
	private Tile tile; 	// associated tile
	
	/**
	 * getName
	 * 	returns name of the card (ie name of corresponding tile)
	 * @return
	 */
	public TileNames getName() {
		return tile.getName();
	}
	
	/**
	 * getTile
	 * 	@return tile associated with the card
	 */
	public Tile getTile() {
		return tile;
	}
	
	/**
	 * FloodCard constructor
	 * 	sets the name of the card
	 * @param name
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
