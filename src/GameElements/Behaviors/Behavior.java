package GameElements.Behaviors;


import GameElements.Piece;
import Utils.ChessGamePoint;


public interface Behavior {
	
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece);
}
