package mechanics;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ViewInputTools {

	public static String letters(Scan user) {
		String input=null;
		String pattern="[a-zA-Z]+";
		while (input==null) {
			try {	
				input = user.getScanner().next(pattern);
			} catch (NoSuchElementException e){
				System.out.println("Only letters permitted!");
				user.getScanner().next();
			}
		}
		return input;	
	}
	
	public static boolean yesNo(Scan user) {
		String input=" ";
		
		while (true) {
			input = user.getScanner().next();
			if(input.toLowerCase().equals("y") || input.toLowerCase().equals("yes")) {
				return true;
			}
			else if (input.toLowerCase().equals("n") || input.toLowerCase().equals("no")){
				return false;
			}
			System.out.println("Please enter \"y\" or \"n\"");
		}

	}
	
	public static int numbers(Scan user, int low, int high) {
		int inputNum=low-1;
		while (inputNum <low || inputNum > high) {
			System.out.println("Enter a number [" + low +"-" +high + "]");
			try {	
				inputNum = user.getScanner().nextInt();
			} catch (NoSuchElementException e){System.out.println("An integer must be inputted!");				
			user.getScanner().next();
			}
		}
		return inputNum;	
	}
	
	
	
}
