package mechanics;

import java.util.ArrayList;
import java.util.Scanner;

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
 * @version 2.1
 * 	Made observable
 * 
 * Date created: 09/12/20
 * Last modified: 21/12/20
 *
 */
public class GamePlay extends Subject{
	private static GamePlay gp=null;	// singleton instance of GamPlay
	private boolean canLeave;			// true if players can leave the island (win condition)
	private Board board;				// TODO Adam - I think this can be removed (Also from constructor)
	private PlayerList playerList;		// List of players in the game
	private boolean gameOver;			// true if the game is over (lose condition)
	
	/**
	 * playGame
	 * 	Carries out turns as long as gameOver is not true
	 * @param user
	 */
	public void playGame(Scanner user) {
		ArrayList<Player> players = playerList.getPlayers();
		while (!gameOver) {
			for (int i = 0; i< players.size(); i++) {
				Turn.getInstance().doTurn(players.get(i), user);	
			}
		}
	}
	
	/**
	 * tryLeave
	 * 	Called when players want to try to leave the island
	 * 	Notifies observers, which will change canLeave if conditions are met
	 */
	public void tryLeave() {
		System.out.println("Notifying");		// TODO Adam - remove when ready :) 
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
		this.board=Board.getInstance();
		this.playerList=PlayerList.getInstance();		// TODO should this be passed to the constructor maybe? not sure which is cleaner
	}
	
	/**
	 * tearDown
	 * 	used for testing
	 */
	public void tearDown() {
		gp=null;
	}
}
