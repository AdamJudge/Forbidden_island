/**
 * Class Name: GiveCardView
 *
 *  View to display action of giving a card
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.2
 * 	adjusted to suit ActionView 
 * 
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
 */

package mechanics.actions;

import players.Player;
import mechanics.TurnController;

import java.io.IOException;
import java.util.Scanner;

public class GiveCardView  extends ActionView{

	private static GiveCardView giveCardView = null;
	private TurnController controller;

	public boolean doAction(Player currentPlayer, Scanner user) throws IOException {
		// give elem of array list (person) item
		return true;
	}

	
	/**
	 * getInstance
	 * 	get singleton instance of GiveCardView
	 * @param controller - controller associated with the view
	 * @return giveCardView (singleton instance)
	 */
	public static GiveCardView getInstance(TurnController controller) {
		if(giveCardView == null) { 
			giveCardView = new GiveCardView(controller);
		}
		return giveCardView;
	}
	
	/**
	 * GiveCardView Constructor
	 * @param controller	 - turn controller 
	 */
	private GiveCardView(TurnController controller) {
		this.controller = controller;
	}
	
}
