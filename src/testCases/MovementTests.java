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
import elements.cards.FloodDeck;
import elements.cards.FloodDiscard;
import elements.cards.TreasureDeck;
import elements.cards.TreasureDiscard;
import elements.pawns.*;
import mechanics.Scan;
import mechanics.TurnView;
import mechanics.actions.MoveView;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.Hand;
import players.Player;
import players.PlayerList;

public class MovementTests {
	private Board testBoard;
	private List<Tile> sortedTiles = new ArrayList<Tile>();
	private Tile midTile;
	private Player player1, player2, player3, player4;

	
	@Before
	public void setup() {
		PlayerList.getInstance().tearDown();
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		sortedTiles = testBoard.getSortedTiles();
		
		player1 = new Player("player 1");
		player2 = new Player("player 2");
		player3 = new Player("player 3");
		player4 = new Player("player 4");
		
		PlayerList.getInstance().addPlayer(player1);
		PlayerList.getInstance().addPlayer(player2);
		PlayerList.getInstance().addPlayer(player3);
		PlayerList.getInstance().addPlayer(player4);

		//Normal pawns movement abilities
		player1.setPawn(new Engineer());
		//All pawns with unique movement abilities
		player2.setPawn(new Navigator());
		player3.setPawn(new Explorer());
		player4.setPawn(new Diver());
		
		for (Player p:PlayerList.getInstance().getPlayers()) {
			p.getPawn().toInitialTile();
			p.setHand(new Hand());
		}
		Setup.setupOnly();
		//Move all players to center tile
		for (Tile t:sortedTiles) {
			t.shoreup();
			if (t.getX()==2 && t.getY()==2) {
				for (Player p:PlayerList.getInstance().getPlayers()) {
					p.getPawn().move(t);
					midTile=t;
				}
			}
		}
	}

	// Test pilots flying/movements
	public void setP1ToPilot() {
		player1.setPawn(new Pilot());
		player1.getPawn().toInitialTile();
		player1.getPawn().move(midTile);
		((Pilot)player1.getPawn()).resetHasFlown();
	}
	
	// Make all tiles Normal
	public void normalTileSetup() {
		for (Tile t:sortedTiles) {
			t.shoreup();
		}
		System.out.println(testBoard.toString());
	}

	//Flood tile setup
	public void floodTileSetup() {
		int[] floodMe = new int[] {0,3,9,10,16,20,21};
		for (int i:floodMe) {
			sortedTiles.get(i).flood();
		}
		System.out.println(testBoard.toString());
	}

	//Flood tile setup 2
	public void sunkenTileSetup() {
		int[] floodMe = new int[] {3,9,14};
		for (int i:floodMe) {
			sortedTiles.get(i).remove();
		}
		System.out.println(testBoard.toString());
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// NORMAL
	// Shoreup tiles for normal pawn. Tile above below left and right of pawn will be normal, remaining 5 flooded.
	
	//Normal Pawn should be able to move to 4 different spots as normal
	@Test
	public void allNormalTilesNormalPawn_Success()   { 	
		normalTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Possible
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should be able to move to up to a 4th, different tile with this board setup", initTile, finalTile);
	}
	
	// Try move to a 5th tile, should not be possible
	@Test
	public void allNormalTilesNormalPawn_Failure()   { 	
		normalTileSetup();
		Tile initTile = player1.getPawn().getTile();

		// Not Possible
		String input = "5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Normal Pawn should not be able to move to a 5th tile with this board setup", initTile, finalTile);
	}
	
	//Test that navigator can move all pawns ADJACENTLY, diver and explorer can't be moved specially.
	@Test
	public void allNormalTilesNavigator_Success()   { 	
		normalTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 4 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should have moved up to a 4th tile", initTileP1, finalTileP1);

		//Move self
		input = "2 4";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertNotEquals("The Engineer should have moved up to a 4th tile", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 4 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertNotEquals("The Explorer should have moved up to a 4th tile, not an 8th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 4 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertNotEquals("The Diver should have moved up to a 4th tile", initTileP4, finalTileP4);
	}
	
	//Try move any pawn by navigator to a 5th tile, should be impossible
	@Test
	public void allNormalTilesNavigator_Failure()   { 	
		normalTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP1, finalTileP1);

		//Move self
		input = "2 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently, not an 8th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP4, finalTileP4);
	}
	
	// Explorer can move adjacently/diagonally, to 8 spaces when surrounded
	@Test
	public void allNormalTilesExplorer_Success()   { 	
		normalTileSetup();
		Tile initTile = player3.getPawn().getTile();

		String input = "8";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player3);

		Tile finalTile = player3.getPawn().getTile();
		assertNotEquals("The Explorer should be able to move up to an 8th, different tile with this board setup", initTile, finalTile);
	}
	
