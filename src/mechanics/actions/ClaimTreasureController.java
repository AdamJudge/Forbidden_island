package mechanics.actions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.board.Board;
import elements.cards.Card;
import elements.cards.TreasureCard;
import elements.cards.TreasureCardTypes;
import elements.treasures.Treasure;
import elements.treasures.TreasureNames;
import players.Player;

/**
 * ClaimTreasureController (Singleton, MVC)
 * 
 * 	Controller to handle claiming treasures
 * 	
 * @author Catherine Waechter
 * @version 1.0 
 * 	Methods were originally in ActionController
 * 
 * Date Created: 23/12/20
 * Last Modified: 23/12/20
 *
 */
public class ClaimTreasureController {

	private static ClaimTreasureController claimTreasureController = null;

	/**
	 * getClaimTreasureCheck
	 * 	Returns the treasure the given player is able to capture
	 * @param currentPlayer
	 * @return Treasure the player can capture, or null if none
	 */
	public Treasure getClaimTreasureCheck(Player currentPlayer) {
		int treasureCount = 0;
		Treasure potentialTreasure = null;
		
		// get treasure associated with tile the player's pawn is on
		potentialTreasure = currentPlayer.getPawn().getTile().getTreasure();
		
		// if no treasure associated, no treasure can be collected
		if(potentialTreasure == null) {
			return null;
		}
		
		// if player's pawn is on a tile with a treasure, count cards of that treasure type
		for(Card card : currentPlayer.getHand().getCards()) {
			if(((TreasureCard)card).getCardType() != TreasureCardTypes.TREASURE) {
				continue;
			}
			
			if(((TreasureCard)card).getTreasureType().getName().equals(potentialTreasure.getName())) {
				treasureCount++;
			}
		}
		if(treasureCount >= 4) {
			return potentialTreasure;
		}

		else return null;
	}	
		
	/**
	 * claimTreasure
	 * 	capture the given treasure and discard 4 cards of that treasure type
	 * 
	 * @param player - player capturing the treasure
	 * @param treasure - treasure to be captured
	 */
	public void claimTreasure(Player player, Treasure treasure) {
		treasure.captureTreasure();
		int discardedCount = 0;
		for(Card card : player.getHand().getCards()) {
			if(discardedCount == 4) {
				break;
			}
			if(((TreasureCard)card).getTreasureType() == treasure) {
				player.getHand().discardCard(card);
				discardedCount++;
			}
		}
	}
			
	/**
	 * getUnclaimedTreasures
	 * 	returns a set of treasures which have not been claimed yet
	 * @return
	 */
	public Set<Treasure> getUnclaimedTreasures(){
		Set<Treasure> unclaimedTreasures = new HashSet<Treasure>();
		for(Map.Entry<TreasureNames, Treasure> entry : Board.getInstance().getTreasures().entrySet()) {
			if(!entry.getValue().isCaptured()) {
				unclaimedTreasures.add(entry.getValue());
			}
		}
		return unclaimedTreasures;
	}

	/**
	 * getInstance
	 * 	get singleton instance of ClaimTreasureController
	 * @return claimTreasureController (singleton instance)
	 */
	public static ClaimTreasureController getInstance() {
		if(claimTreasureController== null) { 
			claimTreasureController= new ClaimTreasureController();
		}
		return claimTreasureController;
	}
}
