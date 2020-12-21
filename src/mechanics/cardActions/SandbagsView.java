package mechanics.cardActions;

import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Tile;
import elements.cards.Card;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import mechanics.actions.ActionController;
import mechanics.TurnController;
import players.Player;

/**
 * Sandbags
 * 	carries out mechanics of playing a sandbags card and discards the card. 
 * 
 * @author Catherine Waechter, Adam Judge
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified:25/11/20
 *
 */
public class SandbagsView {		// TODO combine helicopter and sandbags? 
	
	private CardActionController controller;
	private ActionController actionController;
	private TurnController turnController;
	public static SandbagsView sView = null;
	
	/**
	 * getInstance
	 * 	get singleton instance of HelicopterView
	 * @return actionController (singleton instance)
	 */
	public static SandbagsView getInstance() {
		if(sView == null) { 
			sView = new SandbagsView();
		}
		return sView;
	}
	
	public void setController(CardActionController cardController, ActionController actionController, TurnController turnController) {
		this.controller = cardController;
		this.actionController = actionController;
		this.turnController = turnController;
	}
	
	/**
	 * play 
	 * 	asks for user input and shores up the requested tile
	 */
	public void play(Player player, Card card, Scanner user) {
		
		ArrayList<Tile> floodedTiles = controller.getFloodedTiles();
		System.out.println("Which tile do you want to shore up? (0 to cancel)");
		ViewDisplayTools.printTileList(floodedTiles);
		
		int input = ViewInputTools.numbers(user, 0, floodedTiles.size());
		
		//input minus one as start from 0
		
		actionController.shoreup(floodedTiles.get(input-1));
		
		turnController.discard(player, card);
				
	}
	
	

}
