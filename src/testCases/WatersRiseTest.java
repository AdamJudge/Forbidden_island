package testCases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import elements.board.Board;
import elements.board.Difficulty;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.board.WaterLevel;
import elements.cards.*;
import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.PlayerList;

import java.util.ArrayList;

public class WatersRiseTest {

	@Before
	public void setup() {
		Setup.setupOnly();
		Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
	}
	
	@Test
	public void watersRiseTest() {
		
		ArrayList<Card> cardsDrawn = new ArrayList<Card>();
		int floodedTileCount = 0, removedTileCount = 0;
		
		for(int i = 0; i<3; i++) {
			cardsDrawn.add(FloodDeck.getInstance().draw());
		}
		
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getStatus() == TileStatus.FLOODED) {
				floodedTileCount++;
			}
			else if(tile.getStatus() == TileStatus.REMOVED) {
				removedTileCount++;
			}
		}
		
		assertEquals("Drawing 3 flood cards should give 3 flooded tiles", 3, floodedTileCount);
		assertEquals("Drawing 3 flood cards should not remove any tiles", 0, removedTileCount);
		
		int waterLevel = WaterLevel.getInstance().getWaterLevel();
		
		// Draw a waters rise card
		Card card = TreasureDeck.getInstance().draw();
		while(((TreasureCard)card).getCardType() != TreasureCardTypes.WATERSRISE) {	
			card = TreasureDeck.getInstance().draw();
		}
		
		int containedCount = 0;
		for(int i = 0; i<3; i++) {
			Card fCard = FloodDeck.getInstance().draw();
			if(cardsDrawn.contains(fCard)) {	// if the card was in the original cards drawn, increase count
				containedCount++;
			}
		}
		
		assertEquals("Water level should've risen by 1", waterLevel+1, WaterLevel.getInstance().getWaterLevel());
		assertEquals("Treasure discard should contain the waters rise card", 1, TreasureDiscard.getInstance().getSize());
		
		// We assume Collections.shuffle successfully shuffled the cards, since we can't test shuffling systematically (technically could shuffle into the same order)
		assertEquals("First 3 cards drawn should be the same as original 3 cards as waters rise should add to top of pile", 3, containedCount);
		
		floodedTileCount = 0; // reset counts
		removedTileCount = 0;
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getStatus() == TileStatus.FLOODED) {
				floodedTileCount++;
			}
			else if(tile.getStatus() == TileStatus.REMOVED) {
				removedTileCount++;
			}
		}
		assertEquals("Drawing 3 flood cards again should remove any flooded tiles", 0, floodedTileCount);
		assertEquals("Drawing 3 flood cards again should remove the tiles that had been flooded", 3, removedTileCount);
		
	}

	/**
	 * watersRiseEmptyDiscardTest
	 * check that waters rise works if there are no cards in the discard pile
	 * water level should still rise by 1
	 */
	@Test
	public void watersRiseEmptyDiscardTest() {
		int waterLevel = WaterLevel.getInstance().getWaterLevel();
		
		// Draw a waters rise card
		Card card = TreasureDeck.getInstance().draw();
		while(((TreasureCard)card).getCardType() != TreasureCardTypes.WATERSRISE) {	
			card = TreasureDeck.getInstance().draw();
		}
		
		assertEquals("Treasure discard should contain the waters rise card", 1, TreasureDiscard.getInstance().getSize());
		assertEquals("Water level should've risen by 1", waterLevel+1, WaterLevel.getInstance().getWaterLevel());	
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
