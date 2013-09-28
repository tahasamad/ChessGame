package GameElements.Behaviors;


import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;


public interface Behavior {
	//public SomeObject canMove(Piece piece);
	PurposedMoveResult purposeMove(ChessGamePoint newPosition, Non_Visual_Piece pieceModel);
}
