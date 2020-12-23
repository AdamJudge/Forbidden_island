package elements.cards;

import elements.board.Board;
import elements.board.Tile;
import mechanics.cardActions.CardActionController;

import java.util.Stack;


/**
 * FloodDeck class
 * 
 * Represent the deck of flood cards
 * 
 * @author Catherine Waechter
 * @version 2.3
 * 	added teadDown
 * 
 * Date Created: 26/10/20
 * Last Modified: 16/12/20
 *
 */
public class FloodDeck extends Deck{
	
	private static FloodDeck floodDeck = null;  	// singleton instance
	private CardActionController cardController;
	
	
	/**
	 * draw
	 * 	draws a floodcard and carries out required flooding. Card is discarded in floodTile method
	 * return card - Note card has been either used or discarded, does not need to be added back to deck
	 */
	@Override
	public Card draw() {
		FloodCard card = (FloodCard)super.draw();
		cardController.floodTile(card);	
		
		if (cards.isEmpty()) {
			FloodDiscard.getInstance().toDeck();
		}
		return card;
	}
	
	/**
	 * FloodDeck constructor
	 * 	Adds one card per tile name, then shuffles the deck
	 */
	private FloodDeck() {
		cards = new Stack<Card>();
		
		for(Tile tile : Board.getInstance().getAllTiles()) {
			cards.push(new FloodCard(tile));
		}
		
		shuffleDeck();
	}
	
	public void setup(CardActionController cardController) {
		this.cardController = cardController;
	}
	
	/**
	 * getInstance
	 * returns FloodDeck instance
	 * @return
	 */
	public static FloodDeck getInstance() {	
		if(floodDeck == null) {
			floodDeck = new FloodDeck();
		}
		return floodDeck;
	}
	
	/**
	 * tearDown for testing
	 */
	public void tearDown() {
		floodDeck = null;
	}

}
