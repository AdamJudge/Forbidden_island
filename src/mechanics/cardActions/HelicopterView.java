package mechanics.cardActions;

import players.Player;
import elements.board.Tile;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import elements.cards.Card;
import mechanics.actions.MoveController;
import mechanics.TurnController;
import mechanics.Scan;

import java.util.ArrayList;

/**
 * HelicopterView (Singleton, MVC)
 * 
 * 	View to display steps of playing a helicopter card and retrieve inputs
 * 	Display option to try to win the game. Will call check for game win
 * 
 * @author Catherine Waechter
 * @version 1.2
 * 	Uses helicopter controller 
 * 
 * Date created: 25/11/20
 * Last modified:23/12/20
 *
 */
public class HelicopterView {

	private static HelicopterView hView = null;
	private HelicopterController hController;
	private MoveController moveController;
	private TurnController turnController;
	private Scan user;
	
	/**
	 * play
	 * 	moves given player's pawn to a requested tile
	 * 	if they try to leave, check if possible. if not, ask for a different input
	 * 
	 * @param player
	 * @param card
	 */
	public void play(Card card, Player player) {
		
		ArrayList<Tile> possibleTiles = hController.getHelicopterTiles(player);
		
		System.out.println("Which tile do you want get a helicopter lift to? (0 to Cancel)");
		ViewDisplayTools.printTileList(possibleTiles);
		
		int leaveIndex = possibleTiles.size()+1;	// add an extra option for leaving the island
		System.out.println("[" + leaveIndex + "]: Try to leave Forbidden Island!");
		
		// if pawn is a pilot, check if it has flown before the helicopter lift
		hController.checkPilotFlown(player);
			
		//If can't leave pick different option.
		int input=leaveIndex;	
		while (input == leaveIndex) {
			input = ViewInputTools.numbers(user, 0, possibleTiles.size()+1);
			if(input == 0) {
				return; // exit without using the card
			}
			
			// if they want to leave
			if (input == possibleTiles.size()+1) { 		 
				boolean leaving = hController.tryLeave();
				if(leaving == false) {
					System.out.println("Conditions not met for leaving! Enter another number to fly to a tile, or 0 to cancel.");
				} else {
					break;
				}
			}
			
			// if they want to move to a tile
			else {
				moveController.move(player, possibleTiles.get(input-1));
			}
		}
		
		// if pilot hadn't flown, reset the hasFlown flag
		hController.pilotFixFlown(player);

		turnController.discard(player, card); // discard helicopter lift card
		
	}
	
	/**
	 * getInstance
	 * 	get singleton instance of HelicopterView
	 * @return actionController (singleton instance)
	 */
	public static HelicopterView getInstance() {
		if(hView == null) { 
			hView = new HelicopterView();
		}
		return hView;
	}
	
	/**
	 * setup
	 * assign controllers
	 * @param user
	 * @param hController
	 * @param moveController
	 * @param turnController
	 */
	public void setup(Scan user, HelicopterController hController, MoveController moveController, TurnController turnController) {
		this.user = user;
		this.hController = hController;
		this.moveController = moveController;
		this.turnController = turnController;
	}
	
}
