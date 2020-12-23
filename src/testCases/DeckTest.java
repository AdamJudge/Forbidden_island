package testCases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Set;
import java.util.HashSet;
import elements.cards.*;
import elements.treasures.*;
import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.PlayerList;
import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.board.WaterLevel;

/**
 * DeckTest 
 * 
 * 	Tests deck contents and drawing behaviour
 * 
 * @author Catherine Waechter
 *	
 */
public class DeckTest {

	@Before
	public void setup() {
		Setup.setupOnly();	// carry out setup
	}
	
	/**
	 * treasureDeckContents
	 * 
	 * count up contents of treasure deck
	 */
	@Test
	public void treasureDeckContents() {
		
		TreasureDeck tdeck = TreasureDeck.getInstance();
			
		int oceanCount = 0, fireCount = 0, earthCount = 0, windCount = 0;
		int watersCount = 0, helicopterCount = 0, sandbagsCount = 0;
		 	
		Card card;
		
		int deckSize = tdeck.getSize();
		
		for(int i = 0; i<28; i++) {	
			
			card = tdeck.draw();
			
			System.out.println(card);
			
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
	
	/**
	 * floodDeckContents
	 * 
	 * make sure there's a card for each tile
	 */
	@Test
	public void floodDeckContents() {
		
		FloodDeck fdeck = FloodDeck.getInstance();
		Set<Tile> usedTiles = new HashSet<Tile>();
		
		Card card;
		int duplicateCount = 0;
		
		int deckSize = fdeck.getSize();
		
		for(int i=0; i<24;i++) {			
			
			card = fdeck.draw();
			if(!usedTiles.contains(((FloodCard)card).getTile())) {
				usedTiles.add(((FloodCard)card).getTile());
			}
			else {
				duplicateCount += 1;
			}	
		}
		
		assertEquals("FloodDeck should have 24 cards", 24, deckSize);
		assertEquals("FloodDeck should not contain any duplicates", 0, duplicateCount);
		
	}

	/**
	 * drawFloodCard
	 * 
	 * Draw a flood card, make sure tile has been flooded and card was discarded
	 */
	@Test
	public void drawFloodCard() {
		
		FloodDeck fdeck = FloodDeck.getInstance();
		FloodDiscard fdiscard = FloodDiscard.getInstance();
		
		assertEquals("FloodDeck should initially have 24 cards", 24, fdeck.getSize());
		assertEquals("Flood Discard should be empty", 0, fdiscard.getSize());
		
		Card firstCard = fdeck.draw();
		
		assertEquals("FloodDeck should have 23 cards after a card is drawn", 23, fdeck.getSize());
		assertEquals("Flood Discard should contain one card after a card is drawn", 1, fdiscard.getSize());
		
		assertEquals("Drawn card should have caused the tile to be flooded", TileStatus.FLOODED, ((FloodCard)firstCard).getTile().getStatus());
		
		fdiscard.toDeck();
		
		assertEquals("Discard should be empty after putting card back in deck", 0, fdiscard.getSize());
		assertEquals("Deck should have 24 cards after putting the discarded card back", 24, fdeck.getSize());
		
		Card secondCard = fdeck.draw();
		
		assertEquals("FloodDeck should have 23 cards after a card is drawn", 23, fdeck.getSize());
		assertEquals("Flood Discard should still be empty because card should be removed", 0, fdiscard.getSize());
		
		assertEquals("The two cards drawn should be the same object", firstCard, secondCard);
		assertEquals("Drawn card should have caused the tile to be removed", TileStatus.REMOVED, ((FloodCard)secondCard).getTile().getStatus());
		
		
	}
	
	@After
	public void tearDown() {
		PlayerList.getInstance().tearDown();
		WaterLevel.getInstance().tearDown();
		Board.getInstance().tearDown();
		TreasureDeck.getInstance().tearDown();
		TreasureDiscard.getInstance().tearDown();
		FloodDeck.getInstance().tearDown();
		FloodDiscard.getInstance().tearDown();
		Scan.getInstance().tearDown();
		PlayCardView.getInstance().tearDown();
		GamePlay.getInstance().tearDown();
	}
}
