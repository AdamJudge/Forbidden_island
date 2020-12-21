/**
 * Class Name: Turn
 *
 * DETAILS
 * 
 * @author Adam Judge, Catherine Waechter
 * 
 * @version 1.2
 * 	implement TurnView.setupView 
 * 
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
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
	 */
	public void doTurn(Player player, Scanner user) {
		
		TurnView.getInstance().run(player, user);		// TODO CAT find a better way to organise this
	}
	

}
