package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.io.IOException;

import org.junit.Test;

import mechanics.ViewInputTools;

public class UserInputTest {

	@Test
	public void stringInput() throws IOException{
		String input = "Adam";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		String output = ViewInputTools.letters(scanner);
		
		assertEquals("Input read by parser should equal user input", input, output);
	}
	
	@Test
	public void stringInputNumber() throws IOException{
		String input = "2 \n Adam";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		String output = ViewInputTools.letters(scanner);
		
		assertEquals("Input should not contain numbers", "Adam", output);
	}
	
	@Test
	public void stringInputWithNumber() throws IOException{
		String input = "Adam2\n Adam";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		String output = ViewInputTools.letters(scanner);
		
		assertEquals("Input should not contain numbers", "Adam", output);
	}

	@Test
	public void numberInputInBounds() throws IOException{
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		int output = ViewInputTools.numbers(scanner, 0, 5);
		
		assertEquals("Input read by parser should equal user input", 1, output);
	}
	
	@Test
	public void numberInputOutBounds() throws IOException{
		String input = "6\n2"; // first out of bounds, second in bounds. Out of bounds should ask user to input again
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		int output = ViewInputTools.numbers(scanner, 1, 5);
		
		assertEquals("Input read by parser should equal second user input", 2, output);
	}

	@Test
	public void numberInputLetters() throws IOException{
		String input = "six \n 2"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		int output = ViewInputTools.numbers(scanner, 1, 5);
		
		assertEquals("Input should not contain letters", 2, output);
	}
	
	@Test
	public void numberInputLowLim() throws IOException{
		String input = "6"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		int output = ViewInputTools.numbers(scanner, 0, 6);
		
		assertEquals("Input read by parser should equal user input", 6, output);
	}
	
	@Test
	public void numberInputUpperLim() throws IOException{
		String input = "4"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		int output = ViewInputTools.numbers(scanner, 4, 10);
		
		assertEquals("Input read by parser should equal user input", 4, output);
	}
	
	@Test
	public void yesInput() throws IOException{
		String input = "y"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		boolean output = ViewInputTools.yesNo(scanner);
		
		assertEquals("Parser should return True for \"y\" input", true, output);
	}
		
	@Test
	public void noInput() throws IOException{
		String input = "n"; 
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		boolean output = ViewInputTools.yesNo(scanner);
		
		assertEquals("Parser should return False for \"n\" input", false, output);
	}
	
	@Test
	public void yesNoInputWrong() throws IOException{
		String input = "yn \n n"; 	// yn should count as a wrong input, only n will be counted
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		boolean output = ViewInputTools.yesNo(scanner);
		
		assertEquals("Parser should return False for \"n\" input", false, output);
	}
	
	@Test
	public void yesInputWrong() throws IOException{
		String input = "e \n y"; 	// e should count as a wrong input, only n will be counted
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		boolean output = ViewInputTools.yesNo(scanner);
		
		assertEquals("Parser should return False for \"n\" input", true, output);
	}
}
