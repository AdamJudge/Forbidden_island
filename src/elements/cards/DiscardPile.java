package elements.cards;

import java.util.Collections;

/**
 * DiscardPile class
 * 	represents a discard pile
 * 
 * @author Catherine Waechter
 * @version 3.0
 * 	general methods moved to CardStack superclass
 * 
 * Date created 26/10/20
 * Last modified 21/12/20
 *
 */
public abstract class DiscardPile extends CardStack{

	protected Deck deckInstance;

	/**
	 * toDeck
	 * shuffles the discard pile and returns cards to a deck (general)
	 */
	public void toDeck() {
		Collections.shuffle(cards);
		int totalCards = cards.size();
		for(int i = 0; i<totalCards; i++) {
			deckInstance.addCard(cards.pop());
		}
	}
	
	
}
