package elements.board;

/**
 * WaterLevel Class
 *  Represents the water level marker element
 *  
 * @author Catherine Waechter, Adam Judge
 * @version 1.0
 *  Made singleton
 * Date Created : 26/10/20
 * Last Modified: 29/10/20
 *
 */
public class WaterLevel {
	public static WaterLevel waterLevel=null;
	private int level;		// water level
	private int nbrCards;	// nbr of flood cards to be drawn at this level
	
	/**
	 * WaterLevel Constructor
	 * 	Set initial water level and nbr of cards according to difficulty level
	 * @param difficulty
	 */
	public static WaterLevel getInstance() {
		if (waterLevel==null) {
			waterLevel=new WaterLevel();
		}
		return waterLevel;
	}
	
	private WaterLevel() {

	}
	
	public void setDifficulty(Difficulty difficulty) {
		if (difficulty == Difficulty.NOVICE) {
			level = 1;
			nbrCards = 2;
		}
		else if (difficulty == Difficulty.NORMAL) {
			level = 2;
			nbrCards = 2;
		}
		else if (difficulty == Difficulty.ELITE) {
			level = 3;
			nbrCards = 3;
		}
		else if (difficulty == Difficulty.LEGENDARY) {
			level = 4;
			nbrCards = 3;
		}
	}
	/**
	 * raise_level : raises water level by 1
	 * adjusts number of cards accordingly
	 */
	public void raise_level() {
		level += 1;
		if ( level == 3 || level == 6 || level == 8) { 	// levels that cause an increase in nbrCards
			nbrCards += 1;
		}
		if (level == 10) {	// losing level
//			Game.end();		// game over
		}
	}
	
	/**
	 * getLevel : get water level
	 * @return
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * getNbrCards : get number of flood cards to be drawn at current level
	 * @return
	 */
	public int getNbrCards() {
		return nbrCards;
	}
	
}
