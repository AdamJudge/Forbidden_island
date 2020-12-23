package observers;

import elements.pawns.Pawn;
import mechanics.GameOver;

public class PawnObserver extends Observer {
	public PawnObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}
	
	// Triggers game loss and prints out the pawn which has drowned.
	@Override
	public void update() {
		System.out.println(((Pawn)subject).toString() + " has drowned!");
		GameOver.endGame(false);
	}
}
