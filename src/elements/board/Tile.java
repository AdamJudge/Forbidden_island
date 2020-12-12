	package elements.board;

import elements.treasures.*;
import observers.Subject;

import java.util.Map;


/**
 * Tile class
 *  represents a tile
 *  
 * @author Catherine Waechter
 * @version 2.0
 * 	Associated with Treasure, not treasureName
 * 
 * Date Created : 26/10/20
 * Last Modified: 03/12/20
 */
public class Tile extends Subject{
	
	private Position position;		// position of the tile (x,y)
	private TileNames name;			// location name on the tile
	private TileStatus status;		// tile status
	private Treasure treasure;	// treasure associated with the tile
	
	/**
	 * Tile constructor
	 * @param name
	 * @param position
	 */
	public Tile(TileNames name, Position position, Map<TreasureNames, Treasure> treasures) {
		this.name = name;			// location name on the tile
		this.position = position;	// tile position, as determined by the board class
		status = TileStatus.NORMAL;	// all tiles are normal at the start
		
		// set associated treasure for relevant tiles
		if(name == TileNames.TIDAL_PALACE || name == TileNames.CORAL_PALACE) {
			treasure = treasures.get(TreasureNames.OCEAN_CHALICE);
		}
		else if(name == TileNames.CAVE_OF_EMBERS || name == TileNames.CAVE_OF_SHADOWS) {
			treasure = treasures.get(TreasureNames.CRYSTAL_OF_FIRE);
		}
		else if(name == TileNames.TEMPLE_OF_THE_MOON || name == TileNames.TEMPLE_OF_THE_SUN) {
			treasure = treasures.get(TreasureNames.EARTH_STONE);
		}
		else if(name == TileNames.HOWLING_GARDEN || name == TileNames.WHISPERING_GARDEN) {
			treasure = treasures.get(TreasureNames.STATUE_OF_THE_WIND);
		}
		else {
			treasure = null;
		}
	}
	
	public int compareTo(Tile otherTile) {
		if(this.getY() > otherTile.getY()) {
			return 1;
		}
		else if((this.getY() == otherTile.getY()) && this.getX() > otherTile.getX()) {
			return 1;
		}
		else if(this.getX() == otherTile.getX() && this.getY() == otherTile.getY()) {
			return 0;
		}
		else return -1;
	}
	
	/**
	 * flood : flood a tile
	 */
	public void flood() {
		status = TileStatus.FLOODED;
	}
	
	/**
	 * shoreup : shore-up a tile 
	 */
	public void shoreup() {
		status = TileStatus.NORMAL;
	}
	
	/**
	 * remove : remove a tile
	 */
	public void remove() {
		status = TileStatus.REMOVED;
		if (name.equals(TileNames.FOOLS_LANDING)) {
			notifyAllObservers();
		}
		
	}
	
	/**
	 * getName : get tile name
	 * @return name
	 */
	public TileNames getName() {
		return name;
	}
	
	/**
	 * getTreasure : get treasure type associated with the tile
	 * @return treasure
	 */
	public Treasure getTreasure() {
		return treasure;
	}
	
	/**
	 * getStatus : get tile status
	 * @return status
	 */
	public TileStatus getStatus() {
		return status;
	}
	
	/**
	 * getX : get x position of the tile
	 * @return x
	 */
	public int getX() {
		return position.getX();
	}
	
	/**
	 * getY : get y position of the tile
	 * @return y
	 */
	public int getY() {
		return position.getY();
	}
	
	/**
	 * toString
	 * returns name of the tile
	 */
	@Override
	public String toString() {
		return name.name();
	}
	
}
