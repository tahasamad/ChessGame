package GameElements.Behaviors;

import ChessGameKenai.ChessData;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class NonJumpableBasicBehavior implements Behavior {
	private Behavior behavior = new BasicBehavior();
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		ChessData data = ChessData.getChessData();
		int diffX = newPosition.getX() - currentPosition.getX();
		int diffY = newPosition.getY() - currentPosition.getY();
		int dux = diffX != 0 ? diffX / Math.abs(diffX) : 0;
		int duy = diffY != 0 ? diffY / Math.abs(diffY) : 0;
		int x = currentPosition.getX() + dux;
		int y = currentPosition.getY() + duy;
		for(;(x != newPosition.getX() || y != newPosition.getY()); x += dux, y += duy)
		{
			if(data.posHasPiece(new ChessGamePoint(x, y)))
			{
				return null;
			}
		}
		return this.behavior.proposeMove(currentPosition, newPosition, piece);
	}

}
