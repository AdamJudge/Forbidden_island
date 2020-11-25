package elements.cards;

import elements.treasures.TreasureNames;
import mechanics.cardActions.WatersRise;

/**
 * TreasureDeck class
 * 
 * Represent the deck of treasure cards
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	draw overridden to handle waters rise
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
	 * draw
	 * 	if the card drawn is a waters rise card, it is played, and return null
	 *	otherwise the card is returned
	 *	@return card
	 */
	public Card draw() {
		TreasureCard card = (TreasureCard)super.draw();
		if(card.getCardType() == TreasureCardTypes.WATERSRISE) {
			WatersRise.play(card);
			return null;
		}
		else return card;
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
