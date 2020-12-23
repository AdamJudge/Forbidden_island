package mechanics.cardActions;

import java.util.ArrayList;

import elements.board.Board;
import elements.board.Tile;
import elements.board.TileStatus;
import elements.cards.Card;
import elements.cards.FloodCard;
import elements.cards.FloodDiscard;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import mechanics.actions.MoveController;
import players.Player;
import players.PlayerList;

/**
 * CardActionController (Singleton, MVC)
 * 
 * Controller to handle card mechanics (playing cards, and flood cards being drawn)
 * 
 * @author Catherine Waechter
 * @version 1.2
 * 	Most methods used to be in other controllers or classes
 * 	No longer contains helicopter methods
 * 
 * Date Created: 21/12/20
 * Last modified: 23/12/20
 *
 */
public class CardActionController {
	
	private static CardActionController cardController = null;
	private MoveController moveController;
	private HelicopterView hView;
	private SandbagsView sView;
	private PlayCardView playCardView;
	
	/**
	 * playCard
	 * 	Play a player's card (sandbags or helicopter)
	 * 
	 * @param card
	 * @param player
	 */
	public void playCard(Card card, Player player) {
		if (((TreasureCard)card).getCardType() == TreasureCardTypes.HELICOPTER) {
			hView.play(card, player);
		}
		else if(((TreasureCard)card).getCardType() == TreasureCardTypes.SANDBAGS) {
			sView.play(player, card);
		}
	}
	
	/**
	 * floodTile
	 * 
	 * 	Called by flood cards
	 * 
	 * 	if tile is normal, flood the tile and add card to discard pile
	 * 	if tile is flooded, remove tile, and remove card (ie don't add to discard)
	 * @param card
	 */
	public void floodTile(FloodCard card) {
		
		Tile tile = card.getTile();
		
		if(tile.getStatus() == TileStatus.NORMAL) {
			tile.flood();
			FloodDiscard.getInstance().addCard(card);
		}
		else if(tile.getStatus() == TileStatus.FLOODED) {
			
			
			tile.remove();
			ArrayList<Player> playersToSwim = new ArrayList<Player>();
			for (Player player : PlayerList.getInstance().getPlayers()) {
				if(player.getPawn().getTile() == tile) {
					playersToSwim.add(player);
				}
			}
			if(playersToSwim.size() != 0) {
				moveController.doSwim(playersToSwim);
			}
		}
	}
	
	/**
	 * playable
	 * @param card - single card
	 * @return true if the card is playable (sandbags or helicopter)
	 */
	public boolean playable(Card card) { 
		
		if(isHelicopter(card) || isSandbags(card)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * playable
	 * @param cards - list of cards
	 * @return true if any card in the list is playable
	 */
	public boolean playable(ArrayList<Card> cards) { 
		for(Card card : cards) {
			if(playable(card)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean anyPlayable() {
		for(Player player : PlayerList.getInstance().getPlayers()) {
			for(Card card : player.getHand().getCards()) {
				if(playable(card)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * doPlayCard
	 * prompts the playCardView
	 * @param player
	 */
	public void doPlayCard(Player player) {
		playCardView.doAction(player);
	}
	
	/**
	 * isHelicopter
	 * @param card
	 * @return true if the card is a helicopter
	 */
	public boolean isHelicopter(Card card) {
		return (((TreasureCard)card).getCardType() == TreasureCardTypes.HELICOPTER );
	}
	
	/**
	 * isSandbags
	 * @param card
	 * @return true if the card is a sandbag
	 */
	public boolean isSandbags(Card card) {
		return ((TreasureCard)card).getCardType() == TreasureCardTypes.SANDBAGS;
	}
	
	/**
	 * getFloodedTiles
	 * 	returns list of tiles that can be shored up	 (For Sandbags View)
	 * @return floodedTiles
	 */
	public ArrayList<Tile> getFloodedTiles(){
		ArrayList<Tile> floodedTiles = new ArrayList<Tile>();
		
		for(Tile tile : Board.getInstance().getRemainingTiles()) {
			if(tile.getStatus() == TileStatus.FLOODED) {
				floodedTiles.add(tile);
			}
		}
		
		return floodedTiles;
	}
	

	/**
	 * setup
	 * assign views
	 * @param hView
	 * @param sView
	 * @param playCardView
	 * @param moveController
	 */
	public void setup(HelicopterView hView, SandbagsView sView, PlayCardView playCardView, MoveController moveController) {
		this.moveController = moveController;
		this.hView = hView;
		this.sView = sView;
		this.playCardView = playCardView;
	}
	
	/**
	 * getInstance
	 * 	get singleton instance of CardActionController
	 * @return cardController (singleton instance)
	 */
	public static CardActionController getInstance() {
		if(cardController == null) { 
			cardController = new CardActionController();
		}
		return cardController;
	}

	public void tearDown() {
		cardController=null;
	}
	
}
