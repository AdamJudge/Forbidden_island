/**
 * Class Name: ClaimTreasureView
 *
 * 	View to display action of claiming a treasure
 * 
 * @author Adam Judge, Catherine Waechter
 * 
 * @version 1.2
 * 	adjusted to suit ActionView
 * 
 * Creation Date: 22/10/20
 * Last Modified: 03/12/20
 */

package mechanics.actions;

import players.Player;
import setup.ParseLetterInputs;
import setup.ParseNumberInputs;
import mechanics.TurnController;
import elements.treasures.Treasure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;




public class ClaimTreasureView extends ActionView{

	private static ClaimTreasureView claimTreasureView = null;
	private TurnController controller;
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
	
		Treasure possibleTreasure = controller.getClaimTreasureCheck(currentPlayer);
		
		if(possibleTreasure == null) {
			System.out.println("You cannot claim any treasure at this time. Please select another action");
			return false;
		}
		System.out.println("You can claim the " + possibleTreasure );
		System.out.println("Would you like to do so? [y/n]");
		
		String userAns = null;
		
		while(userAns != "n" && userAns != "y") {
			userAns = ParseLetterInputs.main(user);
			if(userAns == "n") {
				return false;
			}
			else if(userAns == "y") {
				controller.claimTreasure(possibleTreasure);
				System.out.println(possibleTreasure + " has been captured!");
				Set<Treasure> unClaimed = controller.getUnclaimedTreasures();
				if(unClaimed == null) {
					System.out.println("You've claimed all treasures! Make your way to Fools' landing to get off the island!");
				}
				else {
					System.out.println("You still need to claim " + unClaimed);
				}
				return true;
			}
			System.out.println("Please enter \"y\" or \"n\"");
		} 
		return false;
	}

	/**
	 * getInstance
	 * 	get singleton instance of ClaimTreasureView
	 * @param controller - controller associated with the view
	 * @return claimTreasureView (singleton instance)
	 */
	public static ClaimTreasureView getInstance(TurnController controller) {
		if(claimTreasureView == null) { 
			claimTreasureView = new ClaimTreasureView(controller);
		}
		return claimTreasureView;
	}
	
	/**
	 * ClaimTreasureView Constructor
	 * @param controller	 - turn controller 
	 */
	private ClaimTreasureView(TurnController controller) {
		this.controller = controller;
	}
}
