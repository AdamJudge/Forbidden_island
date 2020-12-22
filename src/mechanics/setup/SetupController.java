package mechanics.setup;

import java.io.IOException;
import java.util.Set;

import players.PlayerList;
import elements.board.Board;
import elements.board.Difficulty;
import elements.cards.Card;

/**
 * SetupController (singleton)
 * 
 * 	Controller to handle game setup (implements MVC)
 * 	Model is split into two models : the player setup and the game setup
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	getBoard calls model instead of calling board directly
 * 
 * Date created: 30/11/20
 * Last Modified: 03/12/20
 */
public class SetupController {

	private static SetupController setupController = null; // singleton instance
	private PlayerSetup playerSetup; 	// model for player setup
	private GameSetup gameSetup; 		// model for game setup
	
	/**
	 * startSetup
	 * 	indicates beginning of setup (set flag in model)
	 */
	public void startSetup() {
		gameSetup.setSetup(true);
	}
	
	/**
	 * endSetup
	 * 	indicated end of setup (reset flag in model)
	 */
	public void endSetup() {
		gameSetup.setSetup(false);
	}
	
	/**
	 * getBoard
	 * @return singleton board instance
	 */
	public Board getBoard() {
		return gameSetup.getBoard();
	}
	
	/**
	 * createDecks
	 * 	call model to create treasure and flood decks
	 */
	public void createDecks() {
		gameSetup.createDecks();
	}
	
	/**
	 * setNumPlayers
	 * @param numPlayers - number of players indicated by user
	 */
	public void setNumPlayers(int numPlayers) {
		playerSetup.setNumPlayers(numPlayers);
	}

	/**
	 * setupPlayers
	 * @param names - names of players
	 * @return List of players (names, pawns, and hands initialised)
	 */
	public PlayerList setupPlayers(Set<String> names) {
		return playerSetup.setupPlayerList(names);
	}
	
	/**
	 * setGameDifficulty
	 * @param selectedDifficulty - game difficulty indicated by user
	 */
	public void setGameDifficulty(Difficulty selectedDifficulty) {
		gameSetup.setupWaterLevel(selectedDifficulty);
	}
	
	/**
	 * drawFloodCards
	 *		call model to draw 6 flood cards
	 * @return Set of flood cards drawn
	 */
	public Set<Card> drawFloodCards() {
		return gameSetup.drawFloodCards();
	}
	
	/**
	 * SetupController Constructor
	 * 	assigns view and model for MVC
	 * @param view
	 * @param setup (model)
	 */
	public SetupController(PlayerSetup playerSetup, GameSetup gameSetup) { 	
		this.playerSetup = playerSetup;
		this.gameSetup  = gameSetup;
	}
	
	/**
	 * getInstance
	 * @param view 
	 * @param setup - will be the model
	 * @return setupController singleton Instance of SetupController
	 */
	public static SetupController getInstance(PlayerSetup playerSetup, GameSetup gameSetup) {
		if(setupController == null) {
			setupController = new SetupController(playerSetup, gameSetup);
		}
		return setupController;
	}
	
	
}
