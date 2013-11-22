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
			int xDiff = Math.abs(newPosition.getX() - currentPosition.getX());
			int yDiff = Math.abs(newPosition.getY() - currentPosition.getY());
			if(xDiff == yDiff)
			{
				return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
			}
		}	
		return null;
	}
}
