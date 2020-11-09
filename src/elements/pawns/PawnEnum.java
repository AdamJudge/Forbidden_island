package elements.pawns;

public enum PawnEnum {
	DIVER("Diver"),
	ENGINEER("Engineer"),
	EXPLORER("Explorer"),
	MESSENGER("Messenger"),
	NAVIGATOR("Navigator"),
	PILOT("Pilot");
	
	private final String pawnNames;
	
	private PawnEnum(String pawnNames){
		this.pawnNames = pawnNames;
	}
	
	public String getPawnName() {
		return pawnNames;
	}
}
