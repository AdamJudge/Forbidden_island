/**
 * Class Name: Player
 *
 * DETAILS
 * 
 * Author: @author Adam Judge , Catherine Waechter
 * Version: @version 1.1
 * 	Added getPawn
 * 
 * Creation Date: 22/10/20
 * Last Modified: 25/11/20
 */

package players;

import elements.pawns.Pawn;

public class Player {
	protected String name;
	protected Pawn pawn;
	protected Hand hand;	
	
	public Player(String name) {
		this.name=name;
	}
	
	public void setPawn(Pawn pawn) {
		this.pawn=pawn;
	}
	
	public void setHand(Hand hand) {
		this.hand=hand;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * getPawn
	 * @return player's pawn
	 */
	public Pawn getPawn() {
		return pawn;
	}
	
	@Override
	public String toString() {
		return ("Player Name: " + this.name + ", pawn: " + this.pawn.toString() + ", hand: TBD");
	}
}
