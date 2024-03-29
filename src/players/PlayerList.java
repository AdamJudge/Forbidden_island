package players;

import java.util.ArrayList;

/**
 * PlayerList (Singleton)
 * 	Holds the list of players in the game in a singleton instance
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.2
 * 	Added getPlayers
 *
 *	Date created: 23/11/20
 *	Last modified: 04/12/20
 */
public class PlayerList {
	public static PlayerList pl=null;
	public ArrayList<Player> playerList;
	
	private PlayerList() {
		playerList =new ArrayList<Player>();
	}
	
	public void addPlayer(Player p) {
		this.playerList.add(p);
	}
	
	public ArrayList<Player> getPlayers() {
		return playerList;
	}
	
	@Override
	public String toString() {
		return (playerList.toString());
	}
	
	/**
	 * getInstance
	 * @return singleton instance of the player list
	 */
	public static PlayerList getInstance() {
		if (pl==null) {
			pl=new PlayerList();
		}
		return pl;
	}
	
	/**
	 * tearDown
	 * used in testing
	 */
	public void tearDown() {
		pl=null;
	}
}
