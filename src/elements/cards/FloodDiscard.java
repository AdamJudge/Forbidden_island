package elements.cards;

import java.util.Stack;

/**
 * FloodDiscard class
 * 	Represents the treasure deck's discard pile
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	now has deckInstance for the flood deck
 * 	no more toDeck method
 * 
 * Date created: 25/11/20
 * Last modified: 19/12/20
 */
public class FloodDiscard extends DiscardPile {

	private static FloodDiscard floodDiscard = null;  	// singleton instance
	
	/**
	 * getInstance
	 * returns FloodDiscard instance
	 * @return
	 */
	public static FloodDiscard getInstance() {	
		if(floodDiscard == null) {
			floodDiscard = new FloodDiscard();
		}
		return floodDiscard;
	}
	
	/**
	 * FloodDiscard constructor
	 */
	private FloodDiscard() {
		discardedCards = new Stack<Card>();
		deckInstance = FloodDeck.getInstance();
	}
	
	/**
	 * tearDown for testing
	 */
	public void tearDown() {
		floodDiscard = null;
	}

}