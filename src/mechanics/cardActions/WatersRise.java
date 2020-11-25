package mechanics.cardActions;

import elements.board.WaterLevel;
import elements.cards.FloodDeck;
import elements.cards.FloodDiscard;
import elements.cards.Card;
import elements.cards.TreasureDiscard;

/**
 * WatersRise
 * 	carries out events that happen when a waters rise card is drawn and discards the card
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified: 25/11/20
 *
 */
public class WatersRise {

	public static void play(Card card) {
		// draw cards as indicated by water level
		for(int nbrCards = 0; nbrCards < WaterLevel.getInstance().getNbrCards(); nbrCards++) {
			FloodDeck.getInstance().draw();
		}
		
		// shuffle and return to top of the deck
		FloodDiscard.getInstance().toDeck();		
		
		TreasureDiscard.getInstance().addCard(card);
	}
	
}
