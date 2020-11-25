package elements.cards;

import java.util.Collections;
import java.util.Stack;

/**
 * DiscardPile class
 * 	represents a discard pile
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created 26/10/20
 * Last modified 25/11/20
 *
 */
public abstract class DiscardPile {

	protected Stack<Card> discardedCards;
	
	
	public abstract void toDeck();
	
	/**
	 * returnToDeck
	 * shuffles the discard pile and returns cards to a deck (general)
	 */
	public void returnToDeck(Deck deckInstance) {
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
	
}
