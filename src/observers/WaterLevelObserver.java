package observers;
/**
 * WaterLevelObserver
 * 	Observer for the waterlevel (WaterLevel.getInstance)
 * @author Adam Judge
 * @version 1
 * Date created: 10/12/20 
 * Last modified: 10/12/20
 *
 */
import mechanics.GameOver;

public class WaterLevelObserver extends Observer {
	public WaterLevelObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("The Water Level has risen too high!");
		//Game loss
		GameOver.endGame(false);
	}
}
