package mechanics.cardActions;

import elements.cards.FloodCard;
import elements.board.Board;
import elements.board.Tile;
import elements.board.TileNames;
import elements.board.TileStatus;
import elements.cards.FloodDiscard;
import mechanics.TurnController;
import observers.Subject;
import players.Player;
import players.PlayerList;

import java.util.ArrayList;
import java.io.IOException;

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
public class FloodTile{
	
	/**
	 * floodTile
	 * 	if tile is normal, flood the tile and add card to discard pile
	 * 	if tile is flooded, remove tile, and remove card (ie don't add to discard)
	 * @param card
	 */
	public static void floodTile(FloodCard card, TurnController controller) throws IOException {
		
		Tile tile = card.getTile();
		
		
		if(tile.getStatus() == TileStatus.NORMAL) {
			tile.flood();
			FloodDiscard.getInstance().addCard(card);
		}
		
		
		
		else if(tile.getStatus() == TileStatus.FLOODED) {
			
			if(tile.getTreasure() != null) {
				for(Tile otherTile : Board.getInstance().getRemainingTiles()) {
					if(tile.getTreasure() == otherTile.getTreasure()) {
						// All good!
					}
				}
				// TODO End Game BAD
			}
			
			if(tile.getName() == TileNames.FOOLS_LANDING) {
				//notifyAllObservers();
			}
			
			tile.remove();
			ArrayList<Player> playersToSwim = new ArrayList<Player>();
			for (Player player : PlayerList.getInstance().getPlayers()) {
				if(player.getPawn().getTile() == tile) {
					playersToSwim.add(player);
				}
			}
			if(playersToSwim.size() != 0) {
				controller.doSwim(playersToSwim);
			}
		}
	}

}
