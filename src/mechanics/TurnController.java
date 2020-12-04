package mechanics;

import java.util.ArrayList;

import elements.board.Tile;
import elements.board.WaterLevel;
import players.Player;
import players.PlayerList;
import elements.cards.*;

/**
 * TurnController
 * 	Controller to implement turn as MVC
 * 
 * @author Catherine Waechter
 * @version 1.0
 *
 *	Date created: 03/12/20
 *	Last modified: 03/12/20
 */
public class TurnController {

	private static TurnController turnController = null;
	private Turn model;	// TODO what is the model here, It's not actually turn
	private TurnView view;
	
	/**
	 * getInstance
	 * 	get singleton instance of TurnController
	 * @param view - view associated with controller
	 * @param model - model associated with controller 
	 * @return turnController (singleton instance)
	 */
	public static TurnController getInstance(TurnView view, Turn model) {
		if(turnController == null) { 
			turnController = new TurnController(view, model);
		}
		return turnController;
	}
	
	/**
	 * TurnController Constructor
	 * 		Assigns model and view
	 * @param view
	 * @param model
	 */
	private TurnController(TurnView view, Turn model) {
		this.view = view;
		this.model = model;
	}
	
	/**
	 * getMoveCheck
	 * 	Get possible tiles the given player's pawn can move to 
	 * @param player whose pawn will move
	 * @return	list of possible tiles
	 */
	public ArrayList<Tile> getMoveCheck(Player player){
		return player.getPawn().moveCheck();
	}
	
	/**
	 * drawFloodCards
	 * 	Draw flood cards according to water level
	 */
	public void drawFloodCards() {			// TODO should this be more abstract? 
		for(int i=0; i< WaterLevel.getInstance().getNbrCards(); i++) {
			FloodDeck.getInstance().draw(); 	// cards drawn will automatically flood
		}
	} // TODO needs to return cards for view
	
	/**
	 * drawTreasureCards
	 * 	draw two treasure cards. Add to player's hand
	 * @param player
	 */
	public void drawTreasureCards(Player player) {	// TODO should this be more abstract? 
		Card card;
		for(int i=0; i<2; i++) {
			card = TreasureDeck.getInstance().draw();
			if(card != null) {		// card returned is null if it was a waters rise card
				player.getHand().addCard(card);
			}
		}
	} // TODO needs to return cards for view
	
	/**
	 * move
	 * 	Move player's pawn to the destination
	 * @param player
	 * @param destination
	 * @return
	 */
	public Tile move(Player player, Tile destination) { // TODO should this be more abstract? 
		player.getPawn().move(destination);
		return player.getPawn().getTile();
	}
}
