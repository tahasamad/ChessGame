package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class KnightBehavior implements Behavior {

	private Behavior basicBehavior = new BasicBehavior();

	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		int absDiffX = Math.abs(newPosition.getX() - currentPosition.getX());
		int absDiffY = Math.abs(newPosition.getY() - currentPosition.getY());
		if ((absDiffX == 2 && absDiffY == 1) || (absDiffX == 1 && absDiffY == 2)) {
			return this.basicBehavior.proposeMove(currentPosition, newPosition, piece);
		}
		return null;
	}

}