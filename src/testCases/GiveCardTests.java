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
import elements.treasures.Treasure;
import elements.treasures.TreasureNames;
import mechanics.Scan;
import mechanics.actions.GiveCardView;
import mechanics.cardActions.PlayCardView;
import mechanics.setup.Setup;
import players.Hand;
import players.Player;
import players.PlayerList;

public class GiveCardTests {
	private Board testBoard;
	private List<Tile> sortedTiles = new ArrayList<Tile>();
	private Player player1, player2;
	private Tile distantTile;

	
	@Before
	public void setup() {
		PlayerList.getInstance().tearDown();
		testBoard = Board.getInstance();
		WaterLevel.getInstance().setDifficulty(Difficulty.NOVICE);
		sortedTiles = testBoard.getSortedTiles();
		
		//This will be upper left most tile. A distance from the testing tile in the center
		distantTile=sortedTiles.get(0);
		
		player1 = new Player("player 1");
		player2 = new Player("player 2");

		PlayerList.getInstance().addPlayer(player1);
		PlayerList.getInstance().addPlayer(player2);

		//Set two pawns
		player1.setPawn(new Engineer());
		//Pick pawn with ability relating to giving cards
		player2.setPawn(new Messenger());

		
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
				}
			}
		}
	}

	//Give player 4 random treasure cards
	public void setupHand_fewCards(Player p) {
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.HELICOPTER, null));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.SANDBAGS, null));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.CRYSTAL_OF_FIRE)));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.STATUE_OF_THE_WIND)));
	}
	
	//Give player 5 treasure cards to fill hand
	public void setupHand_full(Player p) {
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
		p.getHand().addCard(new TreasureCard(TreasureCardTypes.TREASURE, new Treasure(TreasureNames.OCEAN_CHALICE)));
	}
	
	//Try to give a card from one player to the other while on the same tile
	@Test
	public void GiveCardOnSameTile()   { 	
		setupHand_fewCards(player1);
		int player1HandSize = player1.getHand().getCards().size();
		int player2HandSize = player2.getHand().getCards().size();
		assertEquals("Player 1 should now have four cards",4 , player1HandSize);
		assertEquals("Player 2 should now have no cards",0 , player2HandSize);

		//Give helicopter card
		String input = "1 1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		GiveCardView.getInstance().doAction(player1);
		
		//Check that player 1 no longer has this card
		String cards = player1.getHand().getCards().toString();
		player1HandSize = player1.getHand().getCards().size();
		assertEquals("Player 1 gave player 2 the helicopter", "[SANDBAGS, CRYSTAL_OF_FIRE, STATUE_OF_THE_WIND]", cards);
		//Check player 1's hand size is 3
		assertEquals("Player 1 should now have three cards",3, player1HandSize);
		
		//Check that player 2 now has this card
		cards = player2.getHand().getCards().toString();
		assertEquals("Player 1 gave player 2 a helicopter!", "[HELICOPTER]", cards);
		//Check player 2's hand size is 1
		player2HandSize = player2.getHand().getCards().size();
		assertEquals("Player 1 should now have one card",1, player2HandSize);
	}
	
	//Try give card to other player while other players hand is full. Should discard and accept card
	@Test
	public void GiveCardOnSameTile_FullHand()   { 	
		setupHand_fewCards(player1);
		setupHand_full(player2);
		int player1HandSize = player1.getHand().getCards().size();
		int player2HandSize = player2.getHand().getCards().size();
		
		assertEquals("Player 1 should now have four cards",4 , player1HandSize);
		assertEquals("Player 2 should now have five cards",5 , player2HandSize);

		//Give helicopter card, proceed, don't use card as hand is full, discard player 2's first card (Ocean)
		String input = "1 y 1 n 1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		GiveCardView.getInstance().doAction(player1);
		
		//Check that player 1 no longer has this card
		String cards = player1.getHand().getCards().toString();
		player1HandSize = player1.getHand().getCards().size();
		assertEquals("Player 1 gave player 2 the helicopter", "[SANDBAGS, CRYSTAL_OF_FIRE, STATUE_OF_THE_WIND]", cards);
		//Check player 1's hand size is 3
		assertEquals("Player 1 should now have three cards",3, player1HandSize);
		
		//Check that player 2 now has this card
		cards = player2.getHand().getCards().toString();
		assertEquals("Player 1 gave player 2 a helicopter!", "[OCEAN_CHALICE, OCEAN_CHALICE, OCEAN_CHALICE, OCEAN_CHALICE, HELICOPTER]", cards);
		//Check player 2's hand size is 5
		player2HandSize = player2.getHand().getCards().size();
		assertEquals("Player 1 should now have five cards",5, player2HandSize);
	}
	
	//Try give a card while noone has any cards
	@Test
	public void GiveCardOnSameTile_EmptyHand()   { 	
		int player1HandSize = player1.getHand().getCards().size();
		int player2HandSize = player2.getHand().getCards().size();
		
		assertEquals("Player 1 should now have no cards",0 , player1HandSize);
		assertEquals("Player 2 should now have no cards",0 , player2HandSize);
		
		//Try give player a card. Need 0 to cancel if didn't proceed
		String input = "1 1 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		GiveCardView.getInstance().doAction(player1);
		
		player1HandSize = player1.getHand().getCards().size();
		player2HandSize = player2.getHand().getCards().size();
		
		assertEquals("Player 1 should now have no cards",0 , player1HandSize);
		assertEquals("Player 2 should now have no cards",0 , player2HandSize);
	}

	//Try give card from different tile when not playing as messenger
	@Test
	public void TryGiveCardOnDifferentTile_NotMessenger()   { 	
		setupHand_fewCards(player1);
		int player1HandSize = player1.getHand().getCards().size();
		int player2HandSize = player2.getHand().getCards().size();
		assertEquals("Player 1 should now have four cards",4 , player1HandSize);
		assertEquals("Player 2 should now have no cards",0 , player2HandSize);
		
		//Move engineer to distant tile
		player1.getPawn().move(distantTile);
		
		//Attempt to give card
		String input = "1 1 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		GiveCardView.getInstance().doAction(player1);
		String cards = player1.getHand().getCards().toString();

		//Hands should now be as in setup as no cards were given
		assertEquals("Player 1 could not give any cards", "[HELICOPTER, SANDBAGS, CRYSTAL_OF_FIRE, STATUE_OF_THE_WIND]", cards);
		cards=player2.getHand().getCards().toString();
		assertEquals("Player 2 has no cards", "[]", cards);
	}
	
	//Try messengers ability to give a card from a different tile
	@Test
	public void TryGiveCardOnDifferentTile_Messenger()   { 	
		setupHand_fewCards(player2);
		int player1HandSize = player1.getHand().getCards().size();
		int player2HandSize = player2.getHand().getCards().size();
		assertEquals("Player 2 should now have four cards",4 , player2HandSize);
		assertEquals("Player 1 should now have no cards",0 , player1HandSize);
		
		//Move engineer to distant tile
		player2.getPawn().move(distantTile);
		
		//Attempt to give card
		String input = "1 1 0";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scan.getInstance().setScanner(new Scanner(in));
		GiveCardView.getInstance().doAction(player2);
		String cards = player1.getHand().getCards().toString();

		//Player 2 should have given player 1 the helicopter card from across the map!
		assertEquals("Player 1 should have been given the helicopter", "[HELICOPTER]", cards);
		cards=player2.getHand().getCards().toString();
		assertEquals("Player 2 has no cards", "[SANDBAGS, CRYSTAL_OF_FIRE, STATUE_OF_THE_WIND]", cards);
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
