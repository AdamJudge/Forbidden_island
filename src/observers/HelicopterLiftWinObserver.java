package observers;

public class HelicopterLiftWinObserver extends Observer {
	public HelicopterLiftWinObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("You have escaped Forbidden Island!");
		System.exit(0);
	}
}
