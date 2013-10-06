package GameElements.Behaviors;


import GameElements.Piece;
import Utils.ChessGamePoint;


public interface Behavior {
	
	public BehaviorResult purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece);
}
