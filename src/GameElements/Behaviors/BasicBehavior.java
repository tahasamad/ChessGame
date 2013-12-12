package GameElements.Behaviors;

import java.util.ArrayList;

import ChessGameKenai.ChessData;
import ChessGameKenai.SquareModel;
import GameElements.ElementColor;
import GameElements.NonVisualPiece;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class BasicBehavior implements Behavior{
	
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		ChessData data = ChessData.getChessData();
		if(data.posHasPiece(newPosition))
		{
			Piece killedPiece = data.getSquareModel(newPosition).getPiece();
			NonVisualPiece killedPieceModel = killedPiece.getPieceModel();
			killedPieceModel.setIsCaptured(true);
			data.addToCapturedPieces(killedPieceModel);
		}
		SquareModel squareModel2 = data.getSquareModel(newPosition);
		squareModel2.setPiece(piece);
		SquareModel squareModel1 = data.getSquareModel(currentPosition);
		squareModel1.setPiece(null);
		piece.getPieceModel().setIsMoved(true);
		ArrayList<SquareModel> list = new ArrayList<SquareModel>();
		list.add(squareModel1);//src
		list.add(squareModel2);//dst
		data.changeTurn();
		if(piece.getPieceModel().getColor() == ElementColor.White)
		{
			data.setEnPessant(ElementColor.Black, null);
		}
		else
		{
			data.setEnPessant(ElementColor.White, null);
		}
		return (new BehaviorResult(true, list));
	}
}
