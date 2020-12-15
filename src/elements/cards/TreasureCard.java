package elements.cards;

import elements.treasures.Treasure;

/**
 * TreasureCard class
 * 
 * Represents a treasure card
 * 
 * @author Catherine Waechter
 * @version 2.0
 * 	Field representing the treasureType is now a Treasure, not a TreasureName
 * Date Created: 26/10/20
 * Last Modified: 08/12/20
 *
 */
public class TreasureCard extends Card{
	
	private TreasureCardTypes cardType;		// type of the card (treasure, sandbag, helicopter lift, waters rise)
	private Treasure treasureType;		// treasure associated (none if not a treasure card)
	
	/**
	 * getCardType
	 * @return type of the card
	 */
	public TreasureCardTypes getCardType() {
		return cardType;
	}

	/**
	 * getTreasureType
	 * @return treasure associated with the card
	 */
	public Treasure getTreasureType() {
		return treasureType;
	}
	
	/**
	 * TreasureCard constructor 
	 * @param cardType
	 * @param treasureType
	 */
	public TreasureCard(TreasureCardTypes cardType, Treasure treasureType) {
		this.cardType = cardType;
		this.treasureType = treasureType;
	}
	
	/**
	 * toString
	 * 	returns the type of the card, or name of treasure if it's a treasure card
	 */
	@Override
	public String toString() {
		if(cardType == TreasureCardTypes.TREASURE) {
			return treasureType.toString();
		}
		else return cardType.name();
	}

}
