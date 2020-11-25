package mechanics.cardActions;

import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.cards.Card;
import elements.cards.TreasureDiscard;

/**
 * Sandbags
 * 	carries out mechanics of playing a sandbags card and discards the card. 
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date created: 25/11/20
 * Last modified:25/11/20
 *
 */
public class Sandbags {
	
	/**
	 * play 
	 * 	asks for user input and shores up the requested tile
	 */
	public static void play(Card card) {
		
		ArrayList<Tile> floodedTiles = getFloodedTiles();
		// TODO : user input for tile to shore up
		int input = 1; // change to user input val
		
		floodedTiles.get(input).shoreup();
		
		TreasureDiscard.getInstance().addCard(card);
		
	}
	
	/**
	 * getFloodedTiles
	 * 	returns list of tiles that can be shored up
	 * @return floodedTiles
	 */
	private static ArrayList<Tile> getFloodedTiles(){
		ArrayList<Tile> floodedTiles = new ArrayList<Tile>();
		
		for(Tile tile : Board.getInstance().getRemainingTiles()) {
			if(tile.getStatus() == TileStatus.FLOODED) {
				floodedTiles.add(tile);
				System.out.println("[" + floodedTiles.size() + "] " + tile);
			}
		}
		
		return floodedTiles;
	}

}
