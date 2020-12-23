package mechanics.actions;

import java.util.ArrayList;

import elements.board.Tile;
import players.Player;

public class ShoreupController {

	private static ShoreupController shoreupController = null;

	/**
	 * getShoreupCheck
	 * 	Get possible tiles the given player's can shore up
	 * @param player 
	 * @return	list of possible tiles
	 */
	public ArrayList<Tile> getShoreupCheck(Player player){	
		return player.getPawn().shoreupCheck();
	}
	
	/**
	 * shoreup
	 * 	Shore up a given tile
	 * @param tile
	 */
	public void shoreup(Tile tile) { 
		tile.shoreup();
	}
	
	/**
	 * getInstance
	 * 	get singleton instance of ShoreupController
	 * @return shoreupController (singleton instance)
	 */
	public static ShoreupController getInstance() {
		if(shoreupController == null) { 
			shoreupController = new ShoreupController();
		}
		return shoreupController;
	}
}
