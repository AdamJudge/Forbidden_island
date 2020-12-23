package mechanics;

import java.util.Scanner;

/**
 * Scan (singleton)
 * 
 * singleton instance of the scanner. Used to ensure the right input is passed in testing. Will just remain System in otherwise
 * 
 * @author Catherine Waechter
 * 
 * Date created: 22/12/20
 * Last modified: 22/12/20
 */
public class Scan {
	private static Scan scan = null;
	
	private Scanner scanner;	
	
	/**
	 * Scan constructor
	 * sets scanner to System.in as default
	 */
	private Scan() {
		scanner = new Scanner(System.in);
	}
	
	/**
	 * setScanner
	 * 
	 * set scanner to a given input
	 * @param input
	 */
	public void setScanner(Scanner input) {
		scanner = input;
	}
	
	/**
	 * getScanner
	 * @return scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * close
	 * 
	 * close the scanner
	 */
	public void close() {
		scanner.close();
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

	/**
	 * tearDown
	 * used for testing
	 */
	public void tearDown() {
		scan=null;
	}
}
