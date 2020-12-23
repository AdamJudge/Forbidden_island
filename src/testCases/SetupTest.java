package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import elements.board.Board;
import elements.board.WaterLevel;
import elements.cards.FloodDeck;
import elements.cards.FloodDiscard;
import elements.cards.TreasureDeck;
import elements.cards.TreasureDiscard;
import mechanics.Scan;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.*;

public class SetupTest {

	@Test
	public void setupTest() {
		
		String input = "3 Adam Catherine Liliana 2";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		Setup.setupAndRun();
		
		ArrayList<Player> players = PlayerList.getInstance().getPlayers();
		assertEquals("Number of players should be 3", 3, players.size());
		assertEquals("Player 1 should be Adam", "Adam", players.get(0).getName());
		assertEquals("Player 2 should be Catherine", "Catherine", players.get(1).getName());
		assertEquals("Player 1 should be Liliana", "Liliana", players.get(2).getName());

		assertEquals("Starting hand should have 2 cards", 2, players.get(0).getHand().getCards().size());
		assertEquals("Starting hand should have 2 cards", 2, players.get(1).getHand().getCards().size());
		assertEquals("Starting hand should have 2 cards", 2, players.get(2).getHand().getCards().size());

		assertNotEquals("Players should have different pawns", players.get(0).getPawn().getClass(), players.get(1).getPawn().getClass());
		assertNotEquals("Players should have different pawns", players.get(0).getPawn().getClass(), players.get(2).getPawn().getClass());
		assertNotEquals("Players should have different pawns", players.get(1).getPawn().getClass(), players.get(2).getPawn().getClass());
		
		assertEquals("Difficulty Level should be Normal", "NORMAL", WaterLevel.getInstance().getDifficulty());
		assertEquals("Water level should be set to 2", 2, WaterLevel.getInstance().getWaterLevel());
		assertEquals("Number of cards drawn per turn should be 2", 2, WaterLevel.getInstance().getNbrCards());

		assertEquals("Treasure Deck should have 22 cards left (28 - 3x2)", 22, TreasureDeck.getInstance().getSize());
		assertEquals("Flood Deck should have 18 cards left (6 taken in setup)", 18, FloodDeck.getInstance().getSize());
		
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
	}

}
