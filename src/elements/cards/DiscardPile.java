package elements.cards;

import java.util.Collections;
import java.util.Stack;

/**
 * DiscardPile class
 * 	represents a discard pile
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	now has a deckInstance field
 * 	returnToDeck renamed to toDeck (because it now has the same use as the old toDeck function)
 * 
 * Date created 26/10/20
 * Last modified 19/12/20
 *
 */
public abstract class DiscardPile {

	protected Stack<Card> discardedCards;
	protected Deck deckInstance;
	
	// TODO Cat make a "CardStack" abstract class with add, shuffle, getSize, isEmpty 
	
	/**
	 * toDeck
	 * shuffles the discard pile and returns cards to a deck (general)
	 */
	public void toDeck() {
		Collections.shuffle(discardedCards);
		int totalCards = discardedCards.size();
		for(int i = 0; i<totalCards; i++) {
			deckInstance.addCard(discardedCards.pop());
		}
	}
	
	/**
	 * addCard
	 * 	Adds a card to the discard pile
	 * @param card
	 */
	public void addCard(Card card) {
		discardedCards.add(card);
	}
	
	/**
	 * getSize
	 * @return size of discard pile
	 */
	public int getSize() {
		return discardedCards.size();
	}
	
	public abstract void tearDown();
}
