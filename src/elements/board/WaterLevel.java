package elements.board;

import observers.Subject;

/**
 * WaterLevel Class
 *  Represents the water level marker element
 *  
 * @author Catherine Waechter, Adam Judge
 * @version 1.1
 *  Made singleton
 * Date Created : 26/10/20
 * Last Modified: 29/10/20
 *
 */
public class WaterLevel extends Subject{
	public static WaterLevel waterLevel=null;
	private Difficulty difficulty;
	private int level;		// water level
	private int nbrCards;	// nbr of flood cards to be drawn at this level
	
	/**
	 * getInstance
	 * 	@return singleton instance of the water level
	 */
	public static WaterLevel getInstance() {
		if (waterLevel==null) {
			waterLevel=new WaterLevel();
		}
		return waterLevel;
	}
	
	/**
	 * setDifficulty
	 * 	sets level and number of cards for required difficulty
	 * @param difficulty
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty=difficulty;
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
			notifyAllObservers();
		}
	}
	
	/**
	 * getNbrCards : get number of flood cards to be drawn at current level
	 * @return
	 */
	public int getNbrCards() {
		return nbrCards;
	}
	
	/**
	 * getDifficulty
	 * @return difficulty level (from start of game)
	 */
	public String getDifficulty() {
		return difficulty.toString();
	}
	
	/**
	 * getWaterLevel
	 * @return water level (int)
	 */
	public int getWaterLevel() {
		return level;
	}
}
