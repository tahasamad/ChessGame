package GameElements.Behaviors;

import ChessGameKenai.Chess_Data;
import ChessGameKenai.PurposedMoveResult;
import ChessGameKenai.Square;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class VerticalBehavior implements Behavior {

	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		PurposedMoveResult result = new PurposedMoveResult();
		if((ChessGameUtils.isInGridBounds(newPosition)) && newPosition.y == pieceModel.getPosition().y)
		{
			Chess_Data data = Chess_Data.getChessData();
			result.setPieceSelected(data.isPieceSelectedAtPos(newPosition));
			result.setIsvalidResult(true);
		}
		
		return result;
	}

}
