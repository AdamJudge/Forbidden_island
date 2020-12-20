package mechanics;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ViewInputTools {

	public static String letters(Scanner user) throws IOException {
		String input=null;
		String pattern="[a-zA-Z]+";
		while (input==null) {
			try {	
				input = user.next(pattern);
			} catch (NoSuchElementException e){
				System.out.println("Only letters permitted!");
				user.next();
			}
		}
		return input;	
	}
	
	public static boolean yesNo(Scanner user) {
		String input=" ";
		
		while (!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")) {
			input = user.next();
			if(input.toLowerCase().equals("y")) {
				return true;
			}
			else if (input.toLowerCase().equals("n")){
				return false;
			}
			System.out.println("Please enter \"y\" or \"n\"");
		}
		return false;
		
	}
	
	public static int numbers(Scanner user, int low, int high) throws IOException {
		int inputNum=low-1;
		while (inputNum <low || inputNum > high) {
			System.out.println("Enter a number [" + low +"-" +high + "]");
			try {	
				inputNum = user.nextInt();
			} catch (NoSuchElementException e){System.out.println("An integer must be inputted!");				
			user.next();
			}
		}
		return inputNum;	
	}
	
	
	
}
