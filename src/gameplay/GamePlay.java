package gameplay;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import elements.board.Board;
import players.Player;
import players.PlayerList;
import mechanics.Turn;

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
	
	public void playGame(Scanner user) throws IOException {
		while (!this.gameOver) {
			for (Player player: playerList.getPlayers()) {
				Turn.getInstance().doTurn(player, user);	
			}
//			this.gameOver=true;
		}
	}
}
