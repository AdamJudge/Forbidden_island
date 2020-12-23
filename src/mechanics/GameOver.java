package mechanics;

/**
 * GameOver
 * 
 * Display for game over. Created once at the end of the game.
 * 
 * @author Adam Judge, Catherine Waechter
 * @version 1.4
 * 	Made methods static
 * 
 * Date Created : 10/12/20
 * Last Modified: 23/12/20
 *
 */
public class GameOver{	
	
	/**
	 * endGame
	 * 
	 * prints end of game screen and sets "Game Over" in game play to true
	 * 
	 * @param gameVictory - true if win, false if lose
	 */
	public static void endGame(boolean gameVictory) {
		
		if (gameVictory) {
			printWin();
		}	
		else {
			printLoss();
		}
		GamePlay.getInstance().setGameOver(true);
	}

	/**
	 * printWin
	 * print win screen
	 */
	private static void printWin(){
		System.out.println("  __   __   U  ___ u   _   _                                _   _     _    ");
		System.out.println("  \\ \\ / /    \\/\"_ \\/U |\"|u| |     __        __     ___     | \\ |\"|  U|\"|u");
		System.out.println("   \\ V /     | | | | \\| |\\| |     \\\"\\      /\"/    |_\"_|   <|  \\| |> \\| |/");
		System.out.println("  U_|\"|_u.-,_| |_| |  | |_| |     /\\ \\ /\\ / /\\     | |    U| |\\  |u  |_|");
		System.out.println("    |_|   \\_)-\\___/  <<\\___/     U  \\ V  V /  U  U/| |\\u   |_| \\_|   (_)");
		System.out.println(".-,//|(_       \\\\   (__) )(      .-,_\\ /\\ /_,-.-,_|___|_,-.||   \\\\,-.|||_");
		System.out.println(" \\_) (__)     (__)      (__)      \\_)-'  '-(_/ \\_)-' '-(_/ (_\")  (_/(__)_)\n");
		System.out.println("You have escaped Forbidden Island!\n\nCongratulations!");
	}
	
	/**
	 * printLoss
	 * print loss screen
	 */
	private static void printLoss(){
		System.out.println("   ____      _      __  __  U _____ u       U  ___ u__     __ U _____ u   ____     ");
		System.out.println("U /\"___|uU  /\"\\  uU|' \\/ '|u\\| ___\"|/        \\/\"_ \\/\\ \\   /\"/u\\| ___\"|/U |  _\"\\ u  ");
		System.out.println("\\| |  _ / \\/ _ \\/ \\| |\\/| |/ |  _|\"          | | | | \\ \\ / //  |  _|\"   \\| |_) |/");
		System.out.println(" | |_| |  / ___ \\  | |  | |  | |___      .-,_| |_| | /\\ V /_,-.| |___    |  _ <");
		System.out.println("  \\____| /_/   \\_\\ |_|  |_|  |_____|      \\_)-\\___/ U  \\_/-(_/ |_____|   |_| \\_\\");
		System.out.println("  _)(|_   \\\\    >><<,-,,-.   <<   >>           \\\\     //       <<   >>   //   \\\\_");
		System.out.println(" (__)__) (__)  (__)(./  \\.) (__) (__)         (__)   (__)     (__) (__) (__)  (__)\n");
		System.out.println("You have failed to escaped Forbidden Island!\n\nBetter luck next time!");
	}
}
