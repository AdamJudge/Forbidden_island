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
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.cards.TreasureDeck;
import elements.cards.TreasureDiscard;
import elements.pawns.*;
import mechanics.Scan;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.*;

public class PlayableCardsTest {
	private Board testBoard;
	private PlayerList playerList;
	private List<Tile> boardTiles =new ArrayList<Tile>();
	private List<TileNames> boardTileNames = new ArrayList<TileNames>();
	private Tile toFlood;
	private Player player1;
	
	@Before
	public void setup()   {
		testBoard = Board.getInstance();
		boardTiles = testBoard.getSortedTiles();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		
		//Make sure no tiles are flooded
		for(Tile t:boardTiles) {
			boardTileNames.add(t.getName());
			t.shoreup();
		}
		toFlood = boardTiles.get(0);
		playerList=PlayerList.getInstance();
		player1 = new Player("player 1");
		playerList.addPlayer(player1);
		
		player1.setPawn(new Engineer());
		player1.getPawn().toInitialTile();
		player1.setHand(new Hand());
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.SANDBAGS, null));
		player1.getHand().addCard(new TreasureCard(TreasureCardTypes.HELICOPTER, null));
		
		Setup.setupOnly();
	}
	
	public void floodATile() {
		toFlood.flood();
	}

	@Test
	public void checkAllTilesNormal() {
		for (Tile t:boardTiles) {
			assertEquals("Tile should have normal status.", TileStatus.NORMAL, t.getStatus());
		}
	}
	
	// Check that all tiles are normal, before flooding a tile, checking status, and shoring up.
	@Test
	public void testSandbags()  {
		assertEquals("Tile should have normal status.", TileStatus.NORMAL, toFlood.getStatus());
		// Flood Tile
		floodATile();
		assertEquals("Tile should have flooded status.", TileStatus.FLOODED, toFlood.getStatus());
		
		//Player 1 players 1st card on tile 1
		String input = "1 1 1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));

		//Shoreup takes place here
		PlayCardView.getInstance().doAction(null);
		assertEquals("Tile should have normal status after being shorn up.", TileStatus.NORMAL, toFlood.getStatus());
		
		//Player should discard this card
		int handSize=player1.getHand().getCards().size();
		assertEquals("Hand should be size 1", 1, handSize);
		
		// Only card left should be helicopter
		TreasureCardTypes cardType = ((TreasureCard)player1.getHand().getCards().get(0)).getCardType();
		assertEquals("Card in hand should be a helicopter", TreasureCardTypes.HELICOPTER, cardType);
	}

	//Test helicopter card being played
	@Test
	public void testHelicopter()  {
		//Try helicopter lift to different tile
		String input = "1  2  1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));

		//Get starting tile
		TileNames originalTile = player1.getPawn().getTile().getName();
		PlayCardView.getInstance().doAction(null);
		
		//The player should have now moved to a different tile
		assertFalse(player1.getPawn().getTile().getName().equals(originalTile));
		
		//Only card in hand is sandbag, shows card was discarded
		assertEquals("Hand should be size 1", player1.getHand().getCards().size(), 1);
		assertEquals("Card in hand should be a sandbag",((TreasureCard)player1.getHand().getCards().get(0)).getCardType(), TreasureCardTypes.SANDBAGS);
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
