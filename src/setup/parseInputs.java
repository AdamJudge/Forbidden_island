package setup;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class parseInputs {
	public static int main(Scanner user, int low, int high) throws IOException {
		int inputNum=low-1;
		while (inputNum <low || inputNum > high) {
			System.out.println("Enter a number [" + low +"-" +high + "]");
			try {	
				inputNum = user.nextInt();
			} catch (NoSuchElementException e){System.out.println("An integer must be inputted!");				user.next();}
		}
		return inputNum;	
	}
}