	//Explorer can not move to up to a 9th tile
	@Test
	public void allNormalTilesExplorer_Failure()   { 	
		normalTileSetup();
		Tile initTile = player3.getPawn().getTile();

		String input = "9 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player3);

		Tile finalTile = player3.getPawn().getTile();
		assertEquals("The Explorer should not be able to move to an 9th with this board setup", initTile, finalTile);
	}
	
	//Check that pilot can fly anywhere then move normally
	@Test 
	public void allNormalPilot_Success() {
		normalTileSetup();
		setP1ToPilot();
		System.out.println(testBoard.toString());
		
		Tile initTile = player1.getPawn().getTile();
		//Pilot can fly to any tile on first turn
		String input = "23";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Pilot should be able to move to a 23rd Tile", initTile, finalTile);
		
		//Manually move pawn back to start to get predictable second turn
		player1.getPawn().move(midTile);
		//Move normally on second turn
		input = "4";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		finalTile = player1.getPawn().getTile();
		assertNotEquals("The Pilot should not be able to move to a 4th Tile on their second go", initTile, finalTile);
	}
	
	//Check that pilot can fly correct number of tiles, and can only move up to a 4th after flying once
	@Test 
	public void allNormalPilot_Failure() {
		normalTileSetup();
		setP1ToPilot();
		
		Tile initTile = player1.getPawn().getTile();
		//Pilot cant fly to a 24th tile
		String input = "24 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Pilot should not be able to move to a 24th Tile", initTile, finalTile);
		
