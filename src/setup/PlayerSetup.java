// Sets up player with a random pawn and user entered name. Handles number of players too for game. Gives empty hand

package setup;

import java.util.*;

import players.Player;
import elements.pawns.Pawn;
import elements.pawns.*;

public class PlayerSetup {
//	private static PlayerSetup playerSetup = null;
	private List<Player> playerList = new ArrayList<Player>();
	public List<Pawn> pawnList = new ArrayList<Pawn>();
	public Set<String> playerNames = new TreeSet<String>();
	private int numPlayers;
	
/*	public static PlayerSetup getInstance() {
		if(playerSetup == null) {
			playerSetup = new PlayerSetup();
		}
		return playerSetup;
	}*/
	
	public PlayerSetup() {
		this.numPlayers = 0;
	}
	
	public void setupPlayers(Scanner user) {
		setPlayerNum(user);
		setPlayerNames(user);
		makePawns();
		setupPlayerList();
		addPlayerPawns();
		setupPlayerHand();
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
	
	public void setupPlayerList() {
		System.out.println("Players: " + playerNames);
		for (String p : playerNames) {
			Player player = new Player(p);
			playerList.add(player);
		}
	}
	
	public void addPlayerPawns() {
		for (Player p : playerList) {
			System.out.println(p.getName() + " will have this pawn: " + pawnList.get(0).toString());
			p.setPawn(pawnList.get(0));
			System.out.println("Player Pawn set to " + pawnList.get(0));
			pawnList.remove(0);
		}
	}

	public void makePawns() {
		//Creates all pawns and adds to pawnList
		this.pawnList.add(new Diver());
		this.pawnList.add(new Engineer());
		this.pawnList.add(new Explorer());
		this.pawnList.add(new Messenger());
		this.pawnList.add(new Navigator());
		this.pawnList.add(new Pilot());
		Collections.shuffle(pawnList);
	}
	
	public void setupPlayerHand() {
		for (Player p : playerList) {
			System.out.println("Setting up " + p.getName() + "'s hand"); //TODO add to hand when cards exist
		}
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
}
