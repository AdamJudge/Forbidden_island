package observers;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	private List<Observer> observers = new ArrayList<Observer>();

	//Class for removing observers, useful for some testing purposes
	public void removeObservers() {
		observers = null;
		observers = new ArrayList<Observer>();
	}
	
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	public void notifyAllObservers() {
		for (Observer observer:observers) {
			observer.update();
		}
	}
}
