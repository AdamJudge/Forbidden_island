package elements.cards;

public abstract class Card {
	// Needed for the decks, as they are implemented using general Cards, so that the same functionality can be used in any type of deck
	// Different card types share no functionality, so there is no requirement for specific methods (excpet toString) 
	
	@Override
	public abstract String toString(); 
}
