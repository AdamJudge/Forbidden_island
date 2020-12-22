package mechanics.setup;

import java.util.Set;

import elements.board.WaterLevel;
import mechanics.GamePlay;
import elements.board.Board;
import elements.board.Tile;
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

public class ObserverSetup {								// TODO does this need to be a singleton? 
	private static ObserverSetup observerSetup = null;

	/**
	 * attachObservers
	 * 	attach observers to pawns and tiles that need to be observed
	 */
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
		
		//Get relevant tiles
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
		//Get all pawns in play to observe
		for(Player p: PlayerList.getInstance().getPlayers()) {
			new PawnObserver(p.getPawn());
		}
		// Game Over Observers
		// Observer water level
		new WaterLevelObserver(WaterLevel.getInstance());
		// If fools landing sinks game over
		new FoolsIslandObserver(foolsLanding);
		// If any pair of treasure tiles sink with out treasure being captured then game over
		new TreasureObserver(whisperingGarden, howlingGarden, howlingGarden.getTreasure());	
		new TreasureObserver(tidalPalace, coralPalace, tidalPalace.getTreasure());	
		new TreasureObserver(templeOfTheSun, templeOfTheMoon, templeOfTheSun.getTreasure());	
		new TreasureObserver(caveOfEmbers, caveOfShadows, caveOfEmbers.getTreasure());	
		
		// Game Win Observer
		new HelicopterLiftWinObserver(GamePlay.getInstance());
	}
	/**
	 * getInstance
	 * @return singleton instance of Observer setup
	 */
	public static ObserverSetup getInstance() {
		if (observerSetup == null) {
			observerSetup = new ObserverSetup();
		}
		return observerSetup;
	}
	
	/**
	 * tearDown
	 * 	used for testing
	 */
	public void tearDown() {
		observerSetup=null;
	}
}
