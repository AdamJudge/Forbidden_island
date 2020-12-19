/**
 * Class Name: ClaimTreasureView
 *
 * 	View to display action of claiming a treasure
 * 
 * @author Adam Judge, Catherine Waechter
 * 
 * @version 1.3
 * 	adjusted for ActionController
 * 
 * Creation Date: 22/10/20
 * Last Modified: 17/12/20
 */

package mechanics.actions;

import players.Player;
import setup.ParseLetterInputs;
import setup.ParseNumberInputs;
import elements.treasures.Treasure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;


public class ClaimTreasureView extends ActionView{

	private static ClaimTreasureView claimTreasureView = null;
	private ActionController controller;
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
	
		Treasure possibleTreasure = controller.getClaimTreasureCheck(currentPlayer);
		
		if(possibleTreasure == null) {
			System.out.println("You cannot claim any treasure at this time. Please select another action");
			return false;
		}
		System.out.println("You can claim the " + possibleTreasure );
		System.out.println("Would you like to do so? [y/n]");
		
		String userAns = " ";
		
		while(!userAns.equals("n") && !userAns.equals("y")) {
			userAns = ParseLetterInputs.main(user);
			if(userAns.equals("n")) {
				return false;
			}
			else if(userAns.equals("y")) {
				controller.claimTreasure(currentPlayer, possibleTreasure);
				System.out.println(possibleTreasure + " has been captured!");
				Set<Treasure> unClaimed = controller.getUnclaimedTreasures();
				if(unClaimed.isEmpty()) {
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
	public static ClaimTreasureView getInstance() {
		if(claimTreasureView == null) { 
			claimTreasureView = new ClaimTreasureView();
		}
		return claimTreasureView;
	}
	
	public void setController(ActionController controller) {
		this.controller = controller;
	}
	
	
}
