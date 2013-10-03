package GameElements.Behaviors;

import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;

public class OneStepVerticalBehavior implements Behavior{

	VerticalBehavior verticalBehavior;
	
	public OneStepVerticalBehavior() {
		verticalBehavior = new VerticalBehavior();
	}
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		PurposedMoveResult result = null;
		PurposedMoveResult verticalMoveResult = verticalBehavior.purposeMove(newPosition, pieceModel);
		int nextYPos = newPosition.y+ 1;
		int pieceNextYPos = pieceModel.getPosition().y + 1;
		if(verticalMoveResult.isValidMove() && nextYPos == pieceNextYPos)
		{
			result = verticalMoveResult;
		}
		
		return result;
	}

}
