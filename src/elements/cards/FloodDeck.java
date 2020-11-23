package elements.cards;

import elements.board.TileNames;


/**
 * FloodDeck class
 * 
 * Represent the deck of flood cards
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created: 26/10/20
 * Last Modified: 23/11/20
 *
 */
public class FloodDeck extends Deck{
	
	private static FloodDeck floodDeck = null;  	// singleton instance
	
	/**
	 * getInstance
	 * returns FloodDeck instance
	 * @return
	 */
	public static FloodDeck getInstance() {	
		if(floodDeck == null) {
			floodDeck = new FloodDeck();
		}
		return floodDeck;
	}
	
	/**
	 * FloodDeck constructor
	 * 	Adds one card per tile name, then shuffles the deck
	 */
	private FloodDeck() {
		for(TileNames name : TileNames.values()) {
			cards.push(new FloodCard(name));
		}
		
		shuffleDeck();
	}

}
