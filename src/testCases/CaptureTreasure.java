package testCases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<TileNames> tileList = new ArrayList<TileNames>();
	private Map<TileNames, TreasureNames> treasureMap = new HashMap<TileNames, TreasureNames>();
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private List<TileNames> boardTileNames = new ArrayList<TileNames>();
	private Player player1, player2, player3, player4;
	
	@Before
	public void setup() {
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NORMAL);
		boardTiles = testBoard.getSortedTiles();
		for(Tile t:boardTiles) {
			boardTileNames.add(t.getName());
		}
		
		player1 = new Player("player 1");
		player2 = new Player("player 2");
		player3 = new Player("player 3");
		player4 = new Player("player 4");
		
		PlayerList.getInstance().addPlayer(player1);
		PlayerList.getInstance().addPlayer(player2);
		PlayerList.getInstance().addPlayer(player3);
		PlayerList.getInstance().addPlayer(player4);
		
		player1.setPawn(new Diver());
		player2.setPawn(new Engineer());
		player3.setPawn(new Explorer());
		player4.setPawn(new Navigator());
		
		player1.getPawn().toInitialTile();
		player2.getPawn().toInitialTile();
		player3.getPawn().toInitialTile();
		player4.getPawn().toInitialTile();
		System.out.println(Board.getInstance().toString());
		
		player1.setHand(new Hand());
		player2.setHand(new Hand());
		player3.setHand(new Hand());
		player4.setHand(new Hand());
		
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		//player1.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));
		//player2.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.EARTH_STONE)));

		
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		//player3.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));

		
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
		player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
		//player4.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));	
		System.out.println(Board.getInstance().toString());

	}
	
	public void moveToIncorrectTreasureTiles() {
		//Move players to tile with relevant treasures
		for (Tile t:boardTiles) {
			if(t.getName().equals(TileNames.CAVE_OF_EMBERS)) {
				player2.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.CORAL_PALACE)) {
				player3.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.TEMPLE_OF_THE_MOON)) {
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
			if(t.getName().equals(TileNames.CORAL_PALACE)) {
				player2.getPawn().move(t);
			}
			if(t.getName().equals(TileNames.TEMPLE_OF_THE_MOON)) {
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
		moveToIncorrectTreasureTiles();
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player1));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player2));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player3));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player4));
		moveToCorrectTreasureTiles();
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player1));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player2));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player3));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player4));
		moveToIncorrectTreasureTiles();
		System.out.println(player1.getHand().toString());
		giveAnotherCard();
		System.out.println(player1.getHand().toString());
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player1));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player2));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player3));
		assertNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player4));
	}
	
	@Test
	public void CaptureAllTreasures() {
		//Should be null if player is unable to capture treasure
		moveToCorrectTreasureTiles();
		System.out.println(testBoard);
		giveAnotherCard();
		System.out.println(player1.getHand().toString());
		//All players should be able to capture a treasure now.
		assertNotNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player1));
		assertNotNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player2));
		assertNotNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player3));
		assertNotNull(TurnController.getInstance(TurnView.getInstance()).getClaimTreasureCheck(player4));
	}

	@After
	public void tearDown() {
		testBoard.tearDown();
	}
}
