package observers;

public class ObserverGameOver extends Observer {

	public ObserverGameOver(Subject subject) {
		this.subject=subject;
	}
	
	@Override
	public void update() {
		System.out.println("The game is dang over!");
	}
}
