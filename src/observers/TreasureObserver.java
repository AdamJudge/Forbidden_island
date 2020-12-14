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
		System.out.println("Treasure status of "+treasureName.toString()+": " + ((Tile)subject).getTreasure().isCaptured());
		System.out.println("Tile: " + ((Tile)subject).toString() + ", Status: " + ((Tile)subject).getStatus());
		System.out.println("Tile: " + ((Tile)subject2).toString() + ", Status: " + ((Tile)subject2).getStatus());
		if (((Tile)subject).getStatus().equals(TileStatus.REMOVED) && ((Tile)subject2).getStatus().equals(TileStatus.REMOVED) && ! treasureName.isCaptured()) {
			System.out.println("Both "+ treasureName.toString()+" treasure tiles have sunken without the treasure being captured!");
			new GameOver(false);
		}
	}
}
