package elements.cards;

import elements.treasures.TreasureNames;

/**
 * TreasureDeck class
 * 
 * Represent the deck of treasure cards
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created: 26/10/20
 * Last Modified: 23/11/20
 *
 */
public class TreasureDeck extends Deck {

	private static TreasureDeck treasureDeck = null; 	// singleton instance
	
	/**
	 * getInstance
	 * @return singleton treasureDeck instance
	 */
	public static TreasureDeck getInstance() {
		if(treasureDeck == null) {
			treasureDeck = new TreasureDeck();
		}
		return treasureDeck;
	}
	
	/**
	 * TreasureDeck Constructor
	 * 
	 * Add required cards to the deck and shuffle it
	 */
	private TreasureDeck() {
		
		// add 5 cards per treasure
		for(TreasureNames treasureType : TreasureNames.values()) {
			for(int cardCount = 0; cardCount < 5; cardCount ++) {
				cards.push(new TreasureCard(TreasureCardTypes.TREASURE, treasureType));
			}
		}
		// add 3 helicopter lift cards
		for(int cardCount = 0; cardCount <3; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.HELICOPTER, TreasureNames.NONE));
		}
		// add 2 sandbag cards
		for(int cardCount = 0; cardCount <2; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.SANDBAGS, TreasureNames.NONE));
		}
		// add 3 waters rise cards
		for(int cardCount = 0; cardCount <3; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.WATERSRISE, TreasureNames.NONE));
		}
		
		shuffleDeck();
		
	}
}
