package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepHorizontalBeahvior implements Behavior {
	
	HorizontalBehavior horizontalBehavior = new HorizontalBehavior();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		int diffX = Math.abs(currentPosition.x - newPosition.x);
		if(diffX == 1)
		{
			if(this.horizontalBehavior.purposeMove(currentPosition, newPosition, piece))
			{
				return true;
			}
		}
		return false;
	}

}
