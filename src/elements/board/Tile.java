package elements.board;

import elements.treasures.TreasureNames;


/**
 * Tile class
 *  represents a tile
 *  
 * @author Catherine Waechter
 * @version 1.1
 * 	added toString
 * 
 * Date Created : 26/10/20
 * Last Modified: 23/11/20
 */
public class Tile {
	
	private Position position;		// position of the tile (x,y)
	private TileNames name;			// location name on the tile
	private TileStatus status;		// tile status
	private TreasureNames treasure;	// treasure associated with the tile
	
	/**
	 * Tile constructor
	 * @param name
	 * @param position
	 */
	public Tile(TileNames name, Position position) {
		this.name = name;			// location name on the tile
		this.position = position;	// tile position, as determined by the board class
		status = TileStatus.NORMAL;	// all tiles are normal at the start
		
		// set associated treasure for relevant tiles
		if(name == TileNames.TIDAL_PALACE || name == TileNames.CORAL_PALACE) {
			treasure = TreasureNames.OCEAN_CHALICE;
		}
		else if(name == TileNames.CAVE_OF_EMBERS || name == TileNames.CAVE_OF_SHADOWS) {
			treasure = TreasureNames.CRYSTAL_OF_FIRE;
		}
		else if(name == TileNames.TEMPLE_OF_THE_MOON || name == TileNames.TEMPLE_OF_THE_SUN) {
			treasure = TreasureNames.EARTH_STONE;
		}
		else if(name == TileNames.HOWLING_GARDEN || name == TileNames.WHISPERING_GARDEN) {
			treasure = TreasureNames.STATUE_OF_THE_WIND;
		}
		else {
			treasure = TreasureNames.NONE;
		}
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
	public TreasureNames getTreasure() {
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
