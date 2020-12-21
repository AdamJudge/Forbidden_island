package elements.cards;

import elements.board.Board;
import elements.board.Tile;
import mechanics.cardActions.FloodTileView;
import mechanics.actions.ActionController;

import java.io.IOException;
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
	private ActionController controller;
	
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
	
	public void setController(ActionController controller) {
		this.controller = controller; 
	}
	
	/**
	 * draw
	 * 	draws a floodcard and carries out required flooding. Card is discarded in floodTile method
	 * return card - Note card has been either used or discarded, does not need to be added back to deck
	 */
	@Override
	public Card draw() {
		FloodCard card = (FloodCard)super.draw();
		FloodTileView.floodTile(card, controller);
		
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
	
	/**
	 * tearDown for testing
	 */
	public void tearDown() {
		floodDeck = null;
	}

}
