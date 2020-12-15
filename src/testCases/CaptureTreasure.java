package testCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import elements.board.*;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.pawns.*;
import elements.treasures.*;
import mechanics.TurnController;
import mechanics.TurnView;
import players.*;

public class CaptureTreasure {
	private Board testBoard;
	private PlayerList playerList;
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private List<TileNames> boardTileNames = new ArrayList<TileNames>();
	private Player player1, player2, player3, player4;
	
	@Before
	public void setup() {
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
	
	public void giveAnotherCard() {
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
	}
	
	@Test
	public void failToCaptureAllTreasures() {
		//Should be null if player is unable to capture treasure
		//Move to correct tile without enough cards
		moveToCorrectTreasureTiles();
		
		for (Player p:playerList.getPlayers()) {
			assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(p));
		}

		//Move to wrong tile and try capture wrong treasure with wrong amount of cards
		moveToIncorrectTreasureTiles();
		for (Player p:playerList.getPlayers()) {
			assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(p));

		}

		//Correct number of cards but wrong treasure type
		giveAnotherCard();
		for (Player p:playerList.getPlayers()) {
			assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(p));
		}

	}
	
	@Test
	public void CaptureAllTreasures() {
		//Move to correct tile and obtain correct number of tiles
		moveToCorrectTreasureTiles();
		giveAnotherCard();
		//All players should be able to capture a treasure now.
		for (Player p:playerList.getPlayers()) {
			//Check if able to claim
			assertNotNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(p));
			//Claim
			p.getPawn().getTile().getTreasure().captureTreasure();
			//Check if claimed
			assertTrue(p.getPawn().getTile().getTreasure().isCaptured());
		}
	}

	@After
	public void tearDown() {
		testBoard.tearDown();
		playerList.tearDown();
	}
}
