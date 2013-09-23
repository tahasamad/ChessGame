package GameElements;
	
public enum PieceType {
	Queen(1),
	King(2),
	Rook(3),
	Pawn(4),
	Knight(5),
	Bishop(6);

	public int currentValue = 0;

	private PieceType(int value) {
		currentValue = value;
	}

	public int value() {
		return currentValue;
	}
}