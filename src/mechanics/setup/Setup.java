package mechanics.setup;

import elements.cards.FloodDeck;
import mechanics.Scan;
import mechanics.TurnController;
import mechanics.actions.ClaimTreasureController;
import mechanics.actions.ClaimTreasureView;
import mechanics.actions.GiveCardController;
import mechanics.actions.GiveCardView;
import mechanics.actions.MoveController;
import mechanics.actions.MoveView;
import mechanics.actions.ShoreupController;
import mechanics.actions.ShoreupView;
import mechanics.cardActions.CardActionController;
import mechanics.cardActions.HelicopterController;
import mechanics.cardActions.HelicopterView;
import mechanics.cardActions.PlayCardView;
import mechanics.cardActions.SandbagsView;
import mechanics.TurnView;

/**
 * Setup 
 * 
 * 	Sets up Setup classes (MVC) to carry out player and game setup
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 4.0
 * 	Changed to have static function instead of be a singleton
 * 	refactored setup into smaller functions
 * 	added setupOnly() for testing (doesn't run the setup)
 * 
 * Date created: 23/11/20 
 * Last modified: 22/12/20
 *
 */


public class Setup {						// TODO Discuss -  singleton or static function ?
	/**
	 * setupAndRun
	 * 	Setup MVC singletons
	 * 	Run the view for the setup
	 * 
	 * @param user - user input scanner
	 */
	public static void setupAndRun() {
		SetupView view = setupOnly();
		
		view.run();
	}
	
	/**
	 * setupOnly
	 * 	setup MVC controllers but don't run the setup
	 * 	Used in testing (sections done when running setup done manually)
	 * @return
	 */
	public static SetupView setupOnly(){
		Scan user = Scan.getInstance();
		SetupView view = setupMVC(user);
		
		TurnController turnController = setupTurn(user);
		setupActions(user, turnController);
		
		ObserverSetup.getInstance().attachObservers();
		return view;
	}
	
	/**
	 * setupTurn
	 * 	create view and controller instances for turns and set them up
	 * 
	 * @return turnController (with setup completed)
	 */
	private static TurnController setupTurn(Scan user) {
		TurnView turnView = TurnView.getInstance();			// create view instance
		
		// create controller instances
		TurnController turnController = TurnController.getInstance();	
		turnView.setup(user, turnController);
		turnController.setView(turnView);
		
		return turnController;
	}
	
	/**
	 * setupActions
	 * 	create action views, action controller and card controller
	 * 	Assign controllers to views and views to controllers
	 * 
	 * @param turnController
	 */
	private static void setupActions(Scan user, TurnController turnController) {
		// Create action controllers
		ClaimTreasureController claimTreasureController = ClaimTreasureController.getInstance();
		GiveCardController giveCardController = GiveCardController.getInstance();
		MoveController moveController = MoveController.getInstance();
		ShoreupController shoreupController = ShoreupController.getInstance();
		CardActionController cardController = CardActionController.getInstance();
		HelicopterController helicopterController = HelicopterController.getInstance();
		
		// Set controllers for all views (Action views and card views)
		ClaimTreasureView.getInstance().setup(user, claimTreasureController);
    	GiveCardView.getInstance().setup(user, turnController, giveCardController);
    	MoveView.getInstance().setup(user, turnController, moveController);
    	ShoreupView.getInstance().setup(user, turnController, shoreupController);
    	PlayCardView.getInstance().setup(user, turnController, cardController);
    	HelicopterView.getInstance().setup(user, helicopterController, moveController, turnController); 
    	SandbagsView.getInstance().setup(user, cardController, shoreupController, turnController); 
    	
    	// setup controllers
		cardController.setup(HelicopterView.getInstance(), SandbagsView.getInstance(), moveController);
		giveCardController.setup(turnController);
		moveController.setup(MoveView.getInstance());
		
    			
		// Other classes that need access to a controller
		FloodDeck.getInstance().setup(cardController);	 
	}
	
	/**
	 * setupMVC
	 * 	Sets up the setup MVC
	 * 
	 * @return setupView instance (with setup completed)
	 */
	private static SetupView setupMVC(Scan user) {
		PlayerSetup playerSetup = PlayerSetup.getInstance();	// create PlayerSetup instance
		GameSetup gameSetup = GameSetup.getInstance();			// create GameSetup instance
		SetupView view = SetupView.getInstance();				// create SetupView instance
		
		SetupController controller = SetupController.getInstance(playerSetup, gameSetup);	// create SetupController instance, assign it view and model instances
		view.setup(user, controller);		// assign controller to view instance
		return view;
	}
}