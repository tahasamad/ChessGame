package GameElements.Behaviors;

import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;

public class HorizontalAndVerticalBehavior implements Behavior {

	HorizontalBehavior horizontalBehavior;
	VerticalBehavior verticalBehavior;
	
	public HorizontalAndVerticalBehavior()
	{
		horizontalBehavior = new HorizontalBehavior();
		verticalBehavior = new VerticalBehavior();
	}
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		
		PurposedMoveResult horizontalMoveResult = horizontalBehavior.purposeMove(newPosition, pieceModel);
		PurposedMoveResult verticalMoveResult = verticalBehavior.purposeMove(newPosition, pieceModel);
		PurposedMoveResult result = null;
		if(horizontalMoveResult.isValidMove())
		{
			result = horizontalMoveResult;
		}
		else if(verticalMoveResult.isValidMove())
		{
			result = verticalMoveResult;
		}
		
		return result;
	}

}
