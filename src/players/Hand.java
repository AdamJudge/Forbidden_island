package players;

import java.util.ArrayList;

import elements.cards.Card;
import elements.cards.TreasureDiscard;
import mechanics.TurnController;

/**
 * Class Name: Hand
 *
 * 	Represents a player's hand
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.4
 * 	AddCard checks size of hand
 * 	
 * Creation Date: 22/10/20
 * Last Modified: 16/12/20
 */
public class Hand {
	protected ArrayList<Card> hand;
	
	
	/**
	 * addCard
	 * when card is added to hand, check size of hand and prompt 
	 * @param card to add to hand
	 */
	public void addCard(Card card) {
		this.hand.add(card);
		if(hand.size() >= 6) {
			TurnController.getInstance().doDiscard();
		}
	}
	/**
	 * getCards
	 * @return cards in hand
	 */
	public ArrayList<Card> getCards(){
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
	
	public Hand() {
		hand = new ArrayList<Card>();
	}
	
	@Override
	public String toString() {
		return ("hand contents: " + hand.toString());
	}
}
