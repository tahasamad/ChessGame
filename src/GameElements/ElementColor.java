package GameElements;

public enum ElementColor {
	Black(1),
	White(2); 

	public int currentValue = 0;

	private ElementColor(int value) {
		currentValue = value;
	}

	public int value() {
		return currentValue;
	}
}
