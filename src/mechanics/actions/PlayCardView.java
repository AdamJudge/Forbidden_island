/**
 * Class Name: ShoreUp
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 23/11/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import elements.board.Tile;
import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.pawns.Engineer;
import elements.pawns.Pawn;
import mechanics.TurnController;
import players.Player;
import setup.ParseNumberInputs;

public class PlayCardView  extends ActionView{

	private static PlayCardView playCardView = null;
	private TurnController controller;
	
	/**
	 * getInstance
	 * 	get singleton instance of ShoreupView
	 * @param controller - controller associated with the view
	 * @return shoreupView (singleton instance)
	 */
	public static PlayCardView getInstance(TurnController controller) {
		if(playCardView == null) { 
			playCardView = new PlayCardView(controller);
		}
		return playCardView;
	}
	
	/**
	 * ShoreupView Constructor
	 * @param controller	 - turn controller 
	 */
	private PlayCardView(TurnController controller) {
		this.controller = controller;
	}

	public boolean doAction(Player player, Scanner user) throws IOException {
		int iter=0;
		System.out.println("Which card do you want to play " + player.getName() +"? (0 to cancel)");
		for (Card c: player.getHand().getCards()) {
			if( c instanceof TreasureCard) { //It always is but to be safe
				//if () {
					System.out.println(((TreasureCard)c).toString());
					iter+=1;
					System.out.println("["+iter+"]: " + c.toString());
				//}
			}
		}
		int cardNum = ParseNumberInputs.main(user, 0, iter);
		//Card cardToPlay = player.getHand().getCards().;
		return false;
	}
}

