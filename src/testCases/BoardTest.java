package testCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import elements.board.*;
import elements.treasures.*;

public class BoardTest {
	private Board testBoard;
	private List<TileNames> tileList = new ArrayList<TileNames>();
	private Map<TileNames, TreasureNames> treasureMap = new HashMap<TileNames, TreasureNames>();
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private List<TileNames> boardTileNames = new ArrayList<TileNames>();

	
	@Before
	public void setup() {
		testBoard = Board.getInstance();
		boardTiles = testBoard.getSortedTiles();

		tileList.add(TileNames.BREAKERS_BRIDGE);
		tileList.add(TileNames.BRONZE_GATE);
		tileList.add(TileNames.CAVE_OF_EMBERS);
		tileList.add(TileNames.CAVE_OF_SHADOWS);
		tileList.add(TileNames.CLIFFS_OF_ABANDON);
		tileList.add(TileNames.COPPER_GATE);
		tileList.add(TileNames.CORAL_PALACE);
		tileList.add(TileNames.CRIMSON_FOREST);
		tileList.add(TileNames.DUNES_OF_DECEPTION);
		tileList.add(TileNames.FOOLS_LANDING);
		tileList.add(TileNames.GOLD_GATE);
		tileList.add(TileNames.HOWLING_GARDEN);
		tileList.add(TileNames.IRON_GATE);
		tileList.add(TileNames.LOST_LAGOON);
		tileList.add(TileNames.MISTY_MARSH);
		tileList.add(TileNames.OBSERVATORY);
		tileList.add(TileNames.PHANTOM_ROCK);
		tileList.add(TileNames.SILVER_GATE);
		tileList.add(TileNames.TEMPLE_OF_THE_MOON);
		tileList.add(TileNames.TEMPLE_OF_THE_SUN);
		tileList.add(TileNames.TIDAL_PALACE);
		tileList.add(TileNames.TWILIGHT_HOLLOW);
		tileList.add(TileNames.WATCHTOWER);
		tileList.add(TileNames.WHISPERING_GARDEN);
		
		treasureMap.put(TileNames.BREAKERS_BRIDGE, null);
		treasureMap.put(TileNames.BRONZE_GATE, null);
		treasureMap.put(TileNames.CLIFFS_OF_ABANDON, null);
		treasureMap.put(TileNames.COPPER_GATE, null);
		treasureMap.put(TileNames.CRIMSON_FOREST, null);
		treasureMap.put(TileNames.DUNES_OF_DECEPTION, null);
		treasureMap.put(TileNames.FOOLS_LANDING, null);
		treasureMap.put(TileNames.GOLD_GATE, null);
		treasureMap.put(TileNames.IRON_GATE, null);
		treasureMap.put(TileNames.LOST_LAGOON, null);
		treasureMap.put(TileNames.MISTY_MARSH, null);
		treasureMap.put(TileNames.OBSERVATORY, null);
		treasureMap.put(TileNames.PHANTOM_ROCK, null);
		treasureMap.put(TileNames.SILVER_GATE, null);
		treasureMap.put(TileNames.TWILIGHT_HOLLOW, null);
		treasureMap.put(TileNames.WATCHTOWER, null);
		
		treasureMap.put(TileNames.CAVE_OF_EMBERS, TreasureNames.CRYSTAL_OF_FIRE);
		treasureMap.put(TileNames.CAVE_OF_SHADOWS, TreasureNames.CRYSTAL_OF_FIRE);
		treasureMap.put(TileNames.CORAL_PALACE, TreasureNames.OCEAN_CHALICE);
		treasureMap.put(TileNames.TIDAL_PALACE, TreasureNames.OCEAN_CHALICE);
		treasureMap.put(TileNames.TEMPLE_OF_THE_MOON, TreasureNames.EARTH_STONE);
		treasureMap.put(TileNames.TEMPLE_OF_THE_SUN, TreasureNames.EARTH_STONE);
		treasureMap.put(TileNames.WHISPERING_GARDEN, TreasureNames.STATUE_OF_THE_WIND);
		treasureMap.put(TileNames.HOWLING_GARDEN, TreasureNames.STATUE_OF_THE_WIND);

	}
	
	@Test
	public void checkAllTilesUsed() {

		for(Tile t:boardTiles) {
			boardTileNames.add(t.getName());
		}
		assertTrue("All possible tiles should be on the board",boardTileNames.containsAll(tileList));
	}
	
	@Test
	public void checkTileTreasures() {
		for (Tile t:boardTiles) {
			// if treasure expected is null then getTreasure().getName() will fail.
			if (treasureMap.get(t.getName()) == null) {
				assertEquals("Treasures should be placed on the correct Tiles", treasureMap.get(t.getName()), t.getTreasure());
			} else {
				assertEquals("Treasures should be placed on the correct Tiles", treasureMap.get(t.getName()), t.getTreasure().getName());
			}
		}
	}
	
	@Test
	public void checkPositionsValid() {
		for (Tile t:boardTiles) {
			assertTrue("The tile's position should be valid.", Position.validPosition(t.getX(), t.getY()));
		}
	}

}
