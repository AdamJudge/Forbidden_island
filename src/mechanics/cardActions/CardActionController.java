package mechanics.cardActions;

import java.util.ArrayList;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.pawns.Pawn;
import elements.pawns.Pilot;
import mechanics.GamePlay;
import players.Player;

public class CardActionController {
	
	static CardActionController cardController = null;
	
	
	/**
	 * getInstance
	 * 	get singleton instance of CardActionController
	 * @return cardController (singleton instance)
	 */
	public static CardActionController getInstance() {
		if(cardController == null) { 
			cardController = new CardActionController();
		}
		return cardController;
	}
	
	public boolean tryLeave() {
		GamePlay.getInstance().tryLeave();
		//If unable to leave
		if (!GamePlay.getInstance().canLeave()) {
			return false;
		}
		return true;
	}
	
	public boolean pilotFlown(Player player) {
		Pawn pawn = player.getPawn();
		if(pawn instanceof Pilot) {
			return ((Pilot)pawn).getHasFlown();
		}
		else return false;
	}
	
	public void pilotFixFlown(Player player, boolean flown) {
		Pawn pawn = player.getPawn();
		if(!flown && pawn instanceof Pilot) {
			((Pilot)pawn).resetHasFlown();
		}
	}
	
	public ArrayList<Tile> getHelicopterTiles(Player player){
		Pawn pawn = player.getPawn();
		
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		
		ArrayList<Tile> sortedTiles = new ArrayList<Tile>();
		sortedTiles.addAll(remainingTiles);
		sortedTiles.remove(pawn.getTile());	// pawn can't fly to its current tile
		return sortedTiles;
	}
	
	
	
}
