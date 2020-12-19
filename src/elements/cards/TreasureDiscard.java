package elements.cards;

import java.util.Stack;

/**
 * TreasureDiscard class
 * 	Represents the treasure deck's discard pile
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	now has deckInstance for the treasure deck
 * 	no more toDeck method
 * 
 * Date created: 25/11/20
 * Last modified: 19/12/20
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
	 * TreasureDiscard constructor
	 */
	private TreasureDiscard() {
		discardedCards = new Stack<Card>();
		deckInstance = TreasureDeck.getInstance();
	}
	
	/**
	 * tearDown for testing
	 */
	public void tearDown() {
		treasureDiscard = null;
	}

}
