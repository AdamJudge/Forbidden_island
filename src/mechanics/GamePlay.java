package mechanics;

import java.util.ArrayList;

import elements.board.Board;
import observers.Subject;
import players.Player;
import players.PlayerList;

/**
 * GamePlay (Singleton)
 * 
 * 	Handles main gameplay (post setup)
 * 	canLeave and gameOver modified by observers to trigger winning or losing
 * 
 * @author Adam Judge
 * @version 2.2
 * 	Turn loop uses TurnView directly
 * 
 * Date created: 09/12/20
 * Last modified: 22/12/20
 *
 */
public class GamePlay extends Subject{
	private static GamePlay gp=null;	// singleton instance of GamPlay
	private boolean canLeave;			// true if players can leave the island (win condition)
	private PlayerList playerList;		// List of players in the game
	private boolean gameOver;			// true if the game is over (lose condition)
	
	/**
	 * playGame
	 * 	Carries out turns as long as gameOver is not true
	 * @param user
	 */
	public void playGame() {
		ArrayList<Player> players = playerList.getPlayers();
		while (!gameOver) {
			for (int i = 0; i< players.size(); i++) {
				TurnView.getInstance().run(players.get(i));	
				if (this.gameOver) {
					break;
				}
			}
		}
	}
	
	/**
	 * tryLeave
	 * 	Called when players want to try to leave the island
	 * 	Notifies observers, which will change canLeave if conditions are met
	 */
	public void tryLeave() {
		notifyAllObservers();
	}
	
	/**
	 * setLeave
	 * 	Observer will update this when updated in tryLeave
	 * @param bool - true if players can leave
	 */
	public void setLeave(boolean bool) {
		canLeave=bool;
	}
	
	/**
	 * canLeave
	 * @return canLeave 
	 */
	public boolean canLeave() {
		return canLeave;
	}
	
	/**
	 * setGameOver
	 * 	Set by observer when lose conditions are met
	 * @param bool - true if players have lost
	 */
	public void setGameOver(boolean bool) {
		gameOver=bool;
	}
	
	/**
	 * getGameOver 
	 * @return gameOver - true if players have lost
	 */
	public boolean getGameOver() {
		return gameOver;
	}
	
	
	/**
	 * getInstance
	 * @return singleton instance of GamePlay
	 */
	public static GamePlay getInstance() {
		if(gp==null) {
			gp=new GamePlay();
		}
		return gp;
	}
	
	/**
	 * GamePlay Constructor
	 * 	set game ending conditions to false and get Player list
	 */
	private GamePlay() {
		this.canLeave=false;
		this.gameOver=false;
		this.playerList=PlayerList.getInstance();
	}
	
	/**
	 * tearDown
	 * 	used for testing
	 */
	public void tearDown() {
		gp=null;
	}
}
