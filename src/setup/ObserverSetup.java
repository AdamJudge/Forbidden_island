package setup;
import elements.board.WaterLevel;
import elements.pawns.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;
import observers.*;
import players.Player;
import players.PlayerList;

/**
 * ObserverSetup
 * 	Handles setting up and attachment of observers
 * @author Adam Judge
 * @version 1
 * Date created: 10/12/20 
 * Last modified: 10/12/20
 *
 */

public class ObserverSetup {
	private static ObserverSetup observerSetup = null;

	public static ObserverSetup getInstance() {
		if (observerSetup == null) {
			observerSetup = new ObserverSetup();
		}
		return observerSetup;
	}
	
	private ObserverSetup() {
		
	}
	
	public void attachObservers() {
		Set<Tile> tileList = Board.getInstance().getAllTiles();
		Tile forbiddenIsland = null;
		
		for(Tile t:tileList) {
			if (t.getName().equals(TileNames.FOOLS_LANDING)){
				forbiddenIsland  = t;
			}
		}
		
		for(Player p: PlayerList.getInstance().getPlayers()) {
			new PawnObserver(p.getPawn());
		}
		new WaterLevelObserver(WaterLevel.getInstance());
		new ForbiddenIslandObserver(forbiddenIsland);
			
	}
}
