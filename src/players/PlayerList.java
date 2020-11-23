package players;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {
	public static PlayerList pl=null;
	public List<Player> playerList=new ArrayList<Player>();
	
	public static PlayerList getInstance() {
		if (pl==null) {
			pl=new PlayerList();
		}
		return pl;
	}
	
	private PlayerList() {
		
	}
	
	public void addPlayer(Player p) {
		this.playerList.add(p);
	}
	
	public List<Player> getPlayers() {
		return playerList;
	}
	
	@Override
	public String toString() {
		return (playerList.toString());
	}
}
