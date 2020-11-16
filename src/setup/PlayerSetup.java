// Sets up player with a random pawn and user entered name. Handles number of players too for game. Gives empty hand

package setup;

import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import players.Player;
import elements.pawns.Pawn;
import elements.pawns.PawnEnum;
import elements.pawns.*;

public class PlayerSetup {
	private List<Player> playerList = new ArrayList<Player>();
	private List<String> pawnList = new ArrayList<String>();
	public Set<String> playerNames = new TreeSet<String>();
	private int numPlayers;
	
	public PlayerSetup() {
		this.numPlayers = 0;
	}
	
	public void setupPlayers(Scanner user) {
		setPlayerNum(user);
		initPawnEnums();
		setPlayerNames(user);
		setupPlayerPawns();
	}
	
	public void setPlayerNum(Scanner user) {
		int inputNum = 0;
		// While player numbers is outside of required range
		while (inputNum <2 || inputNum > 4) {
			System.out.println("How many players? [2-4]");
			try {	
				inputNum = user.nextInt();
			} catch (NoSuchElementException e)
			{
				System.out.println("Enter an int between 2 and 4 inclusive.");
				user.next();
			}
		}
		System.out.println("Number of players chosen is: " + inputNum);
		this.setNumPlayers(inputNum);
	}
	
	// gets player names for each user. Can contain symbols letters and numbers.
	public void setPlayerNames(Scanner user) {
		String line;
		for(int i=1; i <= this.getNumPlayers(); i++) {
			System.out.println("Enter Player " + i + "'s name: ");
			line=user.next();
			playerNames.add(line);
		}
	}
	
	public void setupPlayerPawns() {
		System.out.println("Players: " + playerNames);
		for (String p : playerNames) {
			System.out.println(p);
			Player player = new Player(p);
			System.out.println(p + " will have this pawn: " + pawnList.get(0));
			Pawn pawn=PlayerSetup.makePawn(pawnList.get(0));
			player.setPawn(pawn);
			System.out.println("Player Pawn set to " + pawnList.get(0));
			pawnList.remove(0);
			playerList.add(player);
			System.out.println(player.toString());
		}
	}
	
	public void initPawnEnums() {
		// Try do with enum...
		pawnList.add("Diver");
		pawnList.add("Engineer");
		pawnList.add("Explorer");
		pawnList.add("Messenger");
		pawnList.add("Navigator");
		pawnList.add("Pilot");
		//Shuffle pawns to distribute randomly to players
		Collections.shuffle(pawnList);
	}

	public static Pawn makePawn(String p_name) {
		Pawn pawn = null;
		switch(p_name) {
		case "Diver":
			pawn = new Diver();
			break;
		case "Engineer":
			pawn = new Engineer();
			break;
		case "Explorer":
			pawn = new Explorer();
			break;
		case "Messenger":
			pawn = new Messenger();
			break;
		case "Navigator":
			pawn = new Navigator();
			break;
		case "Pilot":
			pawn = new Pilot();
			break;
		default:
			System.out.println("Pawn name " + p_name + " not found");
			pawn = new Pilot();
		}
		return pawn;
	}
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

}
