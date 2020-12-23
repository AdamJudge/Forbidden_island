package mechanics.actions;

import players.Player;
import elements.treasures.Treasure;
import mechanics.ViewInputTools;
import mechanics.Scan;

import java.util.Set;

/**
 * ClaimTreasureView (Singleton, MVC)
 *
 * 	View to display action of claiming a treasure
 * 
 * @author Adam Judge, Catherine Waechter
 * 
 * @version 2.1
 * 	Uses ClaimTreasureController now
 * 
 * Creation Date: 22/10/20
 * Last Modified: 23/12/20
 */

public class ClaimTreasureView {

	private static ClaimTreasureView claimTreasureView = null;
	private ClaimTreasureController controller;
	
	Scan user;
	
	/**
	 * doAction
	 * 	Display Treasure that can be claimed 
	 * 	Ask user for confirmation
	 * 	Prompt controller to claim treasure
	 * 
	 * @param currentPlayer
	 * @param scanner
	 * @return true if action was carried out, false if cancelled 
	 */
	public boolean doAction(Player currentPlayer) {
		
		Treasure possibleTreasure = controller.getClaimTreasureCheck(currentPlayer); 
		
		// If no treasure can be claimed
		if(possibleTreasure == null) {
			System.out.println("You cannot claim any treasure at this time. Please select another action");
			return false;
		}
		
		// get user confirmation
		boolean proceed = getConfirmation(possibleTreasure);
		if(proceed == false) {
			return false;
		}
				
		controller.claimTreasure(currentPlayer, possibleTreasure);
		System.out.println(possibleTreasure + " has been captured!");
		
		printUnclaimed();
		
		return true;
	}
	
	/**
	 * printUnclaimed
	 * 	Print unclaimed treasures
	 */
	private void printUnclaimed() {
		Set<Treasure> unClaimed = controller.getUnclaimedTreasures();
		if(unClaimed.isEmpty()) {
			System.out.println("You've claimed all treasures! Make your way to Fools' landing to get off the island!");
		}
		else {
			System.out.println("You still need to claim " + unClaimed);
		}
	}

	/**
	 * getConfirmation
	 * 	Make sure the user wants to claim this treasure
	 * @param possibleTreasure
	 * @return true if user wants to continue
	 */
	private boolean getConfirmation(Treasure possibleTreasure) {
		System.out.println("You can claim the " + possibleTreasure );
		System.out.println("Would you like to do so? [y/n]");
		
		boolean userAns = ViewInputTools.yesNo(user);

		return userAns;
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
	
	/**
	 * setController
	 * 	assign action controller
	 * @param controller
	 */
	public void setup(Scan user, ClaimTreasureController controller) {
		this.user = user;
		this.controller = controller;
	}
	
	
}
