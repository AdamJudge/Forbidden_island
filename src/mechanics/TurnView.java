package mechanics;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import setup.ParseNumberInputs;
import setup.SetupController;
import players.Player;
import players.PlayerList;
import elements.pawns.*;
import elements.board.Tile;
import elements.cards.*;
import elements.board.WaterLevel;

/**
 * TurnView
 * 	View to display turn events
 * @author Catherine Waechter
 * @version 1.0
 * 
 * Date Created:  03/12/20
 * Last Modified: 03/12/20
 */
public class TurnView {

	public static TurnView turnView;
	private TurnController controller;
	private Player currentPlayer;
	
	/**
	 * getInstance
	 * @return turnView - singleton instance of TurnView
	 */
	public static TurnView getInstance() {
		if (turnView==null) {
			turnView =new TurnView();
		}
		return turnView;
	}
	
	/**
	 * run the turn view
	 * 
	 * @param player
	 * @param user
	 * @throws IOException
	 */
	public void run(Player player, Scanner user) throws IOException  {
//		System.out.println(board);  // TODO Print board at every turn if we can 
		currentPlayer = player;
		System.out.println("It's your turn " + player + "!");
		System.out.println("Your cards are : " + player.getHand());			// TODO should this be done by controller? (probably... )
		System.out.println("Your pawn is on " + player.getPawn().getTile());
		int actionCount = 3;
		boolean actionReturn;
		while(actionCount > 0) {
			// TODO Allow interrupt to play a card
			actionReturn = selectAction(user);
			if(actionReturn == true) {
				actionCount--;
			}
		}
		// TODO should still be able to play cards in here
		controller.drawTreasureCards(player);
		controller.drawFloodCards();
		
		System.out.println("Your cards are : " + player.getHand());			// TODO should this be done by controller? (probably... )

		// TODO get floodcards to return card name so it can be printed. Will also need to check status of tile to say if it was flooded or removed
	}
	
	/**
	 * selectAction
	 * 	Allow user to select desired action. calls each action
	 * @param user
	 * @return whether an action was used
	 * @throws IOException
	 */
	private boolean selectAction(Scanner user) throws IOException {
		System.out.println("Select action : "); // TODO print possible actions
		System.out.println("[1] Move");
		System.out.println("[2] Shore-up a tile");
		System.out.println("[3] Give someone a card");
		System.out.println("[4] Claim a treasure");
		int actionNum = ParseNumberInputs.main(user, 1, 4);				/// ---------------------- TODO why does parse inputs have a main? -------------------------
		
		switch(actionNum) {
		case 1:
			move(user);
			break;
		case 2:
			shoreup();
			break;
		case 3: 
			giveCard();
			break;
		case 4:
			claimTreasure();
			break;
		}
		
		return true; // use false for cases where the player has not used an action
	}
	
	private void shoreup() {
		
	}
	
	private void giveCard() {
		
	}
	private void claimTreasure() {
		
	}
	
	private void move(Scanner user) throws IOException{
		Player playerToMove = currentPlayer;
		
		// Navigator exception
		if(currentPlayer.getPawn() instanceof Navigator) {
			System.out.println("Would you like to move ");
			int i = 1;
			for (Player player : PlayerList.getInstance().getPlayers()) {
				if(player != currentPlayer) {
					System.out.println("[" + i + "]" + player);
				}
				else {
					System.out.println("[" + i + "]" + player + "(You)");
				}
				i++;
			}
			int input=ParseNumberInputs.main(user, 1, i);
			playerToMove = PlayerList.getInstance().getPlayers().get(input-1);
		}
		
		// TODO Pilot exception
		
		System.out.println(playerToMove + " can move to ");
		ArrayList<Tile> possibleTiles = controller.getMoveCheck(playerToMove);
		int limit = possibleTiles.size();
		System.out.println("Which tile do you want to move to?");
		int i = 1;
		for (Tile t:possibleTiles) {
			System.out.println("[" + i + "]" + t.getName());
			i++;
		}
		int userNum=ParseNumberInputs.main(user, 1, limit);
		Tile newTile = controller.move(playerToMove, possibleTiles.get(userNum-1));
		System.out.println(playerToMove + " has moved to " + newTile);
	}
	
    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setController(TurnController controller) {
    	this.controller = controller;
    }
    
	
}
