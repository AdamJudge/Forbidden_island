package elements.pawns;

import java.util.Set;
import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;

/**
 * Pilot class
 * 	Represents the pilot pawn (Blue)
 * 
 * @author Catherine Waechter
 * @version 2.4
 * 	added toInitialTile method
 * 	no longer check flight for initial tile placement
 * 
 *  Date created: 26/10/20
 *  Last modified: 03/12/20
 */
public class Pilot extends Pawn {

	private boolean hasFlown; 	
	
	/**
	 * resetHasFlown
	 * 	Set hasFlown to false (must be done at end of turn)
	 */
	public void resetHasFlown() {
		hasFlown = false;
	}
	
	/**
	 * toInitialTile
	 * 	move pawn to starting tile (Fools' landing)
	 */
	public void toInitialTile() {
		for(Tile tile : Board.getInstance().getAllTiles()) {
			if(tile.getName() == TileNames.FOOLS_LANDING) {
				move(tile);
				break;
			}
		}
	}
	
	/**
	 * getHasFlown
	 * @return HasFlown variable
	 */
	public boolean getHasFlown() {
		return hasFlown;
	}
	
	/**
	 * moveCheck
	 * 	splits tiles that can be flown to vs walked to 
	 */
	@Override
	public ArrayList<Tile> moveCheck(){
		Set<Tile> remainingTiles = Board.getInstance().getRemainingTiles();
		ArrayList<Tile> validTiles = new ArrayList<Tile>();		
		
		validTiles = super.moveCheck();
		int startPrint = validTiles.size();
		
		if(!hasFlown){
			for(Tile tile : remainingTiles) {
				if(!validTiles.contains(tile)) {
					validTiles.add(tile);
				}
			}
		}
		System.out.println("Pilot can fly to these tiles (will not be able to fly again this turn)");
		checkPrint(validTiles, startPrint);
		
		return validTiles;
	}
	
	/**
	 * move
	 * 	Check tiles pilot can walk to, if destination needs to be flown to, hasFlown set to true
	 * @param destination tile
	 */
	public void move(Tile destination) {
		// initial placement will not be flight
		if(this.currentTile != null) {
			ArrayList<Tile> walkingTiles = super.moveCheck();
			if(!walkingTiles.contains(destination)) {
				hasFlown = true;
			}
		}
		currentTile = destination;
	}
	
	/**
	 * Pilot constructor
	 */
	public Pilot() {
		currentTile = null;
		hasFlown = false;
	}
	
}
