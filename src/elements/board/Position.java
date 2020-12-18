package elements.board;


/**
 * Position Class
 * x , y position . Used for tiles
 * @author Catherine Waechter
 * @version 1.0
 * Date created : 29/10/20
 * Date modified: 29/10/20
 */
public class Position {

	private int x;	// x position
	private int y;	// y position
	
	// TODO Cat make a private class for Tiles ? 
	
	/**
	 * get the x position
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * get the y position
	 * @return y
	 */	
	public int getY() {
		return y;
	}
	
	/**
	 * Check if a position is valid for a tile
	 * @param x
	 * @param y
	 * @return true if valid position, false otherwise.
	 */
	static public boolean validPosition(int x, int y) {
		if (x == 0 || x == 5) {
			if (y == 0 || y == 1 || y == 4 || y == 5) {
				return false;
			}
		}
		else if(x == 1 || x == 4) {
			if(y == 0 || y == 5) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Position constructor
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
