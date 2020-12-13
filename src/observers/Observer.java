package observers;
/**
 * Class Name: observers
 *
 * Base observer class
 * 
 * Author: @author adamj
 * Version: @version 
 * Creation Date: 13/12/20
 */
public abstract class Observer {
	protected Subject subject;
	public abstract void update();
}
