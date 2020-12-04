/**
 * Class Name: Turn
 *
 * DETAILS
 * 
 * @author Adam Judge, Catherine Waechter
 * 
 * @version 1.2
 * 	Rewritten to suit MVC
 * 
 * Creation Date: 22/10/20
 * Last Modified: 03/12/20
 */
package mechanics;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

import elements.board.Tile;
import players.Player;

public class Turn {

	
	
//	public ArrayList<Tile> getMoveCheck(Player player){
//		return model.getMoveCheck(player);
//	}
	


	private static Turn turn= null;
		
	// Singleton Instance
	public static Turn getInstance() {
		if(turn == null) {
			turn = new Turn();
		}
		return turn;
	}
	
	/**
	 * setupAll
	 * 	Creates instances of Player and Game setup, starts the view and controller
	 * 
	 * @param user - user input scanner
	 * @throws IOException
	 */
	public void doTurn(Player player, Scanner user) throws IOException {
		
		// TODO setting up the turn controller and turn view shouldn't be done here, should jsut be done once
		TurnView view = TurnView.getInstance();			// create SetupView instance
		TurnController controller = TurnController.getInstance(view, turn);	// create SetupController instance, assign it view and setup instances
		view.setController(controller);		// assign controller to view instance
		view.run(player, user);		// run the view
	}
	

}
