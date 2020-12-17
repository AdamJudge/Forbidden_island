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
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.pawns.*;
import elements.treasures.*;
import mechanics.actions.ClaimTreasureView;
import players.*;

public class CaptureTreasure {
	private Board testBoard;
	private PlayerList playerList;
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private List<TileNames> boardTileNames = new ArrayList<TileNames>();
	private Player player1, player2, player3, player4;
	
	@Before
	public void setup() throws IOException {
		// Get board
		testBoard = Board.getInstance();
		
		//WaterLevel.getInstance().setDifficulty(Difficulty.NORMAL);
		boardTiles = testBoard.getSortedTiles();
		for(Tile t:boardTiles) {
			boardTileNames.add(t.getName());
		}
		
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
		
		
		//Add three of each treasure to each player
		for (int i=0; i<3; i++) {
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));

		}
	}
	
	public void moveToIncorrectTreasureTiles() {
		//Move players to tile with relevant treasures
		for (Tile t:boardTiles) {
			if(t.getName().equals(TileNames.CAVE_OF_EMBERS)) {
				player2.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.TEMPLE_OF_THE_MOON)) {
				player3.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.CORAL_PALACE)) {
				player4.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.WHISPERING_GARDEN)) {
				player1.getPawn().move(t);
			}
		}
	}
	
	public void moveToCorrectTreasureTiles() {
		//Move players to tile with relevant treasures
		for (Tile t:boardTiles) {
			if(t.getName().equals(TileNames.CAVE_OF_EMBERS)) {
				player1.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.TEMPLE_OF_THE_MOON)) {
				player2.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.CORAL_PALACE)) {
				player3.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.WHISPERING_GARDEN)) {
				player4.getPawn().move(t);
			}
		}
	}
	
	public void giveAnotherCard() throws IOException {
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
	}
	
	@Test
	public void failToCaptureAllTreasures() throws IOException {
		//Move to correct tile without enough cards
		moveToCorrectTreasureTiles();
		//Play sandbag on tile
		
		String input = "";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		Boolean result;
		for (Player p:playerList.getPlayers()) {
			result = ClaimTreasureView.getInstance().doAction(p, scanner);
			assertFalse("Player should not be able to claim treasure", result);
			}

		//Move to wrong tile and try capture wrong treasure with wrong amount of cards
		moveToIncorrectTreasureTiles();
		for (Player p:playerList.getPlayers()) {
			result = ClaimTreasureView.getInstance().doAction(p, scanner);
			assertFalse("Player should not be able to claim treasure", result);
		}

		//Correct number of cards but wrong treasure type
		giveAnotherCard();
		for (Player p:playerList.getPlayers()) {
			result=ClaimTreasureView.getInstance().doAction(p, scanner);
			assertFalse("Player should not be able to claim treasure", result);
		}

	}
	
	@Test
	public void CaptureAllTreasures() throws IOException {
		//Move to correct tile and obtain correct number of tiles
		moveToCorrectTreasureTiles();
		giveAnotherCard();
		String input = "y";
		Boolean result;

		//All players should be able to capture a treasure now.
		for (Player p:playerList.getPlayers()) {
			
			//Input for capture action
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			Scanner scanner = new Scanner(in);
			
			//Try Claim
			result=ClaimTreasureView.getInstance().doAction(p, scanner);
			assertTrue("Player should not be able to claim treasure", result);
			
			//Check if claimed
			result=p.getPawn().getTile().getTreasure().isCaptured();
			assertTrue(result);
			scanner = null;
		}
	}

	@After
	public void tearDown() {
		testBoard.tearDown();
		playerList.tearDown();
	}
}
