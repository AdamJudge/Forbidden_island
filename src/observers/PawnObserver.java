package observers;

import elements.pawns.Pawn;

public class PawnObserver extends Observer {
	public PawnObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println(((Pawn)subject).toString() + " has drowned!");
		//TODO gameover screen
		System.exit(0);
	}
}
