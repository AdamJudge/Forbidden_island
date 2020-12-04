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
import mechanics.TurnController;

import java.io.IOException;
import java.util.Scanner;


public class ClaimTreasureView extends ActionView{

	private static ClaimTreasureView claimTreasureView = null;
	private TurnController controller;
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		// Claim treasure and GIVE THE PLAYER THE TREASURE
		return true;
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
