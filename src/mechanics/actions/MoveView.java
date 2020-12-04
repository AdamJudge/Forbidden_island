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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import elements.board.Tile;
import elements.pawns.*;
import setup.ParseNumberInputs;

/* 
	Check pawns possible return places (TO BE DONE IN PAWN)
	
	MoveTo()
*/

public class Move extends Action{
	public void movePawn(Pawn pawn, Scanner user) throws IOException {
		// Move pawn to elem of array list
		Tile destTile;
		int limit;
		int userNum=-1;
		List<Tile> possibleTiles = new ArrayList<Tile>();
		if (pawn instanceof Explorer) {
			//Override for explorer as method is different
			possibleTiles=((Explorer)pawn).moveCheck();
		} else {
			possibleTiles=pawn.moveCheck();
		}
		
		System.out.println("Which tile do you want to move to? [0-9]");
		limit = possibleTiles.size();
		for (Tile t:possibleTiles) {
			System.out.println(t.getName());
		}
		userNum=ParseNumberInputs.main(user, 0, limit);
		
		destTile=possibleTiles.get(userNum);
		pawn.move(destTile);
		//validTiles=pawn.moveCheck();
	}
}
