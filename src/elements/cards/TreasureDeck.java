package elements.cards;

import java.util.Stack;
import java.util.Map;

import elements.board.Board;
import elements.treasures.*;
import mechanics.cardActions.WatersRise;
import mechanics.setup.GameSetup;

/**
 * TreasureDeck class
 * 
 * Represent the deck of treasure cards
 * 	Singleton as there is only one treasure deck in the game
 * 
 * @author Catherine Waechter
 * @version 2.5
 * 	Added tearDown
 * 
 * Date Created: 26/10/20
 * Last Modified: 16/12/20
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
		}
		
		// outside of setup, waters rise cards are played (discarded in play method)
		else if(card.getCardType() == TreasureCardTypes.WATERSRISE) {
			WatersRise.play(card);
		}
		
		if (cards.isEmpty()) {
			TreasureDiscard.getInstance().toDeck();
		}
		return card;
	}
	
	/**
	 * TreasureDeck Constructor
	 * 
	 * Add required cards to the deck and shuffle it
	 */
	private TreasureDeck() {
		cards = new Stack<Card>();
		
		// add 5 cards per treasure
		for(Map.Entry<TreasureNames, Treasure> entry : Board.getInstance().getTreasures().entrySet()) {
			for(int cardCount = 0; cardCount < 5; cardCount ++) {
				cards.push(new TreasureCard(TreasureCardTypes.TREASURE, entry.getValue()));
			}
		}
		// add 3 helicopter lift cards
		for(int cardCount = 0; cardCount <3; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.HELICOPTER, null));
		}
		// add 2 sandbag cards
		for(int cardCount = 0; cardCount <2; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.SANDBAGS, null));
		}
		// add 3 waters rise cards
		for(int cardCount = 0; cardCount <3; cardCount ++) {
			cards.push(new TreasureCard(TreasureCardTypes.WATERSRISE, null));
		}
		
		shuffleDeck();
		
	}
	
	/**
	 * tearDown for testing
	 */
	public void tearDown() {
		treasureDeck = null;
	}
}
