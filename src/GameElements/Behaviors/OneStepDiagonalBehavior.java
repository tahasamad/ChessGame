package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepDiagonalBehavior implements Behavior{

	private Behavior basicBehavior = new NonJumpableBasicBehavior();

	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		if(isDiagonalMovement(currentPosition, newPosition))
		{
			return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
		}
		return null;
	}
	
	private boolean isDiagonalMovement (ChessGamePoint currentPosition, ChessGamePoint newPosition)
	{
		int diffX = Math.abs(currentPosition.getX() - newPosition.getX());
		int diffY = Math.abs(currentPosition.getY() - newPosition.getY());
		return diffX == 1 && diffY == 1;
	}

}
