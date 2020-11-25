package mechanics.cardActions;

import elements.cards.FloodCard;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.cards.FloodDiscard;

/**
 * FloodTile
 * 	Carries out mechanics that happen when a flood card is drawn
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified:25/11/20
 *
 */
public class FloodTile {
	
	/**
	 * floodTile
	 * 	if tile is normal, flood the tile and add card to discard pile
	 * 	if tile is flooded, remove tile, and remove card (ie don't add to discard)
	 * @param card
	 */
	public static void floodTile(FloodCard card) {
		
		Tile tile = card.getTile();
		if(tile.getStatus() == TileStatus.NORMAL) {
			tile.flood();
			FloodDiscard.getInstance().addCard(card);
		}
		
		else if(tile.getStatus() == TileStatus.FLOODED) {
			tile.remove();
		}
		
	}

}
