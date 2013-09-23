package ChessGameKenai;

public enum BoardFlipMode {
	Normal(1),
	Flipped(2);
	
	public int currentValue = 0;

	private BoardFlipMode(int value) {
		currentValue = value;
	}

	public int value() {
		return currentValue;
	}
}
