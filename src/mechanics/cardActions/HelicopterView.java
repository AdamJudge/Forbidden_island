package mechanics.cardActions;

import players.Player;
import elements.board.Tile;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import elements.cards.Card;
import mechanics.actions.ActionController;
import mechanics.TurnController;
import mechanics.Scan;

import java.util.ArrayList;

/**
 * Helicopter
 * 	carries out mechanics of playing a helicopter lift card and discards the card. 
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified:25/11/20
 *
 */
public class HelicopterView {

	private static HelicopterView hView = null;
	private HelicopterController hController;
	private ActionController actionController;
	private TurnController turnController;
	private Scan user;
	
	/**
	 * play
	 * 	moves given player's pawn to a requested tile
	 * @param player
	 */
	public void play(Card card, Player player) {
		
		ArrayList<Tile> possibleTiles = hController.getHelicopterTiles(player);
		
		System.out.println("Which tile do you want get a helicopter lift to? (0 to Cancel)");
		ViewDisplayTools.printTileList(possibleTiles);
		int leaveIndex = possibleTiles.size()+1;
		System.out.println("[" + leaveIndex + "]: Try to leave Forbidden Island!");
		
		int input=leaveIndex;
		
		// if pawn is a pilot, check if it has flown before the helicopter lift
		hController.checkPilotFlown(player);
			
		//If can't leave pick different option.
		while (input ==possibleTiles.size()+1) {
			input = ViewInputTools.numbers(user, 0, possibleTiles.size()+1);
			if(input == 0) {
				return; // exit without using the card
			}
			
			if (input == possibleTiles.size()+1) {
				System.out.println("Trying to leave!");
				boolean leaving = hController.tryLeave();
				if(leaving == false) {
					System.out.println("Conditions not met for leaving! Enter another number to fly to a tile, or 0 to cancel.");
				} else {
					break;
				}
			}
			
			else {
				actionController.move(player, possibleTiles.get(input-1));
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
	
	public void setup(Scan user, HelicopterController hController, ActionController actionController, TurnController turnController) {
		this.user = user;
		this.hController = hController;
		this.actionController = actionController;
		this.turnController = turnController;
	}
	
}
