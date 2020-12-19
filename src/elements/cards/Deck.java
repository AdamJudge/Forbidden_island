package elements.cards;

import java.util.Collections;
import java.util.Stack;
import java.io.IOException;

/**
 * Deck class (abstract)
 * 
 * Contains general deck methods
 * 
 * @author Catherine Waechter
 * @version 1.3
 * 	Added getSize method
 * Date Created: 26/10/20
 * Last Modified: 16/12/20
 *
 */
public abstract class Deck {

	protected Stack<Card> cards;
	
	/**
	 * addCard
	 * 	adds a card to the deck
	 * @param card
	 */
	public void addCard(Card card) {
		cards.push(card);
	}
	
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
	public Card draw() throws IOException {
		Card cardDrawn = cards.pop();
		return cardDrawn;
	}
	
	/**
	 * getSize
	 * @return size of deck
	 */
	public int getSize() {
		return cards.size();
	}
	
	/**
	 * isEmpty
	 * 	returns whether or not the deck is empty
	 * @return
	 */
	public boolean isEmpty() {
		if (cards.size() == 0) {
			return true;
		}
		else return false;
	}	
	
	public abstract void tearDown();
	
}
