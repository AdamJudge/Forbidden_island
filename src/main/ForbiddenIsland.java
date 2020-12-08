/**
 * Class Name:
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package main;

import java.io.IOException;
import java.util.Scanner;

import mechanics.GamePlay;
import setup.Setup;

public class ForbiddenIsland {
	public static void main(String[] args) throws IOException {
		Scanner inScan = new Scanner(System.in);
		Setup.getInstance().setupAll(inScan);
		
		GamePlay.getInstance().playGame(inScan);
		
		System.out.println("Thanks for playing :)");
        inScan.close();
	}
}