		//Move to 23rd tile to use first move, second move should only be able to move to 4.
		input = "23";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);

		finalTile = player1.getPawn().getTile();
		assertNotEquals("The Pilot should have moved to a 24th tile", initTile, finalTile);
		
		//Manually move pawn back to start to get predictable second turn
		player1.getPawn().move(midTile);
		// Pilots movements restricted to up to 4 after flying
		input = "5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);
		finalTile = player1.getPawn().getTile();
		assertEquals("The Pilot should not be able to move to a 5th Tile on their second go", initTile, finalTile);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FLOODED
	// Shoreup tiles for normal pawn. Tile above below left and right of pawn will be normal, remaining 5 flooded.
	// Normal movement as before
	@Test
	public void allFloodedTilesNormalPawn_Success()   { 	
		floodTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Possible
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);
		
		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should be able to move to up to a 4th, different tile with this board setup", initTile, finalTile);
	}
	
	// Normal movement as before
	@Test
	public void allFloodedTilesNormalPawn_Failure()   { 	
		floodTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Not Possible
		String input = "5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);
		
		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Normal Pawn should not be able to move to a 5th tile with this board setup", initTile, finalTile);
	}
	
	// Normal movement as before
	@Test
	public void allFloodedTilesNavigator_Success()   { 	
		floodTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 4 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should have moved up to a 4th tile", initTileP1, finalTileP1);

		//Move self
		input = "2 4";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertNotEquals("The Engineer should have moved up to a 4th tile", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 4 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertNotEquals("The Explorer should have moved up to a 4th tile, not an 8th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 4 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertNotEquals("The Diver should have moved up to a 4th tile", initTileP4, finalTileP4);
	}
	
	// Normal movement as before
	@Test
	public void allFloodedTilesNavigator_Failure()   { 	
		floodTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP1, finalTileP1);

		//Move self
		input = "2 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently, not an 8th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently, diver unable to swim through flooded tiles by navigator.", initTileP4, finalTileP4);
	}
	
	// Normal movement as before
	@Test
	public void allFloodedTilesExplorer_Success()   { 	
		floodTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Possible
		String input = "8";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player3);
		
		Tile finalTile = player3.getPawn().getTile();
		assertNotEquals("The Explorer should be able to move up to an 8th, different tile with this board setup", initTile, finalTile);
	}
	
	// Normal movement as before
	@Test
	public void allFloodedTilesExplorer_Failure()   { 	
		floodTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Not Possible
		String input = "9 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		MoveView.getInstance().doAction(player3);
		
		Tile finalTile = player3.getPawn().getTile();
		assertEquals("The Explorer should not be able to move to a 9th tile with this board setup", initTile, finalTile);
	}
	
	// Diver can move through flooded tiles AND turn, should reach 18 tiles based on board setup.
	@Test
	public void allFloodedTilesDiver_Success()   { 	
		floodTileSetup();
		Tile initTile = player4.getPawn().getTile();

		// Possible
		String input = "18";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		MoveView.getInstance().doAction(player4);

		Tile finalTile = player4.getPawn().getTile();
		assertNotEquals("The Diver should be able to move up to an 18th, different tile with this board setup", initTile, finalTile);
	}
	
	// Diver can't move to a 19th tile with this setup
	@Test
	public void allFloodedTilesDiver_Failure()   { 	
		floodTileSetup();
		TileNames initTile = player4.getPawn().getTile().getName();
		
		// Not Possible
		String input = "19 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player4);
		
		TileNames finalTile = player4.getPawn().getTile().getName();
		assertEquals("The Diver should not be able to move to a 19th tile with this board setup", initTile, finalTile);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// REMOVED
	// Tiles above below and to the right of pawns removed.
	
	// Only one tile available
	@Test
	public void sunkenTilesNormalPawn_Success()   { 	
		sunkenTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Possible
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);
		
		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should be able to move to up to a 4th, different tile with this board setup", initTile, finalTile);
	}
	
	// Only one tile available
	@Test
	public void sunkenTilesNormalPawn_Failure()   { 	
		sunkenTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Not Possible
		String input = "2 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player1);
		
		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Normal Pawn should not be able to move to a 5th tile with this board setup", initTile, finalTile);
	}
	
	// Only one tile available
	@Test
	public void sunkenTilesNavigator_Success()   { 	
		sunkenTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 1 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should have moved up to only available tile", initTileP1, finalTileP1);

		//Move self
		input = "2 1";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertNotEquals("The Engineer should have moved up to only available tile", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 1 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertNotEquals("The Explorer should have moved up to only available tile, not a 5th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 1 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertNotEquals("The Diver should have moved to only available tile", initTileP4, finalTileP4);
	}
	
	// Only one tile available
	@Test
	public void sunkenTilesNavigator_Failure()   { 	
		sunkenTileSetup();
		Tile initTileP1 = player1.getPawn().getTile();	//Engineer
		Tile initTileP2 = player2.getPawn().getTile();	//Navigator
		Tile initTileP3 = player3.getPawn().getTile();	//Explorer
		Tile initTileP4 = player4.getPawn().getTile();	//Diver

		//Move player 1 one space
		String input = "1 2 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP1 = player1.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP1, finalTileP1);

		//Move self
		input = "2 2 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP2 = player2.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently", initTileP2, finalTileP2);
		
		//Move player 3 one space
		input = "3 2 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP3 = player3.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently, not an 5th as if the explorer moved himself.", initTileP3, finalTileP3);
		
		//Move player 4 one space
		input = "4 2 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player2);
		Tile finalTileP4 = player4.getPawn().getTile();
		assertEquals("All pawns moved by the navigator can only move adjacently, diver unable to swim through flooded tiles by navigator.", initTileP4, finalTileP4);
	}
	
	// 5 tiles available to explorer
	@Test
	public void sunkenTilesExplorer_Success()   { 	
		sunkenTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Possible
		String input = "5";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player3);
		
		Tile finalTile = player3.getPawn().getTile();
		assertNotEquals("The Explorer should be able to move up to an 5th, different tile with this board setup", initTile, finalTile);
	}
	
	// 6 tiles unavailable
	@Test
	public void sunkenTilesExplorer_Failure()   { 	
		sunkenTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Not Possible
		String input = "6 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		MoveView.getInstance().doAction(player3);
		
		Tile finalTile = player3.getPawn().getTile();
		assertEquals("The Explorer should not be able to move to a 6th tile with this board setup", initTile, finalTile);
	}
	
	//Diver can move to 8 tiles on this setup, through sunken tiles
	@Test
	public void sunkenTilesDiver_Success()   { 	
		sunkenTileSetup();
		Tile initTile = player4.getPawn().getTile();

		// Possible
		String input = "8";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		MoveView.getInstance().doAction(player4);

		Tile finalTile = player4.getPawn().getTile();
		assertNotEquals("The Diver should be able to move up to an 8th, different tile with this board setup", initTile, finalTile);
	}
	
	// Diver cant move to a 9th tile with this board setup
	@Test
	public void sunkenTilesDiver_Failure()   { 	
		sunkenTileSetup();
		TileNames initTile = player4.getPawn().getTile().getName();
		
		// Not Possible
		String input = "9 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		MoveView.getInstance().doAction(player4);
		
		TileNames finalTile = player4.getPawn().getTile().getName();
		assertEquals("The Diver should not be able to move to a 9th tile with this board setup", initTile, finalTile);
	}

	@After
	public void tearDown() {
		PlayerList.getInstance().tearDown();
		WaterLevel.getInstance().tearDown();
		Board.getInstance().tearDown();
		TreasureDeck.getInstance().tearDown();
		TreasureDiscard.getInstance().tearDown();
		FloodDeck.getInstance().tearDown();
		FloodDiscard.getInstance().tearDown();
		Scan.getInstance().tearDown();
		PlayCardView.getInstance().tearDown();
		TurnView.getInstance().tearDown();
	}
}
