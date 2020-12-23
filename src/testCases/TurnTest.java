package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import elements.board.Board;
import elements.board.Difficulty;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.board.WaterLevel;
import elements.cards.*;

import org.junit.Before;
import org.junit.After;

import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.TurnView;
import mechanics.ViewInputTools;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.*;
import elements.pawns.*;

public class TurnTest {

	private Player engPlayer, navPlayer, expPlayer, divPlayer, pilPlayer, mesPlayer;
	
	@Before
	public void setup() {
		Setup.setupOnly();
		Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		
		engPlayer = new Player("Adam");
		PlayerList.getInstance().addPlayer(engPlayer);
		engPlayer.setPawn(new Engineer());
		engPlayer.getPawn().toInitialTile();
		
		expPlayer = new Player("Cat");
		PlayerList.getInstance().addPlayer(expPlayer);
		expPlayer.setPawn(new Explorer());
		expPlayer.getPawn().toInitialTile();
	}
	
	/**
	 * noActionTest
	 * inputting 5 should result in no more actions being requested
	 * check that turn happened by checking player's hand
	 */
	@Test
	public void noActionTest() {
		
		String input = "5 n n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int waterLevel = WaterLevel.getInstance().getWaterLevel();
		
		TurnView.getInstance().run(engPlayer);
		
		if(WaterLevel.getInstance().getWaterLevel() == waterLevel) {  // if no waters rise card was drawn, player should have 2 cards now
			assertEquals("Player should've been dealt 2 cards", 2, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel +1 ) { // if 1 waters rise card was drawn, player will have 1 card
			assertEquals("Player should've been dealt 1 cards", 1, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel+ 2) { // if 2 waters rise cards were drawn, player will have 0 card
			assertEquals("Player should've been dealt 0 cards", 0, engPlayer.getHand().getCards().size());
		}
	}

	/**
	 * regularActionCount
	 * 
	 * We assume all actions have been tested in relevant tests. 
	 * This test aims to test that three actions can be carried out. 
	 * Because of the random nature of the board and cards drawn (waters rise, and flood cards), giveCard is the only reliable one to count
	 * Therefore we get one player to give another player 3 cards to test the basic count of actions.
	 */
	@Test
	public void regularActionCount() {
		// deal the engineer 3 cards
		for(int i = 0; i<3; i++) {
			Card card = TreasureDeck.getInstance().draw();
			while(((TreasureCard)card).getCardType() == TreasureCardTypes.WATERSRISE) {	// make sure the card drawn isn't a waters rise
				card = TreasureDeck.getInstance().draw();
			}
			engPlayer.getHand().addCard(card);
		}
		
		// make sure any tiles flooded by waters rise cards are shored up
		for(Tile tile : Board.getInstance().getRemainingTiles()) {	
			tile.shoreup();
		}
		
		expPlayer.getPawn().move(engPlayer.getPawn().getTile());  // move explorer to engineer's tile to enable giving card
		
		int waterLevel = WaterLevel.getInstance().getWaterLevel(); // water level before turn (might've drawn waters rise above)
		
		String input = "3 1 1 3 1 1 3 1 1 n n n 1 1";
		// 3 to give card, 1 to pick player, 1 to pick card (three times)
		// n's in case player is asked to play cards
		// 1 1 in case swimming is required
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		TurnView.getInstance().run(engPlayer);
	
		assertEquals("Explorer shoudld've been given 3 cards", 3, expPlayer.getHand().getCards().size());
		
		if(WaterLevel.getInstance().getWaterLevel() == waterLevel) {  // if no waters rise card was drawn, player should have 2 cards now
			assertEquals("Player should've been dealt 2 cards", 2, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel +1 ) { // if 1 waters rise card was drawn, player will have 1 card
			assertEquals("Player should've been dealt 1 cards", 1, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel+ 2) { // if 2 waters rise cards were drawn, player will have 0 card
			assertEquals("Player should've been dealt 0 cards", 0, engPlayer.getHand().getCards().size());
		}
	}
	
	/**
	 * tooFewActionCount
	 * 
	 * same idea as regularActionCount, but only providing inputs for 2 actions should cause an exception
	 */
	@Test
	public void tooFewActionCount() {
		// deal the engineer 3 cards
		for(int i = 0; i<3; i++) {
			Card card = TreasureDeck.getInstance().draw();
			while(((TreasureCard)card).getCardType() == TreasureCardTypes.WATERSRISE) {	// make sure the card drawn isn't a waters rise
				card = TreasureDeck.getInstance().draw();
			}
			engPlayer.getHand().addCard(card);
		}
		
		// make sure any tiles flooded by waters rise cards are shored up
		for(Tile tile : Board.getInstance().getRemainingTiles()) {	
			tile.shoreup();
		}
		
		expPlayer.getPawn().move(engPlayer.getPawn().getTile());  // move explorer to engineer's tile to enable giving card
		
		// int waterLevel = WaterLevel.getInstance().getWaterLevel(); // water level before turn (might've drawn waters rise above)
		
		String input = "3 1 1 3 1 1 n n";
		// 3 to give card, 1 to pick player, 1 to pick card (three times)
		// n's in case player is asked to play cards
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		// expect an exception as the "n" which would be required at the end of the turn is not accepted because we expect another action to occur
		assertThrows("Should throw exception", java.util.NoSuchElementException.class, () -> {
			TurnView.getInstance().run(engPlayer);
		    });	
	}
	
	
	
	 /** twoShoreupTest
	 * 
	 * test engineer doing two shore ups
	 * both shore ups should count as one action, so do 2 other actions to make sure the count is right
	 * 
	 * construct board so that the actions will happen correctly. checks on actions done in relevant test files
	 * 
	 */
	@Test
	public void twoShoreupTest() {
		// deal the engineer 2 cards
		for(int i = 0; i<2; i++) {
			Card card = TreasureDeck.getInstance().draw();
			while(((TreasureCard)card).getCardType() == TreasureCardTypes.WATERSRISE) {	// make sure the card drawn isn't a waters rise
				card = TreasureDeck.getInstance().draw();
			}
			engPlayer.getHand().addCard(card);
		}
		
		// make sure any tiles flooded by waters rise cards are shored up
		for(Tile tile : Board.getInstance().getRemainingTiles()) {	
			tile.shoreup();
		}
		
		// flood all tiles. ensures Engineer is surrounded by at least 2 flooded tiles
		for(Tile tile : Board.getInstance().getRemainingTiles()) {
			tile.flood();
		}
		
		expPlayer.getPawn().move(engPlayer.getPawn().getTile());  // move explorer to engineer's tile to enable giving card
		
		int waterLevel = WaterLevel.getInstance().getWaterLevel(); // water level before turn (might've drawn waters rise above)
		
		String input = "2 1 1 3 1 1 3 1 1 n n n 1 1";
		// 2 for shoreup, 1 to pick tile, 1 for second shoreup (engineer)
		// 3 to give card, 1 to pick player, 1 to pick card (twice)
		// n's in case player is asked to play cards
		// 1 1 in case swimming is required
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		TurnView.getInstance().run(engPlayer);
		

		// Can't test that tiles were shored up because they could get flooded again when cards are drawn
		// however shore up is checked in the shoreup test, so we will assume the action worked, and focus on the number of actions
		// if the second shore up had not counted as a shore-up correctly, then the give card action would not happen
		// (We would've moved the pawn to the 3rd option)
		// therefore this test is sufficient
		assertEquals("Explorer shoudld've been given two cards", 2, expPlayer.getHand().getCards().size());
		
		if(WaterLevel.getInstance().getWaterLevel() == waterLevel) {  // if no waters rise card was drawn, player should have 2 cards now
			assertEquals("Player should've been dealt 2 cards", 2, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel +1 ) { // if 1 waters rise card was drawn, player will have 1 card
			assertEquals("Player should've been dealt 1 cards", 1, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel+ 2) { // if 2 waters rise cards were drawn, player will have 0 card
			assertEquals("Player should've been dealt 0 cards", 0, engPlayer.getHand().getCards().size());
		}

	}
	
	/**
	 * cancelSecondShoreupTest
	 * get engineer to cancel a shoreup 
	 * this aims to make sure that an action was counted for the first shoreup
	 * test will fail if the action is not counted, as we will only be attempting 2 actions instead of 3
	 */
	@Test 
	public void cancelSecondShoreupTest() {	
		// deal the engineer 2 cards
		for(int i = 0; i<2; i++) {
			Card card = TreasureDeck.getInstance().draw();
			while(((TreasureCard)card).getCardType() == TreasureCardTypes.WATERSRISE) {	// make sure the card drawn isn't a waters rise
				card = TreasureDeck.getInstance().draw();
			}
			engPlayer.getHand().addCard(card);
		}
		
		// make sure any tiles flooded by waters rise cards are shored up
		for(Tile tile : Board.getInstance().getRemainingTiles()) {	
			tile.shoreup();
		}
		
		// flood all tiles. ensures Engineer is surrounded by at least 2 flooded tiles
		for(Tile tile : Board.getInstance().getRemainingTiles()) {
			tile.flood();
		}
		
		expPlayer.getPawn().move(engPlayer.getPawn().getTile());  // move explorer to engineer's tile to enable giving card
		
		int waterLevel = WaterLevel.getInstance().getWaterLevel(); // water level before turn (might've drawn waters rise above)
		
		String input = "2 1 0 3 1 1 3 1 1 n n n 1 1";
		// 2 for shoreup, 1 to pick tile, 0 to cancel second shoreup (engineer)
		// 3 to give card, 1 to pick player, 1 to pick card (twice)
		// n's in case player is asked to play cards
		// 1 1 in case swimming is required
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		TurnView.getInstance().run(engPlayer);
		
		assertEquals("Explorer shoudld've been given two cards", 2, expPlayer.getHand().getCards().size());
		
		if(WaterLevel.getInstance().getWaterLevel() == waterLevel) {  // if no waters rise card was drawn, player should have 2 cards now
			assertEquals("Player should've been dealt 2 cards", 2, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel +1 ) { // if 1 waters rise card was drawn, player will have 1 card
			assertEquals("Player should've been dealt 1 cards", 1, engPlayer.getHand().getCards().size());
		}
		else if(WaterLevel.getInstance().getWaterLevel() == waterLevel+ 2) { // if 2 waters rise cards were drawn, player will have 0 card
			assertEquals("Player should've been dealt 0 cards", 0, engPlayer.getHand().getCards().size());
		}
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
