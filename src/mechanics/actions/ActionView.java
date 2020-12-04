/**
 * Class Name: ActionView
 *
 * 	superclass for action views
 * 
 * Author: @author Adam Judge, Catherine Waechter
 * Version: @version 1.2
 * 	added printCardList 
 * 
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
 */

package mechanics.actions;

import players.Player;
import players.PlayerList;
import elements.board.Tile;
import elements.cards.Card;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public abstract class ActionView{
	
	public abstract boolean doAction(Player currentPlayer, Scanner user) throws IOException;
	
	/**
	 * printTileList 
	 * 	print given list of tiles
	 * @param tiles
	 */
	public void printTileList(ArrayList<Tile> tiles) {
		int i = 1;
		for (Tile tile :tiles) {
			System.out.println("[" + i + "]" + tile.getName());
			i++;
		}
	}
	
	/**
	 * printCardList
	 *  prints given cards
	 * @param cards
	 */
	public void printCardList(ArrayList<Card> cards) {
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
	public void printPlayerList(ArrayList<Player> players, Player currentPlayer) {
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
