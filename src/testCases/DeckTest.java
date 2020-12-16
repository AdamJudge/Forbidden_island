package testCases;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import elements.cards.*;
import elements.treasures.*;
import elements.board.TileNames;

public class DeckTest {

	@Test
	public void treasureDeckContents() throws IOException {
		
		TreasureDeck tdeck = TreasureDeck.getInstance();
		
		int oceanCount = 0, fireCount = 0, earthCount = 0, windCount = 0;
		int watersCount = 0, helicopterCount = 0, sandbagsCount = 0;
		int deckSize = 28; 	// should be 28, will be set to different value if it isn't.
		Card card;
		for(int i = 0; i<29; i++) {			// go for 1 larger than the deck, to catch if the deck is too big
			if(tdeck.isEmpty() == true) {
				deckSize = i;
				break;
			}
			card = tdeck.draw();
			
			
			if(((TreasureCard)card).getCardType() == TreasureCardTypes.HELICOPTER) {
				helicopterCount += 1;
			}
			else if(((TreasureCard)card).getCardType() == TreasureCardTypes.SANDBAGS) {
				sandbagsCount += 1;
			}
			else if(((TreasureCard)card).getCardType() == TreasureCardTypes.WATERSRISE) {
				watersCount += 1;
			}
			else if(((TreasureCard)card).getCardType() == TreasureCardTypes.TREASURE) {
				if(((TreasureCard)card).getTreasureType().getName() == TreasureNames.CRYSTAL_OF_FIRE) {
					fireCount += 1;
				}
				else if(((TreasureCard)card).getTreasureType().getName() == TreasureNames.EARTH_STONE) {
					earthCount += 1;
				}
				else if(((TreasureCard)card).getTreasureType().getName() == TreasureNames.STATUE_OF_THE_WIND) {
					windCount += 1;
				}
				else if(((TreasureCard)card).getTreasureType().getName() == TreasureNames.OCEAN_CHALICE) {
					oceanCount += 1;
				}
			}
		}
		
		assertEquals("Treasure Deck should have 28 cards", 28, deckSize);
		assertEquals("Treasure Deck should have 3 Helicopter Lift cards", 3, helicopterCount);
		assertEquals("Treasure Deck should have 2 Sandbags cards", 2, sandbagsCount);
		assertEquals("Treasure Deck should have 3 Waters Rise cards", 3, watersCount);
		assertEquals("Treasure Deck should have 5 Statue of the Wind cards", 5, windCount);
		assertEquals("Treasure Deck should have 5 Ocean's Chalice cards", 5, oceanCount);
		assertEquals("Treasure Deck should have 5 Crystal of Fire cards", 5, fireCount);
		assertEquals("Treasure Deck should have 5 Earth Stone cards", 5, earthCount);		
	}
	
	@Test
	public void floodDeckContents() throws IOException {
		
		FloodDeck fdeck = FloodDeck.getInstance();
		Set<TileNames> usedNames = new HashSet<TileNames>();
		int nbrCards = 24;
		Card card;
		int duplicateCount = 0;
		
		for(int i=0; i<25;i++) {			// go for 1 larger than the deck, to catch if the deck is too big
			if(fdeck.isEmpty() == true) {
				nbrCards = i;
				break;
			}
			
			card = fdeck.draw();
			if(!usedNames.contains(((FloodCard)card).getName())) {
				usedNames.add(((FloodCard)card).getName());
			}
			else {
				duplicateCount += 1;
			}	
		}
		
		assertEquals("FloodDeck should have 24 cards", 24, nbrCards);
		assertEquals("FloodDeck should not contain any duplicates", 0, duplicateCount);
		
	}

}
