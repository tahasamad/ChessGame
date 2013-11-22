package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepVerticalBehavior implements Behavior{

	VerticalBehavior verticalBehavior = new VerticalBehavior();
	
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		int diffY = Math.abs(currentPosition.y - newPosition.y);
		if(diffY == 1)
		{
			BehaviorResult result = this.verticalBehavior.proposeMove(currentPosition, newPosition, piece);
			if(result != null)
			{
				return result;
			}
		}
		return null;
	}

}
