package GameElements.Behaviors;

import ChessGameKenai.Chess_Data;
import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class DiagnolBehavior implements Behavior {

	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		
		PurposedMoveResult result = new PurposedMoveResult();
		if(ChessGameUtils.isInGridBounds(newPosition))
		{
			Chess_Data data = Chess_Data.getChessData();
			result.setPieceSelected(data.isPieceSelectedAtPos(newPosition));
			
			ChessGamePoint piecePosition = pieceModel.getPosition();
			int xDiff = Math.abs(newPosition.x - piecePosition.x);
			int yDiff = Math.abs(newPosition.y - piecePosition.y);
			if(xDiff == yDiff)
			{
				result.setIsvalidResult(true);
			}
		}	
		return result;
	}
}
