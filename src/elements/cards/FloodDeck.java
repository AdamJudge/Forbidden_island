package elements.cards;

import elements.board.Board;
import elements.board.Tile;
import mechanics.cardActions.FloodTile;


/**
 * FloodDeck class
 * 
 * Represent the deck of flood cards
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	adjusted for change in flood card (associated tile, not tile name)
 * 	Added draw override
 * 
 * Date Created: 26/10/20
 * Last Modified: 25/11/20
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
	 * draw
	 * 	draws a floodcard and carries out required flooding. Card is discarded in floodTile method
	 * return null - card has been used and/or discarded
	 */
	public Card draw() {
		FloodCard card = (FloodCard)super.draw();
		FloodTile.floodTile(card);
		return null;
	}
	
	/**
	 * FloodDeck constructor
	 * 	Adds one card per tile name, then shuffles the deck
	 */
	private FloodDeck() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			cards.push(new FloodCard(tile));
		}
		
		shuffleDeck();
	}

}
