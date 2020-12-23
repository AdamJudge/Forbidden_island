package mechanics;

import java.util.ArrayList;

import elements.board.Tile;
import elements.cards.Card;
import players.Player;

/**
 * ViewDisplayTools
 * 
 * Methods used to print standard lists that are often used in Views
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 	Methods used to be in ActionView
 * 
 * Date Created: 20/12/20
 * Last modified: 20/12/20
 */
public abstract class ViewDisplayTools {
	/**
	 * printTileList 
	 * 	print given list of tiles
	 * @param tiles
	 */
	public static void printTileList(ArrayList<Tile> tiles) {
		int i = 1;
		for (Tile tile :tiles) {
			System.out.println("[" + i + "]" + tile);
			i++;
		}
	}
	
	/**
	 * printCardList
	 *  prints given cards
	 * @param cards
	 */
	public static void printCardList(ArrayList<Card> cards) {
		int i = 1;
		for (Card card : cards) {
			System.out.println("[" + i + "]" + card);
			i++;
		}
	}
	
	/**
	 * printPlayerList 
	 * 	print list of players, highglight currentPlayer
	 * @param players - list of players to be printed
	 * @param currentPlayer
	 */
	public static void printPlayerList(ArrayList<Player> players, Player currentPlayer) {
		int i = 1;
		for (Player player : players) {
			if(player != currentPlayer) {
				System.out.println("[" + i + "]" + player);
			}
			else {
				System.out.println("[" + i + "]" + player + "(You)");
			}
			i++;
		}
	}
}
