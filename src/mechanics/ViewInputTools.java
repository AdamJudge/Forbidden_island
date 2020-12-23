package mechanics;

import java.util.NoSuchElementException;

/**
 * ViewInputTools
 * 
 * Input parsers commonly used by views
 * 
 * @author Catherine Waechter
 * @version 1.2
 *	Methods used to be in separate classes
 *	Implement Scan class
 *
 *	Date created: 20/12/20
 *	Last modified: 22/12/20
 */
public class ViewInputTools {

	/**
	 * letters
	 * 	String parser
	 * 	only accepts letters, will prompt user to enter something else if numbers or special characters are inputted
	 * 
	 * @param user
	 * @return String entered by user 
	 */
	public static String letters(Scan user) {
		String input=null;
		String pattern="[a-zA-Z]+";	// allow lower case and upper case letters
		
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
	
	/**
	 * yesNo
	 * 	Yes or no parser. Accepts y,Y,yes,Yes as yes, n,N,no,No as no
	 * 	treat yes as true, no as false
	 * @param user
	 * @return t/f
	 */
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
