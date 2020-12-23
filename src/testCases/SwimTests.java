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
import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.Hand;
import players.Player;
import players.PlayerList;

public class SwimTests {
	private Board testBoard;
	private List<Tile> sortedTiles = new ArrayList<Tile>();
	private Player player1;
	private Tile startingTile;

	
	@Before
	public void setup() {
		PlayerList.getInstance().tearDown();
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		sortedTiles = testBoard.getSortedTiles();
		
		player1 = new Player("player 1");
		PlayerList.getInstance().addPlayer(player1);

		//Initial pawn set to place on board
		player1.setPawn(new Engineer());
		player1.getPawn().toInitialTile();
		player1.setHand(new Hand());
		Setup.setupOnly();
		PlayCardView.getInstance();
		
		//Move player to center tile
		for (Tile t:sortedTiles) {
			t.shoreup();
			if (t.getX()==2 && t.getY()==2) {
				startingTile=t;
			}
		}
		//Not testing lose conditions. These will render the test impossible.
		removeObservers();
		GamePlay.getInstance();
	}

	public void removeObservers() {
		for (Tile t:sortedTiles) {
			t.removeObservers();
		}
	}
	
	
	public void moveToCenter() {
		player1.getPawn().move(startingTile);
		System.out.println(testBoard.toString());
	}
	
	//Sunken tile setup 1
	public void sunkenTileSetupSurvivable() {
		int[] floodMe = new int[] {3,9,10,14,16};
		for (int i:floodMe) {
			sortedTiles.get(i).remove();
		}
		System.out.println(testBoard.toString());
	}
	
	//Flood tile setup
	public void sunkenTileSetupExplorerDiverOnlySurvivable() {
		int[] floodMe = new int[] {3,7,9,10,14,16};
		for (int i:floodMe) {
			sortedTiles.get(i).remove();
		}
		System.out.println(testBoard.toString());
	}
	
	//Flood tile setup, remove all but tile players are on and bottom corner tile
	public void sunkenTileSetupDiverOnlySurvivable() {
		int[] floodMe = new int[] {0,1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
		for (int i:floodMe) {
			sortedTiles.get(i).remove();
		}
		System.out.println(testBoard.toString());
	}


	@Test
	public void normalPawnSwimCheck_Success() {
		player1.setPawn(new Engineer());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to four adjacent tiles
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, pawn will not drown", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}

	@Test
	public void normalDiverSwimCheck_Success() {
		player1.setPawn(new Diver());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to four adjacent tiles
		String input = "4";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, Diver will not drown", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}

	@Test
	public void sunkenPawnSwimCheck1_Success() {
		sunkenTileSetupSurvivable();
		player1.setPawn(new Engineer());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, Pawn will not drown", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}

	@Test
	public void sunkenDiverSwimCheck1_Success() {
		
		sunkenTileSetupSurvivable();
		player1.setPawn(new Diver());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, Diver will not drown", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}
	
	//Normal pawn will drown
	@Test
	public void sunkenPawnSwimCheck2_Failure() {
		sunkenTileSetupExplorerDiverOnlySurvivable();
		player1.setPawn(new Engineer());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
	
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		System.out.println(noSwimTiles);
		assertTrue("Swim check should return no tiles, player would drown and game ends", noSwimTiles);
	}

	//Explorer can escape diagonally
	@Test
	public void sunkenExlporerSwimCheck2_Success() {
		sunkenTileSetupExplorerDiverOnlySurvivable();
		player1.setPawn(new Explorer());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
	
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, Explorer escapes diagonally", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}
	@Test
	public void sunkenDiverSwimCheck2_Success() {
		sunkenTileSetupExplorerDiverOnlySurvivable();
		player1.setPawn(new Diver());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Swim check should return tiles, Diver will not drown", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
	}
		
	// Explorer will drown as all surrounding tiles removed
	@Test
	public void sunkenExlporerSwimCheck2_Failure() {
		sunkenTileSetupDiverOnlySurvivable();
		player1.setPawn(new Explorer());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
	
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertTrue("Swim check should not return tiles, Explorer will drown", noSwimTiles);
	}
	
	//Diver can escape if there is only one tile remaining
	@Test
	public void sunkenDiverSwimCheck3_Success() {
		sunkenTileSetupDiverOnlySurvivable();
		player1.setPawn(new Diver());
		moveToCenter();
		assertFalse("Game is not over before player drowns", GamePlay.getInstance().getGameOver());
		
		//Pawn should be able to swim to only remaining adjacent tile
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		
		player1.getPawn().getTile().remove();
		boolean noSwimTiles = player1.getPawn().swimCheck().isEmpty();
		assertFalse("Diver will always swim to the only remaining tile", noSwimTiles);
		assertFalse("Game is not over as player does not drown", GamePlay.getInstance().getGameOver());
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
	}
}
