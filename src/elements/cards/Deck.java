package elements.cards;

import java.util.Collections;
import java.util.Stack;

/**
 * Deck class (abstract)
 * 
 * Contains general deck methods
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	Added printout for draw method
 * Date Created: 26/10/20
 * Last Modified: 25/11/20
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
	public Card draw() {
		System.out.println("Card Drawn: " + cards.peek());
		return cards.pop();
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
	
}
