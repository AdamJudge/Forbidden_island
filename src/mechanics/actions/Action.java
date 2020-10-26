package mechanics.actions;

import java.util.ArrayList;

/* Three abstract classes to be implemented by actions.
	
	Print all actions for user selection m/s/g/t
	Check conditions/possible
	Check conditions, i.e player location, prints list of actions to terminal (m/s/g/t) TO BE DONE IN THIS CLASS
	do_action() //Takes in input and does action if possible TO BE DONE IN SUB CLASS

*/
public abstract class Action{
	//protected ArrayList<String> actionList = new ArrayList<String>();
	// Array list of actions
	// Selected element in array list
	
	public void getActions() {
		// Will take an array list from something
	}
	
	public int checkAction() {
		return 0;
		// Print array list, get user input, select action
	}
	
	public abstract void doAction();
}
