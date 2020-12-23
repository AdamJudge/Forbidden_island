package mechanics.cardActions;

import java.util.ArrayList;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;
import elements.pawns.Pilot;
import mechanics.GamePlay;
import players.Player;

/**
 * HelicopterController (Singleton, MVC)
 * 
 * 	Controller to handle functionality required by the Helicopter View
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 	Methods used to be in CardController (got too big, did too many things)
 * 
 * Date Created: 23/12/20 
 * Last Modified: 23/12/20
 *
 */
public class HelicopterController {

	private static HelicopterController hController = null;
	private boolean flown; // True if a pilot has flown, false if no pilot or pilot has not flown
	
	/**
	 * getHelicopterTiles
	 * 
	 * 	get tiles that can be flown to (all remaining tiles except current location)
	 * 
	 * @param player
	 * @return list of possible tiles
	 */
	public ArrayList<Tile> getHelicopterTiles(Player player){
		Pawn pawn = player.getPawn();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(remainingTiles);
		sortedTiles.remove(pawn.getTile());	// pawn can't fly to its current tile
		return sortedTiles;
	}
	
	/**
	 * tryLeave
	 * 
	 * 	Players want to leave the island
	 * @return true if they can leave, false otherwise;
	 */
	public boolean tryLeave() {
		GamePlay.getInstance().tryLeave();
		//If unable to leave
		if (!GamePlay.getInstance().canLeave()) {
			return false;
		}
		return true;
	}
	
	/**
	 * checkPilotFlown
	 * 	
	 * 	To avoid the helicopter lift counting as flying for a pilot
	 * 	if player's pawn is a pilot, check flight status before helicopter lift
	 * 
	 * @param player
	 */
	public void checkPilotFlown(Player player) {
		Pawn pawn = player.getPawn();
		if(pawn instanceof Pilot) {
			flown = ((Pilot)pawn).getHasFlown();
		}
		else flown = false;
	}
	
	/**
	 * pilotFixFlown
	 * 	
	 * 	To avoid the helicopter lift counting as flying for a pilot
	 * 	if flown was false before the lift, set back to false
	 * 	if pilot had flown this will not change anything
	 * 
	 * @param player
	 */
	public void pilotFixFlown(Player player) {
		Pawn pawn = player.getPawn();
		if(!flown && pawn instanceof Pilot) {
			((Pilot)pawn).resetHasFlown();
		}
	}
	
	/**
	 * getInstance
	 * 	get singleton instance of HelicopterController
	 * @return helicopterController (singleton instance)
	 */
	public static HelicopterController getInstance() {
		if(hController == null) { 
			hController = new HelicopterController();
		}
		return hController;
	}
}
