package GameElements.Behaviors;

import ChessGameKenai.PurposedMoveResult;
import GameElements.Non_Visual_Piece;
import Utils.ChessGamePoint;

public class OneStepHorizontalBeahvior implements Behavior {
	
	HorizontalBehavior horizontalBehavior;
	
	public OneStepHorizontalBeahvior() {
		horizontalBehavior = new HorizontalBehavior();
	}
	@Override
	public PurposedMoveResult purposeMove(ChessGamePoint newPosition,
			Non_Visual_Piece pieceModel) {
		PurposedMoveResult result = null;
		PurposedMoveResult horizontalMoveResult = horizontalBehavior.purposeMove(newPosition, pieceModel);
		int nextXPos = newPosition.x+ 1;
		int pieceNextXPos = pieceModel.getPosition().x + 1;
		if(horizontalMoveResult.isValidMove() && nextXPos == pieceNextXPos)
		{
			result = horizontalMoveResult;
		}
		
		return result;
	}

}
