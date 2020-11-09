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

import java.util.Scanner;
import setup.Setup;

public class ForbiddenIsland {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		Setup.getInstance().setupAll(inputScanner);
		        
        //GameManager.getInstance().doGameplay(inputScanner);

        inputScanner.close();
	}
}
