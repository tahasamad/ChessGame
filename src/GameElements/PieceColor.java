package GameElements;

public enum PieceColor {
	Black(1),
	White(2); 

	public int currentValue = 0;

	private PieceColor(int value) {
		currentValue = value;
	}

	public int value() {
		return currentValue;
	}
}
