package mechanics.actions;

import java.util.ArrayList;

import elements.board.Tile;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import players.Player;
import mechanics.Scan;
import mechanics.TurnController;

/**
 * ShoreUp (Singleton, MVC)
 *
 * View for shoring up action
 * 
 * @author Adam Judege, Catherine Waechter
 * @version 2.2
 *  simplified checks on doAction returns with actionUsed variable
 * 
 * Creation Date: 22/10/20
 * Last Modified: 17/12/20
 */
public class ShoreupView {

	private static ShoreupView shoreupView = null;
	private ShoreupController controller;
	private TurnController turnController;
	private Scan user;
	
	/**
	 * doAction
	 * 	carries out shoreUp
	 * 	Engineer can shore up twice
	 * 
	 */
	public boolean doAction(Player player) {	// TODO can we refactor this into smaller functions?? 
		
		boolean actionUsed = false;
		for(int i = 0; i<2; i++) {		// i=0 for first shoreup, if engineer, can get second shoreup (i=1)
			ArrayList<Tile> possibleTiles = controller.getShoreupCheck(player);
			
			// if no tiles can be shored up
			if(possibleTiles.isEmpty()) {
				System.out.println("No tiles to shore up!");
				return actionUsed;		
			}
			
			Tile chosenTile = getShoreupTile(possibleTiles, i);
			if(chosenTile == null) {
				return actionUsed;
			}
			
			controller.shoreup(chosenTile);
			actionUsed = true;
			
			System.out.println("Tile : " + chosenTile + " Status: " + turnController.getTileStatus(chosenTile)); 	
			
			if(turnController.isEngineer(player)&& i==0) { 		
				System.out.println("You can shore up another tile for no additional actions!");
				System.out.println("Which tile do you want to shore up? (Enter 0 if you don't want to shore up another tile. Will still use an action for first shore-up)");
			}
			else {	// other pawns don't get a second shore up
				break;
			}
		}	
		return actionUsed;
	}
	
	/**
	 * getShoreuptile
	 * 	
	 * Print tiles that can be shored up
	 * get user's selection
	 * 
	 * @param possibleTiles
	 * @param i	 - 0 if first shoreup, 1 if second
	 * @return chosen tile. Null if user wants to cancel
	 */
	private Tile getShoreupTile(ArrayList<Tile> possibleTiles, int i) {
		int limit = possibleTiles.size();
		if(i == 0) {	// printout is different if there is another shore up
		System.out.println("Which tile do you want to shore up? (enter 0 to cancel and pick another action)");
		}
		ViewDisplayTools.printTileList(possibleTiles);
	
		int userNum=ViewInputTools.numbers(user, 0, limit);
		
		if(userNum == 0) {
			return null;
		}
		
		return possibleTiles.get(userNum-1);
	}
	
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
	
	/**
	 * setup
	 * assign user and controllers
	 * @param user
	 * @param turnController
	 * @param shoreupController
	 */
	public void setup(Scan user, TurnController turnController, ShoreupController shoreupController) {
		this.user = user;
		this.turnController = turnController;
		controller = shoreupController;
	}
}

