/**
 * Class Name: MoveView
 *
 * 	view to display move action
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.2
 * 	adjusted for ActionController
 * 	
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.*;
import mechanics.TurnController;
import mechanics.ViewDisplayTools;
import mechanics.setup.ParseNumberInputs;
import players.Player;
import players.PlayerList;


public class MoveView extends ActionView{
	
	private Player playerToMove;
	private Scanner user;
	
	private static MoveView moveView = null;
	private ActionController controller;
	private TurnController turnController;
	
	/**
	 * getInstance
	 * 	get singleton instance of MoveView
	 * @param controller - controller associated with the view
	 * @return moveView (singleton instance)
	 */
	public static MoveView getInstance() {
		if(moveView == null) { 
			moveView = new MoveView();
		}
		return moveView;
	}
	
	public void setController(TurnController turnController, ActionController controller) {
		this.controller = controller;
		this.turnController = turnController;
	}
	
	public void doSwim(Player player, ArrayList<Tile> possibleTiles) throws IOException {
		System.out.println("The tile " + player + " was on sank!");
		System.out.println("Where would you like to swim? ");
		
		ViewDisplayTools.printTileList(possibleTiles);
		
		int userNum = ParseNumberInputs.main(user, 1, possibleTiles.size());
		controller.move(player, possibleTiles.get(userNum-1));
		
	}
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		
		this.user = user;
		playerToMove = currentPlayer;
	
		// Navigator exception
		if(currentPlayer.getPawn() instanceof Navigator) {  // TODO do the check in controller? 
			playerToMove = navigatorException(currentPlayer);
		}
		
		System.out.println(playerToMove + " can move to ");
		ArrayList<Tile> possibleTiles = controller.getMoveCheck(playerToMove);
		int limit = possibleTiles.size();
		
		System.out.println("Which tile do you want to move to? (Enter 0 to cancel and pick another action)");
		ViewDisplayTools.printTileList(possibleTiles);
		int userNum=ParseNumberInputs.main(user, 0, limit);
		if(userNum == 0) {
			return false;
		}
		Tile newTile = controller.move(playerToMove, possibleTiles.get(userNum-1));
		System.out.println(playerToMove + " has moved to " + newTile);
		
		return true;
	}
	
	private Player navigatorException(Player currentPlayer) throws IOException{
		
		System.out.println("Who would you like to move?");
		
		ArrayList<Player> validPlayers = turnController.getPlayers();
		ViewDisplayTools.printPlayerList(validPlayers, currentPlayer);

		int input=ParseNumberInputs.main(user, 1, validPlayers.size());
		return PlayerList.getInstance().getPlayers().get(input-1);
	
	}
}
