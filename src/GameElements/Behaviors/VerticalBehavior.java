package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class VerticalBehavior implements Behavior {

	private Behavior basicBehavior = new NonJumpableBasicBehavior();
	
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{	
		if((ChessGameUtils.isInGridBounds(newPosition)) && newPosition.getX() == currentPosition.getX() && newPosition.getY() != currentPosition.getY())
		{
			return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
		}
		return null;
	}

}
