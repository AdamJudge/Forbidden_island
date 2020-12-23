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
import elements.cards.FloodDeck;
import elements.cards.FloodDiscard;
import elements.cards.TreasureDeck;
import elements.cards.TreasureDiscard;
import elements.pawns.*;
import mechanics.GamePlay;
import mechanics.Scan;
import mechanics.TurnController;
import mechanics.TurnView;
import mechanics.cardActions.PlayCardView;
import mechanics.actions.ShoreupView;
import mechanics.setup.Setup;
import players.Hand;
import players.Player;
import players.PlayerList;

public class ShoreUpTests {
	private Board testBoard;
	private List<Tile> sortedTiles = new ArrayList<Tile>();
	private Player player1, player2, player3;

	
	@Before
	public void setup() {
		PlayerList.getInstance().tearDown();
		Setup.setupOnly();
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		sortedTiles = testBoard.getSortedTiles();
		
		player1 = new Player("player 1");
		player2 = new Player("player 2");
		player3 = new Player("player 3");
		
		PlayerList.getInstance().addPlayer(player1);
		PlayerList.getInstance().addPlayer(player2);
		PlayerList.getInstance().addPlayer(player3);

		//Normal pawns shoreup abilities
		player1.setPawn(new Navigator());
		//All pawns with unique shoreup abilities
		player2.setPawn(new Explorer());
		player3.setPawn(new Engineer());
		
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

	//Flood all surrounding tiles
	public void floodSurroundingTiles() {
		int[] floodMe = new int[] {2,3,4,7,8,9,13,14,15};
		for (int i:floodMe) {
			sortedTiles.get(i).flood();
		}
		System.out.println(testBoard.toString());
	}
	
	public int numRemainingFloodedTiles() {
		int num=0;
		for (Tile t:sortedTiles) {
			if (t.getStatus().equals(TileStatus.FLOODED)) {
				System.out.println(t.toString() + " is flooded");
				num++;
			}
		}
		return num;
	}

	// Shoreup tiles for normal pawn. Tile above below left and right of pawn will be normal, remaining 5 flooded.
	@Test
	public void normalPawnShoreUp()   { 	
		floodSurroundingTiles();
		
		int actionsRequired=4;
		String input = "1";
		
		for (int i=0;i<actionsRequired;i++) {
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			Scan.getInstance().setScanner(new Scanner(in));
			
			ShoreupView.getInstance().doAction(player1);
		}
		int floodedTileNum = numRemainingFloodedTiles();
		assertEquals("Only four of nine tiles can be shored up", 5, floodedTileNum);
	}
	
	// Shoreup tiles for explorer. Can shoreup in all directions. Only tile underneath pawn remains.
	@Test
	public void explorerShoreUp()   { 	//p2 explorer
		floodSurroundingTiles();
		int actionsRequired=8;
		String input = "1";
		
		for (int i=0;i<actionsRequired;i++) {
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			Scan.getInstance().setScanner(new Scanner(in));
			ShoreupView.getInstance().doAction(player2);
		}
		int floodedTileNum = numRemainingFloodedTiles();
		assertEquals("Only tile underneath players remain.", 1, floodedTileNum);
	}
	
	// Shoreup tiles for engineer. Tile above below left and right of pawn will be normal, remaining 5 flooded. Less actions required
	@Test
	public void engineerPawnShoreUp()   { //p3 engineer
		floodSurroundingTiles();
		int actionsRequired=2;
		
		// Two tiles shored up in one action
		String input = "1 1";
		
		for (int i=0;i<actionsRequired;i++) {
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			Scan.getInstance().setScanner(new Scanner(in));
			
			ShoreupView.getInstance().doAction(player3);
		}
		int floodedTileNum = numRemainingFloodedTiles();
		assertEquals("Only four of nine tiles can be shored up", 5, floodedTileNum);
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
		GamePlay.getInstance().tearDown();
	}
}
