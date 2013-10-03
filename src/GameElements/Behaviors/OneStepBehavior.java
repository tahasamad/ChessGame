package GameElements.Behaviors;

import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;

public class OneStepBehavior implements Behavior{

	OneStepVerticalBehavior oneStepVerticalBehavior;
	OneStepHorizontalBeahvior oneStepHorizontalBeahvior;
	
	public OneStepBehavior() {
		oneStepVerticalBehavior = new OneStepVerticalBehavior();
		oneStepHorizontalBeahvior = new OneStepHorizontalBeahvior();
	}
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		PurposedMoveResult oneStepVerticalResult = oneStepVerticalBehavior.purposeMove(newPosition, pieceModel);
		PurposedMoveResult oneStepHorizontalResult = oneStepHorizontalBeahvior.purposeMove(newPosition, pieceModel);
		PurposedMoveResult result = null;
		if(oneStepHorizontalResult.isValidMove())
		{
			result = oneStepHorizontalResult;
		}
		else if(oneStepVerticalResult.isValidMove())
		{
			result = oneStepVerticalResult;
		}
		return result;
	}
}
