package GameElements.Behaviors;


import GameElements.Piece;
import Utils.ChessGamePoint;


public interface Behavior {
	
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece);
}
