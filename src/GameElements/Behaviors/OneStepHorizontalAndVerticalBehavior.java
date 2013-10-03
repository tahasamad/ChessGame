package GameElements.Behaviors;

import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;

public class OneStepHorizontalAndVerticalBehavior implements Behavior{

	OneStepBehavior oneStepBehavior;
	HorizontalAndVerticalBehavior horizontalAndVerticalBehavior;
	
	public OneStepHorizontalAndVerticalBehavior() {
		oneStepBehavior = new OneStepBehavior();
		horizontalAndVerticalBehavior = new HorizontalAndVerticalBehavior();
	}
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		PurposedMoveResult result = null;
		PurposedMoveResult oneStepResult = oneStepBehavior.purposeMove(newPosition, pieceModel);
		PurposedMoveResult horizontalAndVerticalResult = horizontalAndVerticalBehavior.purposeMove(newPosition, pieceModel);
		if(horizontalAndVerticalResult.isValidMove() && oneStepResult.isValidMove())
		{
			result = horizontalAndVerticalResult;
		}
		return result;
	}

}
