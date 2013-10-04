package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class HorizontalBehavior implements Behavior {
	
	private Behavior basicBehavior = new BasicBehavior();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		if( (ChessGameUtils.isInGridBounds(newPosition)) && newPosition.y == currentPosition.y && newPosition.x != newPosition.y)
		{
			this.basicBehavior.purposeMove(currentPosition, newPosition, piece);
			return true;
		}
		return false;
	}

}
