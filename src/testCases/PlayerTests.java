package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

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

/**
 * PlayerTests
 * 
 * test full hand behaviour
 * 
 * @author Catherine Waechter
 *
 */
public class PlayerTests {

	@Before 
	public void testSetup() {
		Setup.setupOnly();
	}
	
	/**
	 * fullHandTest
	 * 
	 * check that hand hand does not allow 6 cards 
	 * input used to discard card when prompted
	 */
	@Test
	public void fullHandtest()   {
		
		PlayerList.getInstance().addPlayer(new Player("Adam"));
		
		Player adam = PlayerList.getInstance().getPlayers().get(0);
		
		String input = "n 1";		 // n because we don't want to play a card when the hand gets full. If not prompted, will just count as a wrong input and take the 1
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		for(int i=0; i<6; i++) {
			adam.getHand().addCard(TreasureDeck.getInstance().draw());
		}
		
		assertEquals("Hand should not exceed 5", 5, adam.getHand().getCards().size());
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
