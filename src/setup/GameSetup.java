package setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

import elements.board.WaterLevel;
import elements.board.Difficulty;
public class GameSetup {
	public static GameSetup gs=null;
	public WaterLevel waterLevel;
	
	public static GameSetup getInstance() {
		if(gs==null) {
			gs=new GameSetup();
		}
		return gs;
	}
	
	private GameSetup() {

	}
	
	public void setupGame(Scanner user) throws IOException {
		setupWaterLevel(user);
	}
	
	public void setupWaterLevel(Scanner user) throws IOException {
		this.waterLevel= WaterLevel.getInstance();
		Difficulty difficulty;
		int input;
		int i=1;
		List<Difficulty> difficultyList = new ArrayList<Difficulty>(EnumSet.allOf(Difficulty.class));
		System.out.println("Which difficulty do you want to play?");
		for (Difficulty d:difficultyList) {
			System.out.println("[" + i + "]: "+d.toString());
			i+=1;
		}
		input=ParseNumberInputs.main(user, 1, i-1);
		difficulty=difficultyList.get(input-1);
		waterLevel.setDifficulty(difficulty);
		System.out.println("Water level has been set to: "+ waterLevel.getLevel());
	}
}
