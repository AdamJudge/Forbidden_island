package setup;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ParseLetterInputs {
	public static String main(Scanner user) throws IOException {
		String input=null;
		String pattern="[a-zA-Z]+";
		while (input==null) {
			System.out.println("Enter a string");
			try {	
				input = user.next(pattern);
			} catch (NoSuchElementException e){
				System.out.println("Only letters permitted!");
				user.next();
			}
		}
		return input;	
	}
}
