/**
 * Class Name: Setup
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package mechanics;
import java.util.TreeSet;
import elements.board.Board;
import elements.board.WaterLevel;
import players.Player;
import java.util.Set;

public class Setup {
	protected Set<Player> playerList;
	protected Board board;
	protected WaterLevel waterLevel;
	
	/*
	 * Create board
	 * Assign Players
	 * Assign pawn to player of certain type
	 * Place Pawns on board (board.place(pawn))
	 * Deal starting cards
	 * flip 6 flood cards
	 * :)
	 */
	public Setup() {
		playerList = new TreeSet<Player>();
	}
	
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	public void setPlayerPawn() {
		
	}
	
	public void dealStartingCards() {
		// Deal six cards
	}
	
	public void flipStartingFloodCards() {
		
	}
	
	public void startCards() {
		this.dealStartingCards();
		this.flipStartingFloodCards();
	}
	
	@Override
	public String toString() {
		return null;
	}
}
