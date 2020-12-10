package observers;

public class TreasureObservers extends Observer {
	public TreasureObservers(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	@Override
	public void update() {
		System.out.println("Argh the booty be sunketh 'ere fore it be retrieved!!");
		System.exit(0);
	}
}
