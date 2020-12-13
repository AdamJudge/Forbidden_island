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
		Tile foolsLanding = null;
		Tile whisperingGarden = null;
		Tile howlingGarden = null;
		Tile tidalPalace = null;
		Tile coralPalace = null;
		Tile templeOfTheSun = null;
		Tile templeOfTheMoon = null;
		Tile caveOfEmbers = null;
		Tile caveOfShadows = null;
		
		for(Tile t:tileList) {
			switch (t.getName()){
			case FOOLS_LANDING:
				foolsLanding=t;
				break;			
			case WHISPERING_GARDEN:
				whisperingGarden=t;
				break;			
			case HOWLING_GARDEN:
				howlingGarden=t;
				break;			
			case TIDAL_PALACE:
				tidalPalace=t;
				break;			
			case CORAL_PALACE:
				coralPalace=t;
				break;			
			case TEMPLE_OF_THE_SUN:
				templeOfTheSun=t;
				break;			
			case TEMPLE_OF_THE_MOON:
				templeOfTheMoon=t;
				break;			
			case CAVE_OF_EMBERS:
				caveOfEmbers=t;
				break;			
			case CAVE_OF_SHADOWS:
				caveOfShadows=t;
				break;			
			default:
				break;			
			}
		}
		
		for(Player p: PlayerList.getInstance().getPlayers()) {
			new PawnObserver(p.getPawn());
		}
		new WaterLevelObserver(WaterLevel.getInstance());
		new ForbiddenIslandObserver(foolsLanding);
		new TreasureObserver(whisperingGarden, howlingGarden, howlingGarden.getTreasure());	
		new TreasureObserver(tidalPalace, coralPalace, tidalPalace.getTreasure());	
		new TreasureObserver(templeOfTheSun, templeOfTheMoon, templeOfTheSun.getTreasure());	
		new TreasureObserver(caveOfEmbers, caveOfShadows, caveOfEmbers.getTreasure());	
	}
}
