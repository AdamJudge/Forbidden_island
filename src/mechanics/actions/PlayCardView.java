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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import elements.board.Tile;
import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.pawns.Engineer;
import elements.pawns.Pawn;
import mechanics.TurnController;
import mechanics.cardActions.Helicopter;
import mechanics.cardActions.Sandbags;
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
		int iter2=0;
		Map<Integer, Integer> handNumConv = new HashMap<Integer, Integer>();
		String cardName;
		System.out.println("Which card do you want to play " + player.getName() +"? (0 to cancel)");
		String helicopter = TreasureCardTypes.HELICOPTER.toString();
		String sandbags = TreasureCardTypes.SANDBAGS.toString();
		for (Card c: player.getHand().getCards()) {
			iter2+=1;
			if( c instanceof TreasureCard) { //It always is but to be safe
				cardName=((TreasureCard)c).toString();
				if (cardName.equals(helicopter) || cardName.equals(sandbags)) {
					iter+=1;
					handNumConv.put(iter, iter2);
					System.out.println("["+iter+"]: " + c.toString());
				}
			}
		}
		int cardNum = ParseNumberInputs.main(user, 0, iter);
		if (cardNum == 0){
			//Cancel
			return false;
		}
		Card cardToPlay = player.getHand().getCards().get(handNumConv.get(cardNum)-1);
		System.out.println("Card picked: "+cardToPlay.toString());
		player.getHand().takeCard(cardToPlay);
		if (cardToPlay.toString().equals(helicopter)) {
			Helicopter.play(cardToPlay, player, user);
		} else if (cardToPlay.toString().equals(sandbags)) {
			Sandbags.play(cardToPlay, user);
		}
		return false;
	}
}
