package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class HorizontalBehavior implements Behavior {
	
	private Behavior basicBehavior = new NonJumpableBasicBehavior();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		if( (ChessGameUtils.isInGridBounds(newPosition)) && newPosition.y == currentPosition.y && newPosition.x != newPosition.y)
		{
			return this.basicBehavior.purposeMove(currentPosition, newPosition, piece);
		}
		return false;
	}

}
