// Sets up player with a random pawn and user entered name. Handles number of players too for game. Gives empty hand

package mechanics.setup;

import java.util.*;
import players.Player;
import players.PlayerList;
import elements.pawns.Pawn;
import elements.pawns.*;
import elements.cards.TreasureDeck;

/**
 * PlayerSetup
 * 	Handles setup of the players (name, role (pawn), hand)
 * @author Adam Judge, Catherine Waechter
 * @version 2.1
 * 	Player list ordered
 * 
 * Date created: 23/11/20 
 * Last modified: 22/12/20
 *
 */
public class PlayerSetup {
	private static PlayerSetup playerSetup = null;	// singleton PlayerSetup instance
	
	private PlayerList playerList;	// Ordered list of players
	private int numPlayers;			// number of players in the game
	
	/**
	 * getInstance 
	 * @return PlayerSetup instance
	 */
	public static PlayerSetup getInstance() {
		if(playerSetup == null) {
			playerSetup = new PlayerSetup();
		}
		return playerSetup;
	}
	
	/**
	 * PlayerSetup Constructor
	 * 	Initialise numPlayers to 0, Initialise playerList
	 */
	private PlayerSetup() {
		this.numPlayers = 0;
		this.playerList=PlayerList.getInstance();
	}
		
	/**
	 * setupPlayerList
	 * 	assign players names, pawns and an initial hand
	 * 
	 * @param playerNames - set of player names
	 * @return playerList - list of players
	 */
	public PlayerList setupPlayerList(Queue<String> playerNames) {
		// set player names
		while (!playerNames.isEmpty()){
			Player player = new Player(playerNames.remove());
			playerList.addPlayer(player);	
		}
		
		// assign pawns and deal starting hand
		assignPawns();
		dealInitialHands();
		
		return playerList;
	}

	/**
	 * dealInitialHands
	 * 	deal 2 cards to each player (waters rise exception handled in TreasureDeck)
	 */
	private void dealInitialHands() {
		for (int cardNum = 0; cardNum <2; cardNum++) {
			for (Player player : playerList.getPlayers()) {
				player.getHand().addCard(TreasureDeck.getInstance().draw());
			}
		}
	}
	
	/**
	 * assignPawns
	 * 	Assign each player a pawn type at random
	 */
	private void assignPawns() {
		List<Pawn> pawnList = new ArrayList<Pawn>();
		
		pawnList.add(new Diver());
		pawnList.add(new Engineer());
		pawnList.add(new Explorer());
		pawnList.add(new Messenger());
		pawnList.add(new Navigator());
		pawnList.add(new Pilot());
		Collections.shuffle(pawnList);
		
		for (Player player : playerList.getPlayers()) {
			player.setPawn(pawnList.get(0));
			pawnList.remove(0);
			player.getPawn().toInitialTile();
		}
	}
	
	/**
	 * getNumPlayers
	 * @return numPlayers - number of players in the game
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * setNumPlayers
	 * @param numPlayers - number of players in the game
	 */
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
}
