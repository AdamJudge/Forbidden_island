package testCases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	SetupTest.class,
	BoardTest.class, 
	CaptureTreasure.class,
    DeckTest.class,
    TurnTest.class,
    GiveCardTests.class,
    LoseConditionTests.class,
    MovementTests.class,
    PlayableCardsTest.class,
    WatersRiseTest.class,
    PlayerTests.class,
    ShoreUpTests.class,
    SwimTests.class,
    UserInputTest.class,
    WinConditionTests.class
})

public class RunAllTests {

}
