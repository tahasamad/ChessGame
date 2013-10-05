package GameElements.Behaviors;

import java.awt.Container;

import ChessGameKenai.Chess_Data;
import ChessGameKenai.SquareModel;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class BasicBehavior implements Behavior{
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		Chess_Data data = Chess_Data.getChessData();
		if(data.posHasPiece(newPosition))
		{
			Piece killedPiece = data.getSquareModel(newPosition).getPiece();
			Non_Visual_Piece killedPieceModel = killedPiece.getPieceModel();
			killedPieceModel.setIsCaptured(true);
			data.addToCapturedPieces(killedPieceModel);
		}
		SquareModel squareModel2 = data.getSquareModel(newPosition);
		squareModel2.setPiece(piece);
		SquareModel square1 = data.getSquareModel(currentPosition);
		square1.setPiece(null);
		data.notifyView();
		return true;
	}
}
