package observers;

import elements.pawns.Pawn;
import mechanics.GameOver;

public class PawnObserver extends Observer {
	public PawnObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println(((Pawn)subject).toString() + " has drowned!");
		//TODO Adam gameover screen
		new GameOver(false);
	}
}
