/**
 * Class Name: mechanics
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package mechanics;

import java.util.Set;

import elements.board.Board;
import elements.board.WaterLevel;
import players.Player;

public class Game {						// TODO delete? 
	protected Set<Player> playerList;
	protected Board board;
	protected WaterLevel waterLevel;
	
	public Game() {	
	}
	
	public boolean isOver() {
		// O B S E R V E 	M E
		return true;
	}
	
	public void playGame() {
		while(isOver()) {
			
		}
		//Have fun!
	}
}
