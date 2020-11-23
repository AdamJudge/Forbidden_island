package gameplay;

import java.util.List;
import java.util.Scanner;

import elements.board.Board;
import players.Player;
import players.PlayerList;

public class GamePlay {
	private static GamePlay gp=null;
	private boolean gameOver;
	private Board board;
	private PlayerList playerList;
	
	
	public static GamePlay getInstance() {
		if(gp==null) {
			gp=new GamePlay();
		}
		return gp;
	}
	
	private GamePlay() {
		this.gameOver=false;
		this.board=Board.getInstance();
		this.playerList=PlayerList.getInstance();
	}
	
	public void playGame(Scanner user) {
		while (!this.gameOver) {
			for (Player p: playerList.getPlayers()) {
				System.out.println("It's your turn " + p.getName() + "!");
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
			this.gameOver=true;
		}
	}
}
