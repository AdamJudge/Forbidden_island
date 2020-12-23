package observers;

import mechanics.GameOver;

/**
 * FoolsLandingObserver
 * 	Handles setting up and attachment of observers
 * @author Adam Judge
 * @version 2
 * Date created: 10/12/20 
 * Last modified: 23/12/20
 *
 * Fixed name too fools landing observer...
 */

public class FoolsLandingObserver extends Observer {
	public FoolsLandingObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("Fools Landing has Sunk beneath the waves! You are trapped and ill-fated!");
		GameOver.endGame(false);
	}
}
