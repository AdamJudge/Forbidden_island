package observers;

import mechanics.GameOver;

public class FoolsIslandObserver extends Observer {
	public FoolsIslandObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("Fools Land has Sunk beneath the waves! You are trapped and ill-fated!");
		new GameOver(false);
	}
}