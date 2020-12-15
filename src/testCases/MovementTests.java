package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import elements.board.*;
import elements.pawns.*;
import mechanics.TurnController;
import mechanics.TurnView;
import mechanics.actions.MoveView;
import mechanics.actions.ShoreupView;
import players.Hand;
import players.Player;
import players.PlayerList;

public class MovementTests {
	private Board testBoard;
	private List<Tile> sortedTiles = new ArrayList<Tile>();
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
		
		//Move all players to center tile
		for (Tile t:sortedTiles) {
			t.shoreup();
			if (t.getX()==2 && t.getY()==2) {
				for (Player p:PlayerList.getInstance().getPlayers()) {
					p.getPawn().move(t);
				}
			}
		}
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

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// NORMAL
	// Shoreup tiles for normal pawn. Tile above below left and right of pawn will be normal, remaining 5 flooded.
	@Test
	public void allNormalTilesNormalPawn_Success() throws IOException { 	
		normalTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Possible
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player1, scanner);

		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should be able to move to up to a 4th, different tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allNormalTilesNormalPawn_Failure() throws IOException { 	
		normalTileSetup();
		Tile initTile = player1.getPawn().getTile();

		// Not Possible
		String input = "5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player1, scanner);

		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Normal Pawn should not be able to move to a 5th tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allNormalTilesNavigator_Success() throws IOException { 	
		normalTileSetup();
		Tile initTile = player2.getPawn().getTile();

		//TODO adam
		String input = "1 4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);

		Tile finalTile = player2.getPawn().getTile();
	}
	
	@Test
	public void allNormalTilesNavigator_Failure() throws IOException { 	
		normalTileSetup();
		Tile initTile = player2.getPawn().getTile();

		//TODO adam
		String input = "1 5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);

		Tile finalTile = player2.getPawn().getTile();
	}
	
	@Test
	public void allNormalTilesExplorer_Success() throws IOException { 	
		normalTileSetup();
		Tile initTile = player3.getPawn().getTile();

		String input = "8";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player3, scanner);

		Tile finalTile = player3.getPawn().getTile();
		assertNotEquals("The Explorer should be able to move up to an 8th, different tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allNormalTilesExplorer_Failure() throws IOException { 	
		normalTileSetup();
		Tile initTile = player3.getPawn().getTile();

		String input = "9 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player3, scanner);

		Tile finalTile = player3.getPawn().getTile();
		assertEquals("The Explorer should not be able to move to an 9th with this board setup", initTile, finalTile);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FLOODED
	// Shoreup tiles for normal pawn. Tile above below left and right of pawn will be normal, remaining 5 flooded.
	@Test
	public void allFloodedTilesNormalPawn_Success() throws IOException { 	
		floodTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Possible
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player1, scanner);
		
		Tile finalTile = player1.getPawn().getTile();
		assertNotEquals("The Normal Pawn should be able to move to up to a 4th, different tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allFloodedTilesNormalPawn_Failure() throws IOException { 	
		floodTileSetup();
		Tile initTile = player1.getPawn().getTile();

		//Not Possible
		String input = "5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player1, scanner);
		
		Tile finalTile = player1.getPawn().getTile();
		assertEquals("The Normal Pawn should not be able to move to a 5th tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allFloodedTilesNavigator_Success() throws IOException { 	
		floodTileSetup();
		Tile initTile = player2.getPawn().getTile();

		//TODO adam
		String input = "1 4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);
	}
	
	@Test
	public void allFloodedTilesNormalNavigator_Failure() throws IOException { 	
		floodTileSetup();

		//TODO adam
		String input = "1 5 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);
		
		input = "2 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);

		input = "3 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);
		
		input = "4 5 0";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player2, scanner);
	}
	
	@Test
	public void allFloodedTilesExplorer_Success() throws IOException { 	
		floodTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Possible
		String input = "8";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player3, scanner);
		
		Tile finalTile = player3.getPawn().getTile();
		assertNotEquals("The Explorer should be able to move up to an 8th, different tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allFloodedTilesExplorer_Failure() throws IOException { 	
		floodTileSetup();
		Tile initTile = player3.getPawn().getTile();

		// Not Possible
		String input = "9 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player3, scanner);
		
		Tile finalTile = player3.getPawn().getTile();
		assertEquals("The Explorer should not be able to move to a 9th tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allFloodedTilesDiver_Success() throws IOException { 	
		floodTileSetup();
		Tile initTile = player4.getPawn().getTile();

		// Possible
		String input = "18";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player4, scanner);

		Tile finalTile = player4.getPawn().getTile();
		assertNotEquals("The Diver should be able to move up to an 18th, different tile with this board setup", initTile, finalTile);
	}
	
	@Test
	public void allFloodedTilesDiver_Failure() throws IOException { 	
		floodTileSetup();
		TileNames initTile = player4.getPawn().getTile().getName();
		
		// Not Possible
		String input = "19 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		MoveView.getInstance(TurnController.getInstance(TurnView.getInstance())).doAction(player4, scanner);
		
		TileNames finalTile = player4.getPawn().getTile().getName();
		assertEquals("The Diver should not be able to move to a 19th tile with this board setup", initTile, finalTile);
	}

	@After
	public void tearDown() {
		testBoard.tearDown();
	}
}
