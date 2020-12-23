package mechanics.actions;

import java.util.ArrayList;

import elements.board.Tile;
import elements.pawns.Navigator;
import mechanics.GamePlay;
import players.Player;

/**
 * MoveController (Singleton, MVC)
 * 
 * Controller to handle movement actions (and swimming)
 * 
 * @author Catherine Waechter
 * @version 1.0 
 * 	Methods were originally in ActionController
 * 
 * Date Created: 23/12/20
 * Last Modified: 23/12/20
 *
 */
public class MoveController {

	private static MoveController moveController = null;
	private MoveView moveView;

	/**
	 * getMoveCheck
	 * 	Get possible tiles the given player's pawn can move to 
	 * @param player whose pawn will move
	 * @return	list of possible tiles
	 */
	public ArrayList<Tile> getMoveCheck(Player player){
		return player.getPawn().moveCheck();
	}
	
	/**
	 * doSwim 
	 * 
	 * check if all players can swim somewhere. 
	 * if so, prompt view to get swim locations
	 * if not, game ends
	 * 
	 * @param players - list of players on the sinking tile
	 */
	public void doSwim(ArrayList<Player> players) {
		// check that all players can swim somewhere first 
		for(Player player : players) {
			player.getPawn().swimCheck();  // if anyone can't swim, observer will update.
		}
		
		if (GamePlay.getInstance().getGameOver()) {
			return;
		}
		
		for(Player player : players) {
			ArrayList<Tile> possibleTiles = player.getPawn().swimCheck();
			moveView.doSwim(player, possibleTiles);
		}

	}
	
	/**
	 * moveOtherCheck
	 * 	For navigator to move other players
	 * @param playerToMove
	 * @param navigatorPlayer
	 * @param destination
	 * @return tile the other player is now on
	 */
	public ArrayList<Tile> getMoveOtherCheck(Player playerToMove, Player navigatorPlayer) {
		if(navigatorPlayer.getPawn() instanceof Navigator) {
			return ((Navigator)navigatorPlayer.getPawn()).moveOtherCheck(playerToMove.getPawn());	
		}
		return null;
	}
		
	/**
	 * move
	 * 	Move player's pawn to the destination
	 * @param player
	 * @param destination
	 * @return
	 */
	public Tile move(Player player, Tile destination) { 
		player.getPawn().move(destination);
		return player.getPawn().getTile();
	}

	/**
	 * setup
	 * assign view
	 * @param moveView
	 */
	public void setup(MoveView moveView) {
		this.moveView = moveView;
	}
	
	/**
	 * getInstance
	 * 	get singleton instance of MoveController
	 * @return moveController (singleton instance)
	 */
	public static MoveController getInstance() {
		if(moveController == null) { 
			moveController = new MoveController();
		}
		return moveController;
	}		
			
}
