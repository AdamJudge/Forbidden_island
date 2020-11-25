package elements.cards;

import elements.board.TileNames;

/**
 * FloodCard class
 * 
 * Represents a flood card
 * 
 * @author Catherine Waechter
 * @version 1.1
 *  Added toString
 * 
 * Date Created: 26/10/20
 * Last Modified: 25/11/20
 *
 */
public class FloodCard extends Card{
	
	private TileNames name; 	// associated tile
	
	/**
	 * getName
	 * 	returns name of the card (ie name of corresponding tile)
	 * @return
	 */
	public TileNames getName() {
		return name;
	}
	
	/**
	 * FloodCard constructor
	 * 	sets the name of the card
	 * @param name
	 */
	public FloodCard(TileNames name) {
		this.name = name;
	}
	
	/**
	 * toString
	 * 	Returns the name of corresponding tile
	 */
	@Override
	public String toString() {
		return name.name();
	}
}
