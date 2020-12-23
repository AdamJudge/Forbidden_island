package testCases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    BoardTest.class, 
    CaptureTreasure.class,
    DeckTest.class,
    LoseConditionTests.class,
    MovementTests.class,
    PlayableCardsTest.class,
    PlayerTests.class,
    ShoreUpTests.class,
    SwimTests.class,
    UserInputTest.class,
    WinConditionTests.class
})

public class RunAllTests {

}
