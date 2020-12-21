/**
 * Class Name: MoveView
 *
 * 	view to display move action
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.2
 * 	
 * Creation Date: 22/10/20
 * Last Modified: 19/12/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.*;
import mechanics.TurnController;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
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
	
	/**
	 * doAction
	 * 	display and get inputs for movement action
	 * 
	 */
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		
		this.user = user;
		playerToMove = currentPlayer;
	
		// Navigator exception
		if(currentPlayer.getPawn() instanceof Navigator) {  // TODO do the check in controller?  
			playerToMove = navigatorException(currentPlayer);
		}
		
		// Print tiles the player can move to
		System.out.println(playerToMove + " can move to ");
		ArrayList<Tile> possibleTiles = controller.getMoveCheck(playerToMove);
		
		// Get destination from user
		System.out.println("Which tile do you want to move to? (Enter 0 to cancel and pick another action)");
		ViewDisplayTools.printTileList(possibleTiles);
		int userNum=ViewInputTools.numbers(user, 0, possibleTiles.size());
		if(userNum == 0) {
			return false;
		}
		
		// move pawn
		Tile newTile = controller.move(playerToMove, possibleTiles.get(userNum-1));
		System.out.println(playerToMove + " has moved to " + newTile);		// TODO should we check the destination rather than just assuming ? 
		
		return true;
	}
	
	
	/**
	 * doSwim
	 * 	display and get user input to get a pawn to swim
	 * @param player
	 * @param possibleTiles
	 * @throws IOException
	 */
	public void doSwim(Player player, ArrayList<Tile> possibleTiles) throws IOException {
		System.out.println("The tile " + player + " was on sank!");
		System.out.println("Where would you like to swim? ");
		
		ViewDisplayTools.printTileList(possibleTiles);
		
		int userNum = ViewInputTools.numbers(user, 1, possibleTiles.size());
		controller.move(player, possibleTiles.get(userNum-1));
	}
	
	
	/**
	 * navigatorException
	 * 	Navigator can move another player. need to get required player from user
	 * 
	 * @param currentPlayer
	 * @return player to be moved
	 * @throws IOException
	 */
	private Player navigatorException(Player currentPlayer) throws IOException{		// TODO Important - navigator can move player 2 tiles
		
		System.out.println("Who would you like to move?");
		
		ArrayList<Player> validPlayers = turnController.getPlayers();
		ViewDisplayTools.printPlayerList(validPlayers, currentPlayer);

		int input=ViewInputTools.numbers(user, 1, validPlayers.size());
		return PlayerList.getInstance().getPlayers().get(input-1);
	}
	
	/**
	 * setController
	 * 	set action and turn controllers
	 * @param turnController
	 * @param controller
	 */
	public void setController(TurnController turnController, ActionController controller) {
		this.controller = controller;
		this.turnController = turnController;
	}
}
