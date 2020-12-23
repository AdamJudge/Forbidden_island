package testCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import elements.board.*;
import elements.cards.FloodCard;
import elements.pawns.Diver;
import elements.pawns.Engineer;
import elements.pawns.Explorer;
import elements.pawns.Navigator;
import mechanics.GamePlay;
import mechanics.cardActions.CardActionController;
import mechanics.setup.ObserverSetup;
import mechanics.setup.Setup;
import observers.PawnObserver;
import players.Hand;
import players.Player;
import players.PlayerList;

public class LoseConditionTests {
	private Board testBoard;
	private WaterLevel wl;
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private Player player1, player2, player3, player4;
	private PlayerList playerList;
	private GamePlay gp;
	private CardActionController cardController;
	
	@Before
	public void setup() {
		testBoard = Board.getInstance();
		wl=WaterLevel.getInstance();
		wl.setDifficulty(Difficulty.LEGENDARY);
		boardTiles = testBoard.getSortedTiles();
		Setup.setupOnly();
		cardController = CardActionController.getInstance();
		
		//Setup Players
		player1 = new Player("player 1");
		player2 = new Player("player 2");
		player3 = new Player("player 3");
		player4 = new Player("player 4");
		
		playerList = PlayerList.getInstance();
		
		playerList.addPlayer(player1);
		playerList.addPlayer(player2);
		playerList.addPlayer(player3);
		playerList.addPlayer(player4);
		
		player1.setPawn(new Diver());
		player2.setPawn(new Engineer());
		player3.setPawn(new Explorer());
		player4.setPawn(new Navigator());
		
		for (Player p:playerList.getPlayers()) {
			p.getPawn().toInitialTile();
			p.setHand(new Hand());
		}
		
		//Setup Observers
		gp=GamePlay.getInstance();
	} 
	
	public void movePlayerToTile(Player player, TileNames tilename) {
		//Move player specified tile
		for (Tile t:boardTiles) {
			if(t.getName().equals(tilename)) {
				player.getPawn().move(t);
			}
		}
	}
	
	public Tile getTile(TileNames tilename) {
		for (Tile t:boardTiles) {
			if(t.getName().equals(tilename)) {
				return t;
			}
		}
		return null;
	}
	
	// FIRST LOSS CONDITION
	// If fools landing sinks game is over
	@Test
	public void foolsLandingRemoved() {
		cardController.floodTile(new FloodCard(getTile(TileNames.FOOLS_LANDING)));
		//Flood Tile
		assertFalse("Game should not be over with a flooded fools landing", gp.getGameOver());
		//Remove Tile
		cardController.floodTile(new FloodCard(getTile(TileNames.FOOLS_LANDING)));
		assertTrue("Game should end as fools landing has sunken", gp.getGameOver());
	}
	
	// SECOND LOSS CONDITION
	// If two treasure tiles sink then game is over
	@Test
	public void oceanTilesRemoved() {
		//Remove first treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.CORAL_PALACE)));
		cardController.floodTile(new FloodCard(getTile(TileNames.CORAL_PALACE)));
		assertFalse("Game should not be over with only one treasure tile removed", gp.getGameOver());
		
		//Remove Second treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.TIDAL_PALACE)));
		cardController.floodTile(new FloodCard(getTile(TileNames.TIDAL_PALACE)));
		assertTrue("Game should end as both treasure tiles have sunken", gp.getGameOver());
	}
	
	@Test
	public void earthTilesRemoved() {
		//Remove first treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.TEMPLE_OF_THE_MOON)));
		cardController.floodTile(new FloodCard(getTile(TileNames.TEMPLE_OF_THE_MOON)));
		assertFalse("Game should not be over with only one treasure tile removed", gp.getGameOver());
		
		//Remove Second treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.TEMPLE_OF_THE_SUN)));
		cardController.floodTile(new FloodCard(getTile(TileNames.TEMPLE_OF_THE_SUN)));
		assertTrue("Game should end as both treasure tiles have sunken", gp.getGameOver());
	}
	
	@Test
	public void windTilesRemoved() {
		//Remove first treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.HOWLING_GARDEN)));
		cardController.floodTile(new FloodCard(getTile(TileNames.HOWLING_GARDEN)));
		assertFalse("Game should not be over with only one treasure tile removed", gp.getGameOver());
		
		//Remove Second treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.WHISPERING_GARDEN)));
		cardController.floodTile(new FloodCard(getTile(TileNames.WHISPERING_GARDEN)));
		assertTrue("Game should end as both treasure tiles have sunken", gp.getGameOver());
	}
	
	@Test
	public void fireTilesRemoved() {
		//Remove first treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.CAVE_OF_EMBERS)));
		cardController.floodTile(new FloodCard(getTile(TileNames.CAVE_OF_EMBERS)));
		assertFalse("Game should not be over with only one treasure tile removed", gp.getGameOver());
		
		//Remove Second treasure tile
		cardController.floodTile(new FloodCard(getTile(TileNames.CAVE_OF_SHADOWS)));
		cardController.floodTile(new FloodCard(getTile(TileNames.CAVE_OF_SHADOWS)));
		assertTrue("Game should end as both treasure tiles have sunken", gp.getGameOver());
	}
	
	// THIRD LOSS CONDITION
	// If WaterLevel hits 10 game over
	@Test
	public void waterLevelGameOver() {
		//Start from minimum level 1, increase by 1 until 10 and check status throughout
		wl.setDifficulty(Difficulty.NOVICE);
		assertEquals("Water Level should be 1",1,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 2",2,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 3",3,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 4",4,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 5",5,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 6",6,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 7",7,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 8",8,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 9",9,wl.getWaterLevel());
		assertFalse("Game is not over until level hits 10", gp.getGameOver());
		WaterLevel.getInstance().raise_level();
		assertEquals("Water Level should be 10",10,wl.getWaterLevel());
		assertTrue("Game is over now that the level hit 10", gp.getGameOver());
	}
	
	// FOURTH LOSS CONDITION
	// If player drowns then game is over
	@Test
	public void drownedPlayer() {
		// Remove observers
		for (Tile t:boardTiles) {
			t.removeObservers();
		}
		//Use only 1 player for test
		playerList.tearDown();
		playerList=PlayerList.getInstance();
		playerList.addPlayer(player2);
		
		// Add observer for pawns, normally done in observer but need to ignore other observers
		for (Player p:playerList.getPlayers()) {
			new PawnObserver(p.getPawn());
		}
		
		// Move to top left of board
		player2.getPawn().move(testBoard.getSortedTiles().get(0));
		System.out.println(testBoard.toString());
		// Remove tile to right of player
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(1).getName())));
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(1).getName())));
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());

		// Remove tile below player. Player will have nowhere to swim to if tile is removed
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(3).getName())));
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(3).getName())));
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());

		// Remove tile player is standing on
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(0).getName())));
		cardController.floodTile(new FloodCard(getTile(testBoard.getSortedTiles().get(0).getName())));
		assertTrue("The player should have no tiles available to swim to and will drown", player2.getPawn().swimCheck().isEmpty());
		assertTrue("Game is over if player drowns", GamePlay.getInstance().getGameOver());
	}
	
	@After
	public void tearDown() {
		testBoard.tearDown();
		playerList.tearDown();
		ObserverSetup.getInstance().tearDown();
		GamePlay.getInstance().tearDown();
	}
}
