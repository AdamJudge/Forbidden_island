package mechanics;

import java.util.Scanner;

public class Scan {
	private static Scan scan = null;
	
	private Scanner scanner;
	
	public void setScanner(Scanner input) {
		scanner = input;
	}
	
	public void close() {
		scanner.close();
	}
	
	public Scanner getScanner() {
		return scanner;
	}
	
	 /**
	 * getInstance
	 * @return setupView - singleton instance of Scan
	 */
	
	public static Scan getInstance() {
		if(scan == null)
			scan= new Scan();
		return scan;
	}
	
	private Scan() {
		scanner = new Scanner(System.in);
	}

	public void tearDown() {
		scan=null;
	}
}
