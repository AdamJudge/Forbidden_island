/**
 * Class Name: ActionView
 *
 * 	superclass for action views
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.2
 * 	added printCardList 
 * 
 * Creation Date: 22/10/20
 * Last Modified: 04/12/20
 */

package mechanics.actions;

import players.Player;
import java.util.Scanner;
import java.io.IOException;

public abstract class ActionView{
	
	// TODO Discuss - Possibly remove
	
	public abstract boolean doAction(Player currentPlayer, Scanner user) throws IOException;
	
	
	

}
