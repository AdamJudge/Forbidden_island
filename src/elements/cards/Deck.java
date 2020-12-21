package elements.cards;

import java.util.Collections;

/**
 * Deck class (abstract)
 * 
 * Contains general deck methods
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	general methods moved to CardStack superclass
 * Date Created: 26/10/20
 * Last Modified: 21/12/20
 *
 */
public abstract class Deck extends CardStack {

	/**
	 * shuffleDeck()
	 * 
	 * shuffles the deck
	 */
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	/**
	 * draw
	 * 	returns the top card of the deck
	 * @return
	 */
	public Card draw() {
		Card cardDrawn = cards.pop();
		return cardDrawn;
	}	
	
}
