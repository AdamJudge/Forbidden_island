package mechanics;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

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
	
	public void playGame(Scanner user) throws IOException {
		ArrayList<Player> players = playerList.getInstance().getPlayers();
		while (!this.gameOver) {
			for (int i = 0; i< players.size(); i++) {
				Turn.getInstance().doTurn(players.get(i), user);	
			}
//			this.gameOver=true;
		}
	}
}
