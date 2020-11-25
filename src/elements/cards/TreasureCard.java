package elements.cards;

import elements.treasures.TreasureNames;

/**
 * TreasureCard class
 * 
 * Represents a treasure card
 * 
 * @author Catherine Waechter
 * @version 1.1
 * 	Added toString
 * Date Created: 26/10/20
 * Last Modified: 25/11/20
 *
 */
public class TreasureCard extends Card{
	
	private TreasureCardTypes cardType;		// type of the card (treasure, sandbag, helicopter lift, waters rise)
	private TreasureNames treasureType;		// treasure associated (none if not a treasure card)
	
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
	public TreasureNames getTreasureType() {
		return treasureType;
	}
	
	/**
	 * TreasureCard constructor 
	 * @param cardType
	 * @param treasureType
	 */
	protected TreasureCard(TreasureCardTypes cardType, TreasureNames treasureType) {
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
			return treasureType.name();
		}
		else return cardType.name();
	}

}
