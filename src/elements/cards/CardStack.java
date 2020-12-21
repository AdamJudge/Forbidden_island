package elements.cards;

import java.util.Stack;

/**
 * CardStack class (abstract)
 * 
 * Contains general methods for a pile of cards (Deck or discard)
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 	
 * Date Created: 21/12/20
 * Last Modified: 21/12/20
 *
 */
public abstract class CardStack {

	
	protected Stack<Card> cards;
	
	/**
	 * addCard
	 * 	Adds a card to the top of the stack
	 * @param card
	 */
	public void addCard(Card card) {
		cards.push(card);
	}
	/**
	 * getSize
	 * @return size of stack
	 */
	public int getSize() {
		return cards.size();
	}
	
	/**
	 * isEmpty
	 * 	returns whether or not the stack is empty
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
