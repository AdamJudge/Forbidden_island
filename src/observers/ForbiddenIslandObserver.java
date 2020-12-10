package observers;

public class ForbiddenIslandObserver extends Observer {
	public ForbiddenIslandObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("Forbidden Island has Sunk beneath the waves!");
		System.exit(0);
	}
}
