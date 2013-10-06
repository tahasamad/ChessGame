package ChessGameKenai;

import java.awt.Color;

import Utils.ChessGameRect;


public class ChessGameConstants {
	//Strings
	public static final String boardImage = "Icons/board.jpg";
	public static final String copyrightString = "All Rights Reserved @Dimitri_Mario_Valeria";
	public static final String turnToPlay = " turn to play";
	public static final String tAreaTopString = "--------Moves Made-------\n";
	public static final String hasWon = " has won!";
	public static final String saveFile = "saved_game.dat";
	public static final String saveFileCapturedPieces = "saved_game_captured_pieces.dat";
	//Values
	public static final int pieceDimension = 64;
	public static final int gridDimension = 8;
	public static final ChessGameRect pieceBounds = new ChessGameRect(5, 5, 55, 55);
	public static final int squareSize = 65;
	public static final int pieceIndex = 0;
	public static final Color selectedSquareColor = Color.BLUE;
}
