package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import elements.board.*;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.pawns.Diver;
import elements.pawns.Engineer;
import elements.pawns.Explorer;
import elements.pawns.Navigator;
import elements.treasures.*;
import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.TurnController;
import mechanics.TurnView;
import mechanics.actions.ActionController;
import mechanics.actions.PlayCardView;
import mechanics.setup.ObserverSetup;
import mechanics.setup.Setup;
import players.Hand;
import players.Player;
import players.PlayerList;

public class WinConditionTests {
	private Board testBoard;
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private Player player1, player2, player3, player4;
	private PlayerList playerList;
	private GamePlay gp;
	private Scanner scanner;

	
	@Before
	public void setup() {
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NORMAL);
		boardTiles = testBoard.getSortedTiles();
		Setup.setupOnly();
		
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
	
	public void givePlayerHelicopter(Player player) {
		player.getHand().addCard(new TreasureCard(TreasureCardTypes.HELICOPTER, null));
	}
	
	public void movePlayerToTile(Player player, TileNames tilename) {
		//Move player specified tile
		for (Tile t:boardTiles) {
			if(t.getName().equals(tilename)) {
				player.getPawn().move(t);
			}
		}
	}
	
	public void captureTreasures(TreasureNames treasureName) {
		testBoard.getTreasures().get(treasureName).captureTreasure();
		System.out.println(testBoard.getTreasures().get(treasureName).getName().toString());
		System.out.println(testBoard.getTreasures().get(treasureName).isCaptured());
	}
	
	@Test
	public void SuccessfulEscape() {
		// Move all players to fools landing
		
		for (Player p: playerList.getPlayers()) {
			movePlayerToTile(p, TileNames.FOOLS_LANDING);
		}
		
		// Capture All Treasures
		captureTreasures(TreasureNames.CRYSTAL_OF_FIRE);
		captureTreasures(TreasureNames.EARTH_STONE);
		captureTreasures(TreasureNames.OCEAN_CHALICE);
		captureTreasures(TreasureNames.STATUE_OF_THE_WIND);
		
		//System.out.println(testBoard.toString());
		givePlayerHelicopter(player1);
		String input = "1 24";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		PlayCardView.getInstance().doAction(player1);
		assertTrue("GamePlay should now have GameOver set to true", gp.getGameOver());
	}
	
	@Test
	public void MissingPlayers() {
		// Capture All Treasures
		captureTreasures(TreasureNames.CRYSTAL_OF_FIRE);
		captureTreasures(TreasureNames.EARTH_STONE);
		captureTreasures(TreasureNames.OCEAN_CHALICE);
		captureTreasures(TreasureNames.STATUE_OF_THE_WIND);
		
		// Try leave with player
		String input = "1 24 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		givePlayerHelicopter(player1);

		movePlayerToTile(player1, TileNames.FOOLS_LANDING);
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if 3 players are missing from fools landing", gp.getGameOver());
		
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		movePlayerToTile(player2, TileNames.FOOLS_LANDING);
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if 2 players are missing from fools landing", gp.getGameOver());
		
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		movePlayerToTile(player3, TileNames.FOOLS_LANDING);
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if 1 player is missing from fools landing", gp.getGameOver());
		
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		movePlayerToTile(player4, TileNames.FOOLS_LANDING);
		PlayCardView.getInstance().doAction(player1);
		assertTrue("Win condition met if no players are missing from fools landing", gp.getGameOver());
	}
	
	@Test
	public void MissingTreasures() {	
		//Move all to fools landing
		movePlayerToTile(player1, TileNames.FOOLS_LANDING);
		movePlayerToTile(player2, TileNames.FOOLS_LANDING);
		movePlayerToTile(player3, TileNames.FOOLS_LANDING);
		movePlayerToTile(player4, TileNames.FOOLS_LANDING);
		
		// Try leave fools landing
		String input = "1 24 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		givePlayerHelicopter(player1);
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if not all treasures captured", gp.getGameOver());
		
		//Capture 1 trasure at a time and retest leave
		captureTreasures(TreasureNames.CRYSTAL_OF_FIRE);
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if not all treasures captured", gp.getGameOver());
		
		captureTreasures(TreasureNames.EARTH_STONE);
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if not all treasures captured", gp.getGameOver());

		
		captureTreasures(TreasureNames.OCEAN_CHALICE);
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		PlayCardView.getInstance().doAction(player1);
		assertFalse("Win condition not met if not all treasures captured", gp.getGameOver());
	
		captureTreasures(TreasureNames.STATUE_OF_THE_WIND);
		in = new ByteArrayInputStream(input.getBytes());
		Scan.getInstance().setScanner(new Scanner(in));
		PlayCardView.getInstance().doAction(player1);
		assertTrue("Win condition met if all treasures captured", gp.getGameOver());
}
	
	@After
	public void tearDown() {
		testBoard.tearDown();
		playerList.tearDown();
		ObserverSetup.getInstance().tearDown();
		GamePlay.getInstance().tearDown();
	}
}
