package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepVerticalBehavior implements Behavior{

	VerticalBehavior verticalBehavior = new VerticalBehavior();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		int diffY = Math.abs(currentPosition.y - newPosition.y);
		if(diffY == 1)
		{
			if(this.verticalBehavior.purposeMove(currentPosition, newPosition, piece))
			{
				return true;
			}
		}
		return false;
	}

}
