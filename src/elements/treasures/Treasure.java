package elements.treasures;

/**
 * Treasure Class
 * Represents a treasure piece
 * 
 * @author Catherine Waechter
 * @version 1.0
 * Date created : 29/10/20
 * Last modified: 29/10/20
 */
public class Treasure {

	private TreasureNames name;
	private boolean captured; 	// true if captured, false if not
	
	/**
	 * getName : return name of the treasure
	 * @return name
	 */
	public TreasureNames getName() {
		return name;
	}
	
	/**
	 * isCaptured : return whether the treasure has been captured
	 * @return captured
	 */
	public boolean isCaptured() {
		return captured;
	}
	
	/**
	 * captureTreasure
	 * sets a treasure to be captured
	 */
	public void captureTreasure() {
		captured = true;
	}
	
	/**
	 * toString
	 * returns string representation of the treasure (ie its name)
	 */
	public String toString() {
		return name.name();
	}
	
	/**
	 * Treasure Constructor
	 * @param name : name of the treasure
	 */
	public Treasure(TreasureNames name) {
		this.name = name;
		captured = false;
	}
}
