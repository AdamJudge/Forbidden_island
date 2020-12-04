/**
 * Class Name: Move
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.*;
import mechanics.TurnController;
import players.Player;
import players.PlayerList;
import setup.ParseNumberInputs;

/* 
	Check pawns possible return places (TO BE DONE IN PAWN)
	
	MoveTo()
*/

public class MoveView extends ActionView{
	
	private Player playerToMove;
	private Scanner user;
	
	private static MoveView moveView = null;
	private TurnController controller;
	
	/**
	 * getInstance
	 * 	get singleton instance of MoveView
	 * @param controller - controller associated with the view
	 * @return moveView (singleton instance)
	 */
	public static MoveView getInstance(TurnController controller) {
		if(moveView == null) { 
			moveView = new MoveView(controller);
		}
		return moveView;
	}
	
	/**
	 * MoveView Constructor
	 * @param controller	 - turn controller 
	 */
	private MoveView(TurnController controller) {
		this.controller = controller;
	}
	
	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		
		this.user = user;
		playerToMove = currentPlayer;
	
		// Navigator exception
		if(currentPlayer.getPawn() instanceof Navigator) {
			playerToMove = navigatorException(currentPlayer);
		}
		
		// TODO Pilot exception
		
		System.out.println(playerToMove + " can move to ");
		ArrayList<Tile> possibleTiles = controller.getMoveCheck(playerToMove);
		int limit = possibleTiles.size();
		System.out.println("Which tile do you want to move to? (Enter 0 to cancel and pick another action)");
		printTileList(possibleTiles);
		int userNum=ParseNumberInputs.main(user, 0, limit);
		if(userNum == 0) {
			return false;
		}
		Tile newTile = controller.move(playerToMove, possibleTiles.get(userNum-1));
		System.out.println(playerToMove + " has moved to " + newTile);
		
		return true;
	}
	
	private Player navigatorException(Player currentPlayer) throws IOException{
		
		System.out.println("Would you like to move ");
		int i = 1;
		for (Player player : PlayerList.getInstance().getPlayers()) {
			if(player != currentPlayer) {
				System.out.println("[" + i + "]" + player);
			}
			else {
				System.out.println("[" + i + "]" + player + "(You)");
			}
			i++;
		}
		int input=ParseNumberInputs.main(user, 1, i);
		return PlayerList.getInstance().getPlayers().get(input-1);
	
	}
}
