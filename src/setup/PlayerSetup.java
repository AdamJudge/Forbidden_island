// Sets up player with a random pawn and user entered name. Handles number of players too for game. Gives empty hand

package setup;

import java.io.IOException;
import java.util.*;
import players.Player;
import players.PlayerList;
import elements.pawns.Pawn;
import elements.pawns.*;

public class PlayerSetup {
	private static PlayerSetup playerSetup = null;
	private PlayerList playerList;
	//private List<Player> playerList = new ArrayList<Player>();
	public List<Pawn> pawnList = new ArrayList<Pawn>();
	public List<String> playerNames = new ArrayList<String>();
	private int numPlayers;
	
	public static PlayerSetup getInstance() {
		if(playerSetup == null) {
			playerSetup = new PlayerSetup();
		}
		return playerSetup;
	}
	
	private PlayerSetup() {
		this.numPlayers = 0;
		this.playerList=PlayerList.getInstance();
	}
	
	public void setupPlayers(Scanner user) throws IOException {
		setPlayerNum(user);
		setPlayerNames(user);
		makePawns();
		setupPlayerList();
		addPlayerPawns();
		setupPlayerHand();
	}
	
	public void setPlayerNum(Scanner user) throws IOException {
		int inputNum;
		System.out.println("How many players?");
		inputNum = ParseNumberInputs.main(user, 2, 4);
		System.out.println("Number of players chosen is: " + inputNum);
		this.setNumPlayers(inputNum);
	}
	
	// gets player names for each user. Can contain symbols letters and numbers.
	public void setPlayerNames(Scanner user) throws IOException {
		//Sets players names
		boolean duplicate;
		String name;
		for(int i=1; i <= this.getNumPlayers(); i++) {
			duplicate=true;
			System.out.println("Enter Player " + i + "'s name: ");
			//Loop while entered name is a duplicate
			while (duplicate){
				name=ParseLetterInputs.main(user);
				if (playerNames.contains(name)) {
					System.out.println("Enter an unique name for player "+i+":");
				} else {
					duplicate=false;
					playerNames.add(name);
				}
			}
		}
	}
	
	public void setupPlayerList() {
		System.out.println("Players: " + playerNames);
		for (String p : playerNames) {
			Player player = new Player(p);
			playerList.addPlayer(player);
		}
	}
	
	public void addPlayerPawns() {
		for (Player p : playerList.getPlayers()) {
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
		for (Player p : playerList.getPlayers()) {
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
