package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class KnghtBehavior implements Behavior{
	
	private Behavior basicBehavior = new BasicBehavior();

	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		int absDiffX = Math.abs(newPosition.x - currentPosition.x);
		int absDiffY = Math.abs(newPosition.y - currentPosition.y);
		if((absDiffX == 2 && absDiffY == 1) || (absDiffX == 1 && absDiffY == 2))
		{
			return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
		}
		return null;
	}
	
}
