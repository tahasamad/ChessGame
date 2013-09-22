package ChessGameKenai;


public class ChessGameConstants {
	//Strings
	public static final String boardImage = "Icons/board.jpg";
	public static final String copyrightString = "All Rights Reserved @Dimitri_Mario_Valeria";
	//Values
	public static final int pieceDimension = 64;
	//Enums
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

}
