package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class OneStepDiagonalBehavior implements Behavior{

	private Behavior basicBehavior = new NonJumpableBasicBehavior();

	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		int diffX = Math.abs(currentPosition.getX() - newPosition.getX());
		int diffY = Math.abs(currentPosition.getY() - newPosition.getY());
		if(diffX == 1 && diffY == 1)
		{
			return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
		}
		return null;
	}

}
