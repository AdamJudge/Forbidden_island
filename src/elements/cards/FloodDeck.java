package elements.cards;

import elements.board.Board;
import elements.board.Tile;
import mechanics.cardActions.FloodTile;
import java.util.Stack;


/**
 * FloodDeck class
 * 
 * Represent the deck of flood cards
 * 
 * @author Catherine Waechter
 * @version 2.1
 * 	Initialised cards
 * 
 * Date Created: 26/10/20
 * Last Modified: 30/11/20
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
	@Override
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
		cards = new Stack<Card>();
		
		for(Tile tile : Board.getInstance().getAllTiles()) {
			cards.push(new FloodCard(tile));
		}
		
		shuffleDeck();
	}

}
