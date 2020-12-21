package testCases;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import elements.cards.TreasureDeck;
import mechanics.TurnController;
import mechanics.TurnView;
import mechanics.actions.ActionController;
import players.*;

public class PlayerTests {

	@Before 
	public void testSetup() {
		TurnController.getInstance();
		TurnView view = TurnView.getInstance();
		TurnController controller = TurnController.getInstance();
		
		controller.setView(view);
		view.setupView(controller, ActionController.getInstance());
	}
	
	@Test
	public void fullHandtest()   {
		
		PlayerList.getInstance().addPlayer(new Player("Adam"));
		
		Player adam = PlayerList.getInstance().getPlayers().get(0);
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(in);
		
		TurnView.getInstance().setScanner(scanner);
		
		for(int i=0; i<6; i++) {
			adam.getHand().addCard(TreasureDeck.getInstance().draw());
		}
		
		assertEquals("Hand should not exceed 5", 5, adam.getHand().getCards().size());
	}

	
}
