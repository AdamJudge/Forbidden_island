/**
 * ForbiddenIsland 
 *
 * Main class to run the game
 * 
 * @author Adam Judge
 * @version 1.2
 * 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package main;

import java.util.Scanner;

import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.setup.Setup;

public class ForbiddenIsland {
	
	/**
	 * main
	 * 	
	 * 	Carry out setup, and set game to play
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scan.getInstance();		// set Scanner to System.in (default in constructor)
		Setup.setupAndRun();		
		
		GamePlay.getInstance().playGame();
		System.out.println("Thanks for Playing!");		// TODO Remove when we've figured out all win / lose conditions ? 
        Scan.getInstance().close();
	}
}
