/**
 * Class Name: ActionView
 *
 * 	superclass for action views
 * 
 * Author: @author Adam Judge, Catherine Waechter
 * Version: @version 1.1
 * 	added doAction and printTileList
 * Creation Date: 22/10/20
 * Last Modified: 03/12/20
 */

package mechanics.actions;

import players.Player;
import players.PlayerList;
import elements.board.Tile;
import elements.cards.Card;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

/* Three abstract classes to be implemented by actions.
	
	Print all actions for user selection m/s/g/t
	Check conditions/possible
	Check conditions, i.e player location, prints list of actions to terminal (m/s/g/t) TO BE DONE IN THIS CLASS
	do_action() //Takes in input and does action if possible TO BE DONE IN SUB CLASS

*/
public abstract class ActionView{
	//protected ArrayList<String> actionList = new ArrayList<String>();
	// Array list of actions
	// Selected element in array list
	
	public abstract boolean doAction(Player currentPlayer, Scanner user) throws IOException;
	
	public void printTileList(ArrayList<Tile> tiles) {
		int i = 1;
		for (Tile tile :tiles) {
			System.out.println("[" + i + "]" + tile.getName());
			i++;
		}
	}
	
	public void printCardList(ArrayList<Card> cards) {
		int i = 1;
		for (Card card : cards) {
			System.out.println("[" + i + "]" + card);
			i++;
		}
	}
	
	/**
	 * printPlayerList 
	 * 	print list of players, except currentPlayer
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
	
	public void getActions() {
		// Will take an array list from something
	}
	
	public int checkAction() {
		return 0;
		// Print array list, get user input, select action
	}
	
	//public abstract void doAction();
}
