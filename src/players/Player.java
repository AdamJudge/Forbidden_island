/**
 * Class Name: Player
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package players;

import elements.pawns.Pawn;

public class Player {
	protected String name;
	protected Pawn pawn;
	protected Hand hand;	
	
	public Player(String name, Pawn pawn) {
		this.name=name;
		this.pawn=pawn;
	}
	
	public void setHand(Hand hand) {
		this.hand=hand;
	}
}
