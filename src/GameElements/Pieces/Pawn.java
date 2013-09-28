package GameElements.Pieces;

import ChessGameKenai.Board;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.Behaviors.HorizontalAndVerticalBehavior;
import Utils.ChessGamePoint;

public class Pawn extends Piece{
	
	public Pawn(Non_Visual_Piece pieceModel, Board board)
	{
		super(pieceModel, board);
		this.behavior = new HorizontalAndVerticalBehavior();
	}
	
	@Override
	public boolean tryToMove(ChessGamePoint position) {
		//Implement change in behavior here.
		return super.tryToMove(position);
	}
	
}
