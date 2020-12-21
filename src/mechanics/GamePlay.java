package mechanics;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import elements.board.Board;
import observers.Subject;
import players.Player;
import players.PlayerList;

public class GamePlay extends Subject{
	private static GamePlay gp=null;
	private boolean canLeave;
	private Board board;
	private PlayerList playerList;
	private Boolean gameOver;
	
	
	public static GamePlay getInstance() {
		if(gp==null) {
			gp=new GamePlay();
		}
		return gp;
	}
	
	private GamePlay() {
		this.canLeave=false;
		this.gameOver=false;
		this.board=Board.getInstance();
		this.playerList=PlayerList.getInstance();
	}
	
	public void tryLeave() {
		System.out.println("Notifying");
		notifyAllObservers();
	}
	
	// Observer will update this when updated in tryLeave
	public void setLeave(Boolean bool) {
		canLeave=bool;
	}
	
	public Boolean canLeave() {
		return canLeave;
	}
	
	public void setGameOver(Boolean bool) {
		gameOver=bool;
	}
	
	public Boolean getGameOver() {
		return gameOver;
	}
	
	public void playGame(Scanner user) {
		ArrayList<Player> players = playerList.getPlayers();
		while (!gameOver) {
			for (int i = 0; i< players.size(); i++) {
				Turn.getInstance().doTurn(players.get(i), user);	
			}
		}
	}

	public void tearDown() {
		gp=null;
	}
}
