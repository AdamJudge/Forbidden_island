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
import java.io.IOException;
import java.util.Scanner;

/**
 * Setup
 * 	Sets up MVC and Setup classes to carry out player and game setup
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 2.0
 * 	Edited to suit MVC. Removed board setup (done in game setup now)
 * 
 * Date created: 23/11/20 
 * Last modified: 30/11/20
 *
 */


public class Setup {
	private static Setup setup = null;
	
	private PlayerSetup playerSetup; 
	private GameSetup gameSetup;
	
	// Singleton Instance
	public static Setup getInstance() {
		if(setup == null) {
			setup = new Setup();
		}
		return setup;
	}
	
	/**
	 * getPlayerSetup
	 * @return playerSetup instance
	 */
	public PlayerSetup getPlayerSetup() {
		return playerSetup;
	}

	/**
	 * getGameSetup
	 * @return gameSetup instance
	 */
	public GameSetup getGameSetup() {
		return gameSetup;
	}
	
	/**
	 * setupAll
	 * 	Creates instances of Player and Game setup, starts the view and controller
	 * 
	 * @param user - user input scanner
	 * @throws IOException
	 */
	public void setupAll(Scanner user) throws IOException {
		this.playerSetup = PlayerSetup.getInstance();		// create PlayerSetup instance
		this.gameSetup = GameSetup.getInstance();			// create GameSetup instance
		
		SetupView view = SetupView.getInstance();			// create SetupView instance
		SetupController controller = SetupController.getInstance(view, setup);	// create SetupController instance, assign it view and setup instances
		view.setController(controller);		// assign controller to view instance
		view.run(user);		// run the view
	}
	

}