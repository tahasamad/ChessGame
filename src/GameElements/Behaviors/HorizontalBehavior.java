package GameElements.Behaviors;

import ChessGameKenai.Chess_Data;
import ChessGameKenai.PurposedMoveResult;
import ChessGameKenai.Square;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class HorizontalBehavior implements Behavior {

	
	
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		
		PurposedMoveResult result = new PurposedMoveResult();
		if( (ChessGameUtils.isInGridBounds(newPosition)) && newPosition.x == pieceModel.getPosition().x)
		{
			Chess_Data data = Chess_Data.getChessData();
			result.setHasKilled(data.isPieceSelectedAtPos(newPosition));
			result.setIsValidMove(true);
		}
		
		return result;
	}

}
