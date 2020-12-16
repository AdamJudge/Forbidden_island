package testCases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import elements.cards.TreasureDeck;
import players.*;

public class PlayerTests {

	@Test
	public void fullHandtest() throws IOException {
		
		PlayerList.getInstance().addPlayer(new Player("Adam"));
		
		Player adam = PlayerList.getInstance().getPlayers().get(0);
		
		for(int i=0; i<6; i++) {
			adam.getHand().addCard(TreasureDeck.getInstance().draw());
		}
		
		assertNotEquals("Hand should not exceed 5", 6, adam.getHand().getCards().size());
	}

}
