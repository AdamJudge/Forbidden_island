/**
 * Class Name: Hand
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package players;

import java.util.HashSet;
import java.util.Set;

import elements.cards.Card;

public class Hand {
	protected Set<Card> hand;
	
	public Hand() {
		hand = new HashSet<Card>();
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
		// Add card to hand
	}
	
	public void discardCard(Card card) {
		this.hand.remove(card);
		// Remove card from hand
	}
	
	@Override
	public String toString() {
		return hand.toString();
	}
}
