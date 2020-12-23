package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

import elements.board.Board;
import elements.board.WaterLevel;
import elements.cards.FloodDeck;
import elements.cards.FloodDiscard;
import elements.cards.TreasureDeck;
import elements.cards.TreasureDiscard;
import mechanics.Scan;
import mechanics.ViewInputTools;
import mechanics.cardActions.PlayCardView;
import players.PlayerList;

/**
 * UserInputTest
 * 
 * test user input methods (with correct and wrong inputs)
 * 
 * @author Catherine Waechter
 *
 */
public class UserInputTest {

	/**
	 * stringInput
	 * correct string input
	 */
	@Test
	public void stringInput()  {
		String input = "Adam";
		
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		String output = ViewInputTools.letters(Scan.getInstance());
	    
		assertEquals("Input read by parser should equal user input", input, output);
	}
	
	/**
	 * stringInputNumber
	 * incorrect string input
	 */
	@Test
	public void stringInputNumber()  {
		String input = "2";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		assertThrows("Should throw exception", java.util.NoSuchElementException.class, () -> {
		      ViewInputTools.letters(Scan.getInstance());
		    });		
		
	}
	
	/**
	 * stringInputWithNumber
	 * incorrect string input
	 */
	@Test
	public void stringInputWithNumber()  {
		String input = "Adam2";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		assertThrows("Should throw exception", java.util.NoSuchElementException.class, () -> {
		      ViewInputTools.letters(Scan.getInstance());
		    });		
	}

	/**
	 * numberInputInBounds
	 * correct number input
	 */
	@Test
	public void numberInputInBounds()  {
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int output = ViewInputTools.numbers(Scan.getInstance(), 0, 5);
		
		assertEquals("Input read by parser should equal user input", 1, output);
	}
	
	@Test
	public void numberInputOutBounds()  {
		String input = "6\n2"; // first out of bounds, second in bounds. Out of bounds should ask user to input again
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int output = ViewInputTools.numbers(Scan.getInstance(), 1, 5);
		
		assertEquals("Input read by parser should equal second user input", 2, output);
	}

	/**
	 * numberInputInBounds
	 * incorrect number input
	 */
	@Test
	public void numberInputLetters()  {
		String input = "six"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
	    assertThrows("Should throw exception", java.util.NoSuchElementException.class, () -> {
		      ViewInputTools.numbers(Scan.getInstance(), 0, 5);
		    });
	}
	
	/**
	 * numberInputInBounds
	 * correct number input
	 */
	@Test
	public void numberInputUpperLim()  {
		String input = "6"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int output = ViewInputTools.numbers(Scan.getInstance(), 0, 6);
		
		assertEquals("Input read by parser should equal user input", 6, output);
	}
	
	/**
	 * numberInputInBounds
	 * correct number input
	 */
	@Test
	public void numberInputLowLim()  {
		String input = "4"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int output = ViewInputTools.numbers(Scan.getInstance(), 4, 10);
		
		assertEquals("Input read by parser should equal user input", 4, output);
	}
	
	@Test
	public void yesInput()  {
		String input = "y"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		boolean output = ViewInputTools.yesNo(Scan.getInstance());
		
		assertEquals("Parser should return True for \"y\" input", true, output);
	}
		
	@Test
	public void noInput()  {
		String input = "n"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		boolean output = ViewInputTools.yesNo(Scan.getInstance());
		
		assertEquals("Parser should return False for \"n\" input", false, output);
	}
	
	@Test
	public void yesNoInputWrong()  {
		String input = "yn \n n"; 	// yn should count as a wrong input, only n will be counted
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		boolean output = ViewInputTools.yesNo(Scan.getInstance());
		
		assertEquals("Parser should return False for \"n\" input", false, output);
	}
	
	@Test
	public void yesInputWrong()  {
		String input = "e \n y"; 	// e should count as a wrong input, only n will be counted
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		boolean output = ViewInputTools.yesNo(Scan.getInstance());
		
		assertEquals("Parser should return False for \"n\" input", true, output);
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
