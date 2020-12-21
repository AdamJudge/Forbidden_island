package mechanics.cardActions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.cards.Card;
import elements.cards.TreasureDiscard;
import mechanics.ViewInputTools;

/**
 * Sandbags
 * 	carries out mechanics of playing a sandbags card and discards the card. 
 * 
 * @author Catherine Waechter, Adam Judge
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
	public static void play(Card card, Scanner user) {
		
		ArrayList<Tile> floodedTiles = getFloodedTiles();
		int iter=1;
		System.out.println("Which tile do you want to shore up?");
		for (Tile t:floodedTiles) {
			System.out.println("["+iter+"]: " + t.getName());
			iter+=1;
		}
		int input = ViewInputTools.numbers(user, 1, floodedTiles.size());
		
		//input minus one as start from 0
		floodedTiles.get(input-1).shoreup();
		
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
