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
		System.out.println("Checking conditions for leaving!");
		Boolean onFoolsIsland=true, treasuresCaptured=true;
		String notOnFI=" ";
		String notCaptured=" ";
		for (Player p:PlayerList.getInstance().getPlayers())
		{
			//If a player is not on fools landing set condition to false
			if (p.getPawn().getTile().getName() != TileNames.FOOLS_LANDING) {
				onFoolsIsland=false;
				notOnFI+=p.toString() + ", ";
			}
		}
		System.out.println(notOnFI + " must be on Fools Landing!");

		
		for (Treasure t:Board.getInstance().getTreasures().values()) {
			if (!t.isCaptured()) {
				treasuresCaptured=false;
				notCaptured+=t.toString() + ", ";
			}
		}
		System.out.println(notCaptured + " must be captured first!");

		System.out.println("All players on fools landing?: " + onFoolsIsland);
		System.out.println("All treasures captured?: " + treasuresCaptured);

		if (onFoolsIsland && treasuresCaptured) {
			System.out.println("Safe flight! x");
			GamePlay.getInstance().setLeave(true);
			new GameOver(true);
		} else {
			GamePlay.getInstance().setLeave(false);
		}
	}
}
