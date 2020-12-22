package mechanics.setup;

import java.util.Set;
import java.util.HashSet;

import elements.board.WaterLevel;
import elements.board.Difficulty;
import elements.cards.TreasureDeck;
import elements.cards.FloodDeck;
import elements.cards.Card;
import elements.board.Board;

/**
 * GameSetup
 * 	Handles setup of the board, water level, and decks
 * @author Adam Judge, Catherine Waechter
 * @version 2.2
 * 	initialise inSetup to false
 * 
 * Date created: 23/11/20 
 * Last modified: 16/12/20
 *
 */

public class GameSetup {
	public static GameSetup gameSetup=null;
	
	private boolean inSetup = false; 	// flag to set the setup mode // TODO maybe put in Setup instead
	
	/**
	 * getInstance
	 * @return instance of GameSetup
	 */
	public static GameSetup getInstance() {
		if(gameSetup==null) {
			gameSetup=new GameSetup();
		}
		return gameSetup;
	}
	
	/**
	 * setSetup
	 * @param inSetup
	 */
	public void setSetup(boolean inSetup) {
		this.inSetup = inSetup;
	}
	
	/**
	 * getSetup
	 * @return inSetup - whether the game is in setup mode
	 */
	public boolean getSetup() {
		return inSetup;
	}
	
	/**
	 * drawFloodCards
	 * 	draw 6 flood cards
	 * @return drawnCards - set of flood cards drawn
	 */
	public Set<Card> drawFloodCards() {
		Set<Card> drawnCards = new HashSet<Card>();
		for(int i = 0; i<6; i++) {
			drawnCards.add(FloodDeck.getInstance().draw());
		}
		return drawnCards;
	}
	
	/**
	 * createDecks
	 * 	create and shuffle Treasure and Flood decks
	 */
	public void createDecks() {
		TreasureDeck.getInstance().shuffleDeck();
		FloodDeck.getInstance().shuffleDeck();
	}
	
	/**
	 * setupWaterLevel
	 * 	Creates a WaterLevel instance, and sets difficulty according to input
	 * @param difficulty - difficulty chosen by user
	 */
	public void setupWaterLevel(Difficulty difficulty) {
		WaterLevel waterLevel= WaterLevel.getInstance();	
		waterLevel.setDifficulty(difficulty);
	}
	
	/**
	 * getBoard 
	 * @return singleton instance of the board
	 */
	public Board getBoard() {
		return Board.getInstance();
	}
}
