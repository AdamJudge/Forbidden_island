package observers;

import elements.board.Board;
import elements.board.TileNames;
import elements.treasures.Treasure;
import mechanics.GameOver;
import mechanics.GamePlay;
import players.Player;
import players.PlayerList;

/**
 * HelicopterLiftWinObserver
 * 	Handles setting up and attachment of observers
 * @author Adam Judge
 * @version 3
 * Date created: 12/12/20 
 * Last modified: 24/12/20
 *
 */

public class HelicopterLiftWinObserver extends Observer {
	public HelicopterLiftWinObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
	//Updates when a helicopter lift is used in an attempt at escaping the island
	@Override
	public void update() {
		boolean onFoolsIsland=true, treasuresCaptured=true;
		String notOnFI="";
		String notCaptured="";
		for (Player p:PlayerList.getInstance().getPlayers())
		{
			//If a player is not on fools landing set condition to false
			if (p.getPawn().getTile().getName() != TileNames.FOOLS_LANDING) {
				onFoolsIsland=false;
				notOnFI+=p.toString() + ",";
			}
		}
		
		if (!onFoolsIsland) {
			System.out.println(notOnFI + " must be on Fools Landing!");
		}
		
		for (Treasure t:Board.getInstance().getTreasures().values()) {
			if (!t.isCaptured()) {
				//If a treasure is not captured set to false
				treasuresCaptured=false;
				notCaptured+=t.toString() + ",";
			}
		}
		
		if (!treasuresCaptured) {
			System.out.println(notCaptured + " must be captured first!");
		}

		//Conditions met
		if (onFoolsIsland && treasuresCaptured) {
			GamePlay.getInstance().setLeave(true);
			//Trigger victory
			GameOver.endGame(true);
		} else {
			GamePlay.getInstance().setLeave(false);
		}
	}
}
