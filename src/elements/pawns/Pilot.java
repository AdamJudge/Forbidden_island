package elements.pawns;

import java.util.Set;

import elements.board.Tile;
import elements.board.TileStatus;

public class Pilot extends Pawn {

	// field : has_flown (?)
	private boolean has_flown;
	
	public boolean has_flown() {
		return has_flown;
	}
	
	@Override
	public Set<Tile> moveCheck(Set<Tile> allTiles){
		Set<Tile> validTiles = super.moveCheck(allTiles);
		if(has_flown) {
			for(Tile tile : allTiles) {
				if(tile.getStatus() != TileStatus.REMOVED) {
					validTiles.add(tile);
				}
			}
		}
		return validTiles;
	}
	
	
	// override check_move
	// override move
	
	public Pilot() {
		position.setPosition(0, 0);
		has_flown = false;
	}
	
}
