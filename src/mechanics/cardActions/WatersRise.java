package mechanics.cardActions;

import elements.board.WaterLevel;
import elements.cards.FloodDiscard;
import elements.cards.Card;
import elements.cards.TreasureDiscard;

/**
 * WatersRise
 * 	carries out events that happen when a waters rise card is drawn and discards the card
 * 
 * @author Catherine Waechter
 * @version 1.2
 * 	Removed drawing cards, bc that's not in the rules
 * 
 * Date created: 25/11/20
 * Last modified: 14/12/20
 *
 */
public class WatersRise {

	/**
	 * play
	 * 	raise water level
	 * 	put flood discard into deck
	 * 	discard the card
	 * @param card
	 */
	public static void play(Card card) {
		WaterLevel.getInstance().raise_level();
		
		// shuffle and return to top of the deck
		FloodDiscard.getInstance().toDeck();		
		
		TreasureDiscard.getInstance().addCard(card);
	}
	
}
