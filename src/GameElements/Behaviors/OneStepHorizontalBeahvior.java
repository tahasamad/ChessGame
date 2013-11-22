package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepHorizontalBeahvior implements Behavior {
	
	HorizontalBehavior horizontalBehavior = new HorizontalBehavior();
	
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		int diffX = Math.abs(currentPosition.getX() - newPosition.getX());
		if(diffX == 1)
		{
			BehaviorResult result = this.horizontalBehavior.proposeMove(currentPosition, newPosition, piece);
			if(result != null)
			{
				return result;
			}
		}
		return null;
	}

}
