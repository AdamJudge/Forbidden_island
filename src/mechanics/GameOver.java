package mechanics;

public class GameOver{	
	public GameOver(boolean gameVictory){
		endGame(gameVictory);
	}
	
	private void endGame(boolean gameVictory) {
		//If win else lose
		String resultString;
		if (gameVictory) {
			resultString="You have escaped Forbidden Island!\n\nCongratulations!";
			printWin(resultString);
		}	else {
			resultString="You have failed to escaped Forbidden Island!\n\nBetter luck next time!";
			printLoss(resultString);
		}
		GamePlay.getInstance().setGameOver(true);
	}

	private void printWin(String resultString){
		System.out.println("  __   __   U  ___ u   _   _                                _   _     _    ");
		System.out.println("  \\ \\ / /    \\/\"_ \\/U |\"|u| |     __        __     ___     | \\ |\"|  U|\"|u");
		System.out.println("   \\ V /     | | | | \\| |\\| |     \\\"\\      /\"/    |_\"_|   <|  \\| |> \\| |/");
		System.out.println("  U_|\"|_u.-,_| |_| |  | |_| |     /\\ \\ /\\ / /\\     | |    U| |\\  |u  |_|");
		System.out.println("    |_|   \\_)-\\___/  <<\\___/     U  \\ V  V /  U  U/| |\\u   |_| \\_|   (_)");
		System.out.println(".-,//|(_       \\\\   (__) )(      .-,_\\ /\\ /_,-.-,_|___|_,-.||   \\\\,-.|||_");
		System.out.println(" \\_) (__)     (__)      (__)      \\_)-'  '-(_/ \\_)-' '-(_/ (_\")  (_/(__)_)\n");
		System.out.println(resultString);
	}
	
	private void printLoss(String resultString){
		System.out.println("   ____      _      __  __  U _____ u       U  ___ u__     __ U _____ u   ____     ");
		System.out.println("U /\"___|uU  /\"\\  uU|' \\/ '|u\\| ___\"|/        \\/\"_ \\/\\ \\   /\"/u\\| ___\"|/U |  _\"\\ u  ");
		System.out.println("\\| |  _ / \\/ _ \\/ \\| |\\/| |/ |  _|\"          | | | | \\ \\ / //  |  _|\"   \\| |_) |/");
		System.out.println(" | |_| |  / ___ \\  | |  | |  | |___      .-,_| |_| | /\\ V /_,-.| |___    |  _ <");
		System.out.println("  \\____| /_/   \\_\\ |_|  |_|  |_____|      \\_)-\\___/ U  \\_/-(_/ |_____|   |_| \\_\\");
		System.out.println("  _)(|_   \\\\    >><<,-,,-.   <<   >>           \\\\     //       <<   >>   //   \\\\_");
		System.out.println(" (__)__) (__)  (__)(./  \\.) (__) (__)         (__)   (__)     (__) (__) (__)  (__)\n");
		System.out.println(resultString);
	}
}
