package mechanics.cardActions;

import java.util.ArrayList;

import mechanics.Scan;
import elements.board.Tile;
import elements.cards.Card;
import mechanics.ViewDisplayTools;
import mechanics.ViewInputTools;
import mechanics.actions.ShoreupController;
import mechanics.TurnController;
import players.Player;

/**
 * Sandbags (Singelton, MVC)
 * 	View to display mechanics of playing a sandbags card  
 * 
 * @author Catherine Waechter, Adam Judge
 * @version 2.2
 * 	made into a view
 * 	
 * Date created: 25/11/20
 * Last modified:22/12/20
 *
 */
public class SandbagsView {		 
	
	private CardActionController controller;
	private ShoreupController shoreupController;
	private TurnController turnController;
	private Scan user;
	public static SandbagsView sView = null;
	
	/**
	 * play 
	 * 	get possible tiles from controller
	 * 	display possible tiles
	 * 	get correct tile from user
	 * 	shore up correct tile
	 * 	discard
	 * 
	 * @param player - the one playing the card
	 * @param card - card to be played and discarded
	 */
	public void play(Player player, Card card) {
		
		ArrayList<Tile> floodedTiles = controller.getFloodedTiles();
		
		System.out.println("Which tile do you want to shore up? (0 to cancel)");
		ViewDisplayTools.printTileList(floodedTiles);
		
		int input = ViewInputTools.numbers(user, 0, floodedTiles.size());
		
		shoreupController.shoreup(floodedTiles.get(input-1));
		
		turnController.discard(player, card);
				
	}
	
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
	
	/**
	 * setup
	 * 	assign controllers
	 * 
	 * @param user
	 * @param cardController
	 * @param shoreupController
	 * @param turnController
	 */
	public void setup(Scan user, CardActionController cardController, ShoreupController shoreupController, TurnController turnController) {
		this.user = user;
		this.controller = cardController;
		this.shoreupController = shoreupController;
		this.turnController = turnController;
	}
}
