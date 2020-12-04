/**
 * Class Name: ShoreUp
 *
 * DETAILS
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 23/11/20
 */

package mechanics.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import elements.board.Tile;
import elements.pawns.Explorer;
import elements.pawns.Pawn;
import setup.ParseNumberInputs;

public class ShoreUp  extends Action{


	public void shoreUp(Pawn pawn, Scanner user) throws IOException {
		// Move pawn to elem of array list
		Tile destTile;
		int limit;
		int userNum=-1;
		List<Tile> possibleTiles = new ArrayList<Tile>();
		if (pawn instanceof Explorer) {
			//Override for explorer as method is different
			possibleTiles=((Explorer)pawn).shoreupCheck();
		} else {
			possibleTiles=pawn.shoreupCheck();
		}
		
		System.out.println("Which tile do you want to shore up? [0-9]");
		limit = possibleTiles.size();
		for (Tile t:possibleTiles) {
			System.out.println(t.getName());
		}
		userNum=ParseNumberInputs.main(user, 0, limit);
		
		destTile=possibleTiles.get(userNum);
		pawn.shoreup(destTile);
		//validTiles=pawn.moveCheck();
	}
}

