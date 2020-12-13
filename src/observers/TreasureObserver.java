package observers;

import elements.board.Tile;
import elements.board.TileStatus;


import elements.treasures.*;
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
		System.out.println("Treasure status 1: " + ((Tile)subject).getTreasure().isCaptured() + ", on tile: " + ((Tile)subject).toString());
		System.out.println("Treasure status 2: " + ((Tile)subject2).getTreasure().isCaptured() + ", on tile: " + ((Tile)subject2).toString());
		System.out.println(treasureName.toString());
		if (((Tile)subject).getStatus().equals(TileStatus.REMOVED) && ((Tile)subject2).getStatus().equals(TileStatus.REMOVED) && ! treasureName.isCaptured()) {
			System.out.println("Both "+ treasureName.toString()+" treasure tiles have sunken without the treasure being captured!");
			System.exit(0);
		}
		//System.out.println("Forbidden Island has Sunk beneath the waves!");
	}
}
