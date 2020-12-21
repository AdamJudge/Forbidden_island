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

import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.*;
import mechanics.TurnController;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import players.Player;
import players.PlayerList;


public class MoveView {
	
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
	public boolean doAction(Player currentPlayer, Scanner user) {
		
		this.user = user;
		playerToMove = currentPlayer;
	
		// Navigator exception
		if(currentPlayer.getPawn() instanceof Navigator) {  // TODO do the check in controller?  
			playerToMove = navigatorException(currentPlayer);
		}
		
		int moves = 1;
		if(currentPlayer != playerToMove) {
			moves = 2;
		}
		
		for(int i = 0; i<moves; i++) {
			// Print tiles the player can move to
			System.out.println(playerToMove + " can move to ");
			
			ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
			if(playerToMove == currentPlayer) {
				 possibleTiles = controller.getMoveCheck(playerToMove);
			}
			else {
				possibleTiles = controller.getMoveOtherCheck(playerToMove, currentPlayer);
			}
			// Get destination from user
			System.out.println("Which tile do you want to move to?");
			if(i == 0) {
				System.out.println("(Enter 0 to cancel and pick another action)");
			}
			else {
				System.out.println("Enter 0 to stop moving " + playerToMove + ". Action will still be used for 1st move.");
			}
			ViewDisplayTools.printTileList(possibleTiles);
			int userNum = ViewInputTools.numbers(user, 0, possibleTiles.size());
			 
			if(userNum == 0 && i == 0) {
				return false;
			}
			else if(userNum == 0) {
				break;
			}
			// move pawn
			Tile newTile = controller.move(playerToMove, possibleTiles.get(userNum-1));
			
			System.out.println(playerToMove + " has moved to " + newTile);	 
		}
		return true;
	}
	
	
	/**
	 * doSwim
	 * 	display and get user input to get a pawn to swim
	 * @param player
	 * @param possibleTiles
	 */
	public void doSwim(Player player, ArrayList<Tile> possibleTiles) {
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
	 */
	private Player navigatorException(Player currentPlayer) {
		
		System.out.println("Who would you like to move?");
		
		ArrayList<Player> validPlayers = turnController.getPlayers();
		ViewDisplayTools.printPlayerList(validPlayers, currentPlayer);

		int input = ViewInputTools.numbers(user, 1, validPlayers.size());
		
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
