package observers;

import mechanics.GameOver;

public class WaterLevelObserver extends Observer {
	public WaterLevelObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("The Water Level has risen too high!");
		GameOver.endGame(false);
	}
}
