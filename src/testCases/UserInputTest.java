package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

import mechanics.Scan;
import mechanics.ViewInputTools;

public class UserInputTest {

	@Test
	public void stringInput()  {
		String input = "Adam";
		
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		String output = ViewInputTools.letters(Scan.getInstance());
	    
		assertEquals("Input read by parser should equal user input", input, output);
	}
	
	@Test
	public void stringInputNumber()  {
		String input = "2 \n Adam";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
	    String output = ViewInputTools.letters(Scan.getInstance());
		
		assertEquals("Input should not contain numbers", "Adam", output);
	}
	
	@Test
	public void stringInputBlank()  {
		String input = "  ";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		String output = ViewInputTools.letters(Scan.getInstance());
		
		assertEquals("Input should not contain numbers", " ", output);
	}
	
	
	@Test
	public void stringInputWithNumber()  {
		String input = "Adam2\n Adam";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		String output = ViewInputTools.letters(Scan.getInstance());
		
		assertEquals("Input should not contain numbers", "Adam", output);
	}

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
	
	@Test
	public void numberInputLowLim()  {
		String input = "6"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		int output = ViewInputTools.numbers(Scan.getInstance(), 0, 6);
		
		assertEquals("Input read by parser should equal user input", 6, output);
	}
	
	@Test
	public void numberInputUpperLim()  {
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
}
