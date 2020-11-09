/**
 * Class Name: Setup
 *
 * Singleton Facade for setting up game of Forbidden Island
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 22/10/20
 * Last Modified: 29/10/20
 */

package setup;
import java.util.Scanner;


public class Setup {
	private static Setup setup = null;
	
	// All other private setups such as board/player
	private PlayerSetup playerSetup;
	
	// Singleton Instance
	public static Setup getInstance() {
		if(setup == null) {
			setup = new Setup();
		}
		return setup;
	}
	
	//Private constructor as singleton
	private Setup() {
		this.playerSetup = new PlayerSetup();
	}
	
	public void setupAll(Scanner user) {
		welcomeScreen();
		playerSetup.setupPlayers(user);
	}
	
    public void welcomeScreen(){
    	System.out.println("___________         ___.   .__    .___  .___              .___       .__                     .___");
    	System.out.println("\\_   _____/_________\\_ |__ |__| __| _/__| _/____   ____   |   | _____|  | _____    ____    __| _/");
    	System.out.println(" |    __)/  _ \\_  __ \\ __ \\|  |/ __ |/ __ |/ __ \\ /    \\  |   |/  ___/  | \\__  \\  /    \\  / __ | ");
    	System.out.println(" |     \\(  <_> )  | \\/ \\_\\ \\  / /_/ / /_/ \\  ___/|   |  \\ |   |\\___ \\|  |__/ __ \\|   |  \\/ /_/ | ");
    	System.out.println(" \\___  / \\____/|__|  |___  /__\\____ \\____ |\\___  >___|  / |___/____  >____(____  /___|  /\\____ | ");
    	System.out.println("     \\/                  \\/        \\/    \\/    \\/     \\/           \\/          \\/     \\/      \\/");
    }
}