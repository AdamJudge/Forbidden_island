package setup;

import java.io.IOException;
import java.util.Scanner;

import java.util.Set;

import elements.board.Difficulty;
import elements.board.Board;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

import players.*;

/**
 * SetupView
 * 	View to display game setup (implements MVC)
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 30/11/20
 * Last Modified: 03/12/20
 */
public class SetupView {

	private SetupController controller;
	
	private static SetupView setupView = null;	// singleton instance of the view

	/**
	 * getInstance
	 * @return setupView - singleton instance of SetupView
	 */
	public static SetupView getInstance() {
		if(setupView == null)
			setupView = new SetupView();
		return setupView;
	}
	
	/**
	 * run
	 * 	handles steps of setup
	 * @param user - input scanner
	 * @throws IOException
	 */
	public void run(Scanner user) throws IOException {
		
		controller.startSetup();	// sets flag to indicate the game is in setup
		
		welcomeScreen();	// print welcome screen
		
		// set up board/cards (no user interaction required, but print out progress to screen)
		setupBoard();
		setupCards();
		
		// Setup players 
		int numPlayers = this.getPlayerNum(user);
		PlayerList players = getPlayerNames(numPlayers, user);
		outputPlayerInfo(players);
		
		// set up difficulty from water level
		setupWaterLevel(user);
		
		// draw initial flood cards (6)
		controller.drawFloodCards();
		
		controller.endSetup();	// indicate game is no longer in setup
	}
	
	/**
	 * setupCards
	 * 	get controller to create decks and print progress
	 */
	private void setupCards() {
		controller.createDecks();
		System.out.println("Decks created and shuffled! ");
	}
	
	/**
	 * setupBoard
	 * 	get controller to create the board and print it
	 */
	private void setupBoard() {
		Board board = controller.getBoard();
		System.out.println("Board has been set up"); 
	}
	
	/**
	 * setupWaterLevel
	 * 	request difficulty level from user, and send to controller
	 * 
	 * @param user - input scanner
	 * @throws IOException
	 */
	private void setupWaterLevel(Scanner user) throws IOException {
		
		System.out.println("Which difficulty do you want to play?");
		
		int i = 1;
		Difficulty difficulty;
		int input;
		
		List<Difficulty> difficultyList = new ArrayList<Difficulty>(EnumSet.allOf(Difficulty.class)); // need the enum to be listed in order
		for (Difficulty d: difficultyList) {
			System.out.println("[" + i + "]: "+ d);
			i+=1;
		}
		
		input=ParseNumberInputs.main(user, 1, i-1);
		difficulty = difficultyList.get(input-1);
		controller.setGameDifficulty(difficulty);
		
		System.out.println("Water level has been set to: "+ difficulty); 	// TODO might need to fix up for printout of difficulty
	}
	
	/**
	 * outputPlayerInfo
	 * 	print player info given player list
	 * @param players - list of players
	 */
	private void outputPlayerInfo(PlayerList players) {
		for(Player player : players.getPlayers()) {
			System.out.println(player.getName() + " got the " + player.getPawn() + " role");
			System.out.println(player.getName() + " starting hand: ");
			System.out.println(player.getHand());
		}
	}
	
	/**
	 * getPlayerNames
	 *  gets player names for each user. Can contain symbols letters and numbers.
	 *  Sends names to controller and receives list of initialised players
	 *  
	 * @param numPlayers - number of players indicated by user
	 * @param user - input scanner
	 * @return list of players
	 * @throws IOException
	 */
	private PlayerList getPlayerNames(int numPlayers, Scanner user) throws IOException {
		//Sets players names
		boolean duplicate;
		Set<String> names = new HashSet<String>();		// TODO is hashset okay here? or does it need to be ordered? 
		String name;
		for(int i=1; i <= numPlayers; i++) {
			duplicate=true;
			System.out.println("Enter Player " + i + "'s name: ");
			//Loop while entered name is a duplicate
			while (duplicate){
				name=ParseLetterInputs.main(user);
				if (names.contains(name)) {
					System.out.println("Enter an unique name for player "+i+":");
				} else {
					duplicate=false;
					names.add(name);
				}
			}
		}
		return controller.setupPlayers(names);
	}
	
	/**
	 * getPlayerNum
	 * 	get number of players from the user, send to controller
	 * 
	 * @param user - input scanner
	 * @return inputNum - number of players indicated by user
	 * @throws IOException
	 */
	private int getPlayerNum(Scanner user) throws IOException {
		int inputNum;
		System.out.println("How many players?");
		inputNum = ParseNumberInputs.main(user, 2, 4);
		System.out.println("Number of players chosen is: " + inputNum);
		controller.setNumPlayers(inputNum);
		return inputNum; // for the next set of inputs taken from the user.
	}
	
	/**
	 * welcomeScreen
	 * 	print welcome screen
	 */
    private void welcomeScreen(){
    	System.out.println("___________         ___.   .__    .___  .___              .___       .__                     .___");
    	System.out.println("\\_   _____/_________\\_ |__ |__| __| _/__| _/____   ____   |   | _____|  | _____    ____    __| _/");
    	System.out.println(" |    __)/  _ \\_  __ \\ __ \\|  |/ __ |/ __ |/ __ \\ /    \\  |   |/  ___/  | \\__  \\  /    \\  / __ | ");
    	System.out.println(" |     \\(  <_> )  | \\/ \\_\\ \\  / /_/ / /_/ \\  ___/|   |  \\ |   |\\___ \\|  |__/ __ \\|   |  \\/ /_/ | ");
    	System.out.println(" \\___  / \\____/|__|  |___  /__\\____ \\____ |\\___  >___|  / |___/____  >____(____  /___|  /\\____ | ");
    	System.out.println("     \\/                  \\/        \\/    \\/    \\/     \\/           \\/          \\/     \\/      \\/");
    }
    
    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setController(SetupController controller) {
    	this.controller = controller;
    }
    
}
