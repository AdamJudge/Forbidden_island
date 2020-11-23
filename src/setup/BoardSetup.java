package setup;

import elements.board.Board;

public class BoardSetup {
	private static BoardSetup bSetup = null;
	public Board board;
	
	public static BoardSetup getInstance() {
		if (bSetup == null) {
			bSetup = new BoardSetup();
		}
		return bSetup;
	}
	
	private BoardSetup() {
		this.board = new Board();
	}
	
	public void setupBoard() {
		//Do any setup required 
	}
}
