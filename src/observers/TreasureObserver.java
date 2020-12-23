package observers;

import elements.board.Tile;
import elements.board.TileStatus;


import elements.treasures.*;
import mechanics.GameOver;
public class TreasureObserver extends Observer {
	private Subject subject2;
	private Treasure treasureName;
	public TreasureObserver(Subject subject, Subject subject2, Treasure treasure) {
		this.subject=subject;
		this.subject2=subject2;
		this.treasureName=treasure;
		this.subject.attach(this);
		this.subject2.attach(this);
	}
	
	@Override
	public void update() {
		if (((Tile)subject).getStatus().equals(TileStatus.REMOVED) && ((Tile)subject2).getStatus().equals(TileStatus.REMOVED) && ! treasureName.isCaptured()) {
			System.out.println("Both "+ treasureName.toString()+" treasure tiles have sunken without the treasure being captured!");
			GameOver.endGame(false);
		}
	}
}
