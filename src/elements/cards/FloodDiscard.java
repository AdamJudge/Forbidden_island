package elements.cards;

import java.util.Stack;

/**
 * FloodDiscard class
 * 	Represents the treasure deck's discard pile
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified: 25/11/20
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
	 * toDeck
	 * 	shuffle and return to correct deck
	 */
	public void toDeck() {
		super.returnToDeck(FloodDeck.getInstance());
	}
	
	
	/**
	 * FloodDiscard constructor
	 */
	private FloodDiscard() {
		discardedCards = new Stack<Card>();
	}

}