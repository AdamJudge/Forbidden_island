/**
 * Class Name: ShoreUp
 *
 * View for shoring up action
 * 
 * @author Adam Judege, Catherine Waechter
 * @version 2.1
 *  adjusted for ActionController
 * 
 * Creation Date: 22/10/20
 * Last Modified: 17/12/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.Engineer;
import elements.pawns.Pawn;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import players.Player;

public class ShoreupView  extends ActionView{

	private static ShoreupView shoreupView = null;
	private ActionController controller;
	
	/**
	 * getInstance
	 * 	get singleton instance of ShoreupView
	 * @param controller - controller associated with the view
	 * @return shoreupView (singleton instance)
	 */
	public static ShoreupView getInstance() {
		if(shoreupView == null) { 
			shoreupView = new ShoreupView();
		}
		return shoreupView;
	}
	
	public void setController(ActionController actionController) {
		controller = actionController;
	}

	public boolean doAction(Player player, Scanner user) throws IOException {
		int limit, userNum;
		
		for(int i = 0; i<2; i++) {
			ArrayList<Tile> possibleTiles = controller.getShoreupCheck(player);
			
			limit = possibleTiles.size();
			if(i == 0) {	// printout is different if there is another shore up
			System.out.println("Which tile do you want to shore up? (enter 0 to cancel and pick another action)");
			}
			ViewDisplayTools.printTileList(possibleTiles);
		
			userNum=ViewInputTools.numbers(user, 0, limit);
			if(userNum == 0 && i == 0) {
				return false;
			}
			else if(userNum == 0 && i == 1) {
				break;
			}
			
			Tile chosenTile = possibleTiles.get(userNum-1);
			controller.shoreup(player, chosenTile);
			
			System.out.println("Tile : " + chosenTile + " Status: " + chosenTile.getStatus().name());
			
			if(player.getPawn() instanceof Engineer && i==0) {
				System.out.println("You can shore up another tile for no additional actions!");
				System.out.println("Which tile do you want to shore up? [1-9] (Enter 0 if you don't want to shore up another tile. Will still use an action for first shore-up)");
			}
			else {	// other pawns don't get a second shore up
				break;
			}
		}	
		return true;
	}
}

