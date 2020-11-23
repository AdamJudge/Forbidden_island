/**
 * Class Name: Move
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package mechanics.actions;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;

/* 
	Check pawns possible return places (TO BE DONE IN PAWN)
	
	MoveTo()
*/

public class Move extends Action{
	public void movePawn(Pawn pawn, Scanner user, Board board) {
		// Move pawn to elem of array list
		Set<Tile> validTiles = new HashSet<Tile>();
		//validTiles=pawn.moveCheck();
	}
}
