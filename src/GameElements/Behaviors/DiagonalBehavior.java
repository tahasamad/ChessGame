package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class DiagonalBehavior implements Behavior {
	
	private Behavior basicBehavior = new NonJumpableBasicBehavior();
	
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		if(ChessGameUtils.isInGridBounds(newPosition))
		{
			int xDiff = Math.abs(newPosition.x - currentPosition.x);
			int yDiff = Math.abs(newPosition.y - currentPosition.y);
			if(xDiff == yDiff)
			{
				return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
			}
		}	
		return null;
	}
}
