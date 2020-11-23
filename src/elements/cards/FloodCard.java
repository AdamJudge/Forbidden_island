package elements.cards;

import elements.board.TileNames;

/**
 * FloodCard class
 * 
 * Represents a flood card
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created: 26/10/20
 * Last Modified: 23/11/20
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
}
