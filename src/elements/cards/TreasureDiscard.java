package elements.cards;

import java.util.Stack;

/**
 * TreasureDiscard class
 * 	Represents the treasure deck's discard pile
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified: 25/11/20
 */
public class TreasureDiscard extends DiscardPile {

	private static TreasureDiscard treasureDiscard = null;  	// singleton instance
	
	/**
	 * getInstance
	 * returns TreasureDiscard instance
	 * @return
	 */
	public static TreasureDiscard getInstance() {	
		if(treasureDiscard == null) {
			treasureDiscard = new TreasureDiscard();
		}
		return treasureDiscard;
	}
	
	/**
	 * toDeck
	 * 	shuffle and return to correct deck
	 */
	public void toDeck() {
		super.returnToDeck(TreasureDeck.getInstance());
	}
	
	
	/**
	 * TreasureDiscard constructor
	 */
	private TreasureDiscard() {
		discardedCards = new Stack<Card>();
	}

}
