package elements.cards;

import java.util.Stack;

import elements.treasures.TreasureNames;
import mechanics.cardActions.WatersRise;
import setup.GameSetup;

/**
 * TreasureDeck class
 * 
 * Represent the deck of treasure cards
 * 
 * @author Catherine Waechter
 * @version 2.1
 * 	Initialised cards
 * 	Made waters rise cards have effect only outside of setup
 * 
 * Date Created: 26/10/20
 * Last Modified: 30/11/20
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
		
		// if in setup mode, waters rise cards drawn are returned to the deck, which is then shuffled
		if(GameSetup.getInstance().getSetup()) {
			while(card.getCardType() == TreasureCardTypes.WATERSRISE) {
				addCard(card);
				shuffleDeck();
				card = (TreasureCard)super.draw();
			}
			return card;
		}
		
		// outside of setup, waters rise cards are played (discarded in play method)
		else if(card.getCardType() == TreasureCardTypes.WATERSRISE) {
			WatersRise.play(card);
			return null;
		}
		
		// if a regular card is drawn outside of setup
		else return card;
	}
	
	/**
	 * TreasureDeck Constructor
	 * 
	 * Add required cards to the deck and shuffle it
	 */
	private TreasureDeck() {
		cards = new Stack<Card>();
		
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
