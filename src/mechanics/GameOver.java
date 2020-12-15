package mechanics;

public class GameOver{
	private String resultString;
	
	public GameOver(Boolean gameVictory) {
		endGame(gameVictory);
	}
	
	private void endGame(Boolean gameVictory) {
		//If win else lose
		if (gameVictory) {
			resultString="You have escaped Forbidden Island!\n\nCongratulations!";
		}	else {
			resultString="You have failed to escaped Forbidden Island!\n\nBetter luck next time!";
		}
		printMessageAndExit();
	}

	private void printMessageAndExit() {
		System.out.println("   ____      _      __  __  U _____ u       U  ___ u__     __ U _____ u   ____     ");
		System.out.println("U /\"___|uU  /\"\\  uU|' \\/ '|u\\| ___\"|/        \\/\"_ \\/\\ \\   /\"/u\\| ___\"|/U |  _\"\\ u  ");
		System.out.println("\\| |  _ / \\/ _ \\/ \\| |\\/| |/ |  _|\"          | | | | \\ \\ / //  |  _|\"   \\| |_) |/");
		System.out.println(" | |_| |  / ___ \\  | |  | |  | |___      .-,_| |_| | /\\ V /_,-.| |___    |  _ <");
		System.out.println("  \\____| /_/   \\_\\ |_|  |_|  |_____|      \\_)-\\___/ U  \\_/-(_/ |_____|   |_| \\_\\");
		System.out.println("  _)(|_   \\\\    >><<,-,,-.   <<   >>           \\\\     //       <<   >>   //   \\\\_");
		System.out.println(" (__)__) (__)  (__)(./  \\.) (__) (__)         (__)   (__)     (__) (__) (__)  (__)\n");
		System.out.println(resultString);
		System.exit(0);
	}
}
