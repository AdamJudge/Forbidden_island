package observers;

public class ObserverGameOver extends Observer {

	public ObserverGameOver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("The game is dang over!");
	}
}
