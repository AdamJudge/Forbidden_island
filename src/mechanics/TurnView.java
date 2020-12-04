package mechanics;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import setup.ParseNumberInputs;
import setup.SetupController;
import players.Player;
import players.PlayerList;
import elements.pawns.*;
import elements.board.Board;
import elements.board.Tile;
import elements.cards.*;
import elements.board.WaterLevel;
import mechanics.actions.*;

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

	private static TurnView turnView = null;
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
		System.out.println(Board.getInstance());  // TODO Print board at every turn if we can 
		currentPlayer = player;
		System.out.println("It's your turn " + player + "!");
		System.out.println("Your cards are : " + player.getHand());			// TODO should this be done by controller? (probably... )
		System.out.println("Your " + player.getPawn() + " is on " + player.getPawn().getTile());
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
			return MoveView.getInstance(controller).doAction(currentPlayer, user);
		case 2:
			return ShoreupView.getInstance(controller).doAction(currentPlayer, user);
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
	

    /**
     * setController
     * 	assign controller instance
     * @param controller
     */
    public void setupView(TurnController controller) {
    	this.controller = controller;
    	ClaimTreasureView.getInstance(controller);
    	GiveCardView.getInstance(controller);
    	MoveView.getInstance(controller);
    	ShoreupView.getInstance(controller);
    }
    
	
}
