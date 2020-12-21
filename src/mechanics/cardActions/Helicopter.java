package mechanics.cardActions;

import players.Player;
import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;
import elements.pawns.Pilot;
import mechanics.GamePlay;
import mechanics.ViewInputTools;
import elements.cards.Card;
import elements.cards.TreasureDiscard;

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
public class Helicopter {

	/**
	 * play
	 * 	moves given player's pawn to a requested tile
	 * @param player
	 */
	public static void play(Card card, Player player, Scanner user) {
		
		Pawn pawn = player.getPawn();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		remainingTiles.remove(pawn.getTile());	// pawn can't fly to its current tile
		
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(remainingTiles);
		
		//for(int i = 0; i<sortedTiles.size(); i++) {
		//	System.out.println("[" + i + "]: " + sortedTiles.get(i));
		//}

		// Get user Input
		int iter=1;
		
		System.out.println("Which tile do you want get a helicopter lift to?");
		for (Tile t:sortedTiles) {
			System.out.println("["+iter+"]: " + t.getName());
			iter+=1;
		}
		System.out.println("[" + (sortedTiles.size()+1) + "]: Try Leave Forbidden Island!");
		int input=sortedTiles.size()+1;
		//If can't leave pick different option.
		while (input ==sortedTiles.size()+1) {
			input = ViewInputTools.numbers(user, 1, sortedTiles.size()+1);
			
			if (input == sortedTiles.size()+1) {
				System.out.println("Trying to leave!");
				GamePlay.getInstance().tryLeave();
				//If unable to leave
				if (!GamePlay.getInstance().canLeave()) {
					System.out.println("Conditions not met for leaving!");
					continue;
				} else {
					return;
				}
				
			} else {
				pawn.move(sortedTiles.get(input-1));
			}
		}
		// if pawn is a pilot, check if it has flown before the helicopter lift
		boolean flown=false;
		if(pawn instanceof Pilot) {
			flown = ((Pilot)pawn).getHasFlown();
		}
		
		// if pilot hadn't flown, reset the hasFlown flag
		if(!flown && pawn instanceof Pilot) {
			((Pilot)pawn).resetHasFlown();
		}
		
		TreasureDiscard.getInstance().addCard(card); 	// discard helicopter lift card
		
	}
	
}
