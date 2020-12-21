package mechanics.cardActions;

import players.Player;
import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;
import elements.pawns.Pilot;
import mechanics.GamePlay;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import elements.cards.Card;
import elements.cards.TreasureDiscard;
import mechanics.actions.ActionController;
import mechanics.TurnController;

import java.util.Set;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	private CardActionController cardController;
	private ActionController actionController;
	private TurnController turnController;
	
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
	
	public void setController(CardActionController cardController, ActionController actionController, TurnController turnController) {
		this.cardController = cardController;
		this.actionController = actionController;
		this.turnController = turnController;
	}
	
	/**
	 * play
	 * 	moves given player's pawn to a requested tile
	 * @param player
	 */
	public void play(Card card, Player player, Scanner user) {
		
		ArrayList<Tile> possibleTiles = cardController.getHelicopterTiles(player);
		
		System.out.println("Which tile do you want get a helicopter lift to? (0 to Cancel)");
		ViewDisplayTools.printTileList(possibleTiles);
		int leaveIndex = possibleTiles.size()+1;
		System.out.println("[" + leaveIndex + "]: Try to leave Forbidden Island!");
		
		int input=leaveIndex;
		
		// if pawn is a pilot, check if it has flown before the helicopter lift
		boolean flown=cardController.pilotFlown(player);
			
		//If can't leave pick different option.
		while (input ==possibleTiles.size()+1) {
			input = ViewInputTools.numbers(user, 0, possibleTiles.size()+1);
			if(input == 0) {
				return; // exit without using the card
			}
			
			if (input == possibleTiles.size()+1) {
				System.out.println("Trying to leave!");
				boolean leaving = cardController.tryLeave();
				if(leaving == false) {
					System.out.println("Conditions not met for leaving! Enter another number to fly to a tile, or 0 to cancel.");
				}
			}
			
			else {
				actionController.move(player, possibleTiles.get(input-1));
			}
		}
		
		// if pilot hadn't flown, reset the hasFlown flag
		cardController.pilotFixFlown(player, flown);

		turnController.discard(player, card); // discard helicopter lift card
		
	}
	
}
