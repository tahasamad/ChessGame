package GameElements.Behaviors;

import java.util.ArrayList;

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
		ArrayList<SquareModel> updatedSquares = new ArrayList<SquareModel>();
		SquareModel square2;
		if(data.posHasPiece(newPosition))
		{
			Non_Visual_Piece killedPiece = data.getPieceModel(newPosition);
			killedPiece.setIsCaptured(true);
			square2 = data.getSquareModel(newPosition);
			square2.setPiece(piece);
			updatedSquares.add(square2);
			//Notify killedPiece
		}
		SquareModel square1 = data.getSquareModel(currentPosition);
		square1.setPiece(null);
		updatedSquares.add(square1);
		if(updatedSquares.size() > 0)
		{
			//Notify
		}
		return true;
	}
}
