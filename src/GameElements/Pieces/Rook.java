package GameElements.Pieces;

import ChessGameKenai.Board;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.Behaviors.HorizontalAndVerticalBehavior;

public class Rook extends Piece{

	public Rook(Non_Visual_Piece pieceModel, Board board) {
		super(pieceModel, board);
		this.behavior = new HorizontalAndVerticalBehavior();
	}

}
