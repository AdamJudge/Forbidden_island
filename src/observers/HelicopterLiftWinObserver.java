package observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import elements.board.Board;
import elements.board.TileNames;
import elements.treasures.Treasure;
import elements.treasures.TreasureNames;
import mechanics.GameOver;
import mechanics.GamePlay;
import mechanics.TurnController;
import mechanics.TurnView;
import mechanics.actions.ClaimTreasureView;
import players.Player;
import players.PlayerList;

public class HelicopterLiftWinObserver extends Observer {
	public HelicopterLiftWinObserver(Subject subject) {
		this.subject=subject;
		this.subject.attach(this);
	}
	
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
		
		if (!notOnFI.equals("")) {
			System.out.println(notOnFI + " must be on Fools Landing!");
		}
		
		for (Treasure t:Board.getInstance().getTreasures().values()) {
			if (!t.isCaptured()) {
				treasuresCaptured=false;
				notCaptured+=t.toString() + ",";
			}
		}
		
		if (!notCaptured.equals("")) {
			System.out.println(notCaptured + " must be captured first!");
		}

		if (onFoolsIsland && treasuresCaptured) {
			GamePlay.getInstance().setLeave(true);
			GameOver.endGame(true);
		} else {
			GamePlay.getInstance().setLeave(false);
		}
	}
}
