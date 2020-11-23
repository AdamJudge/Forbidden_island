package elements.cards;

import elements.treasures.TreasureNames;

/**
 * TreasureCard class
 * 
 * Represents a treasure card
 * 
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created: 26/10/20
 * Last Modified: 23/11/20
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

}
