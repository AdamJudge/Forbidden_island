package mechanics.cardActions;

import players.Player;
import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;
import elements.cards.Card;
import elements.cards.TreasureDiscard;

import java.util.Set;
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
public class Helicopter {

	/**
	 * play
	 * 	moves given player's pawn to a requested tile
	 * @param player
	 */
	public static void play(Card card, Player player) {
		
		Pawn pawn = player.getPawn();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		remainingTiles.remove(pawn.getTile());	// pawn can't fly to its current tile
		
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(remainingTiles);
		
		for(int i = 0; i<sortedTiles.size(); i++) {
			System.out.println("[" + i + "] " + sortedTiles.get(i));
		}
		
		// TODO get user input
		int input = 1; // change to input
		
		pawn.move(sortedTiles.get(input));
		
		TreasureDiscard.getInstance().addCard(card);
		
	}
	
}
