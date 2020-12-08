/**
 * Class Name: Hand
 *
 * 	Represents a player's hand
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.3
 * 	Discard to actual discard pile (typo, had it going to deck)
 * 	
 * Creation Date: 22/10/20
 * Last Modified: 08/12/20
 */

package players;

import java.util.HashSet;
import java.util.Set;

import elements.cards.Card;
import elements.cards.TreasureDiscard;

public class Hand {
	protected Set<Card> hand;
	
	public Hand() {
		hand = new HashSet<Card>();
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
		// Add card to hand
	}
	
	public Set<Card> getCards(){
		return hand;
	}
	
	/**
	 * takeCard
	 * 	removes card from hand (do not discard)
	 * @param card
	 * @return card
	 */
	public void takeCard(Card card) {
		hand.remove(card);
	}
	
	/**
	 * discardCard
	 * 	Put card in discard
	 * @param card
	 */
	public void discardCard(Card card) {
		TreasureDiscard.getInstance().addCard(card);
		this.hand.remove(card);
	}
	
	@Override
	public String toString() {
		return ("hand contents: " + hand.toString());
	}
}
