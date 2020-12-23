package mechanics.actions;

import java.util.ArrayList;

import elements.board.Tile;
import mechanics.Scan;
import mechanics.TurnController;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import players.Player;

/**
 * MoveView (Singleton, MVC)
 *
 * 	view to display move action
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.2
 * 	
 * Creation Date: 22/10/20
 * Last Modified: 19/12/20
 */
public class MoveView {
	
	private static MoveView moveView = null;
	private MoveController controller;
	private TurnController turnController;
	private Player playerToMove;
	private Player currentPlayer;
	private Scan user;
	
	/**
	 * doAction
	 * 	
	 * 	if Navigator, get the player that should be moved
	 * 	get tiles the player to be moved can go to
	 * 	get chosen tile from user
	 * 	move player
	 * 
	 * @return true if an action is used, false if cancelled
	 */
	public boolean doAction(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		
		playerToMove = getPlayerToMove(); // check if Navigator, if so ask which player should be moved
		
		int moves = 1;	
		if(currentPlayer != playerToMove) { 	// Navigator can move other players 2 adjacent tiles
			moves = 2;
		}
		
	 
		
		for(int i = 0; i<moves; i++) {
			ArrayList<Tile> possibleTiles = getPossibleTiles();
			
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
	 * getPossibleTiles
	 * 	
	 * get tiles playerToMove can move to from controller
	 * 
	 * @return ArrayList of possible tiles
	 */
	private ArrayList<Tile> getPossibleTiles() {
		System.out.println(playerToMove + " can move to ");
		
		ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
		if(playerToMove == currentPlayer) {
			 possibleTiles = controller.getMoveCheck(playerToMove);
		}
		else {
			possibleTiles = controller.getMoveOtherCheck(playerToMove, currentPlayer);
		}
		return possibleTiles;
	}
	
	/**
	 * getPlayerToMove
	 * 	Navigator can move another player. need to get required player from user
	 * 
	 * @return player to be moved
	 */
	private Player getPlayerToMove() {
		
		if(turnController.isNavigator(currentPlayer)) { 
			System.out.println("Who would you like to move?");
			
			ArrayList<Player> validPlayers = turnController.getPlayers();
			ViewDisplayTools.printPlayerList(validPlayers, currentPlayer);

			int input = ViewInputTools.numbers(user, 1, validPlayers.size());
			
			return turnController.getPlayers().get(input-1);
		}
		else return currentPlayer;
	}
	
	/**
	 * setController
	 * 	set action and turn controllers
	 * @param turnController
	 * @param controller
	 */
	public void setup(Scan user, TurnController turnController, MoveController controller) {
		this.user = user;
		this.controller = controller;
		this.turnController = turnController;
	}
	
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
}
