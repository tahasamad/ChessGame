package GameElements.Behaviors;

import ChessGameKenai.Chess_Data;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class NonJumpableBasicBehavior implements Behavior {
	private Behavior behavior = new BasicBehavior();
	@Override
	public BehaviorResult purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		Chess_Data data = Chess_Data.getChessData();
		int diffX = newPosition.x - currentPosition.x;
		int diffY = newPosition.y - currentPosition.y;
		int dux = diffX != 0 ? diffX / Math.abs(diffX) : 0;
		int duy = diffY != 0 ? diffY / Math.abs(diffY) : 0;
		int x = currentPosition.x + dux;
		int y = currentPosition.y + duy;
		for(;(x != newPosition.x || y != newPosition.y); x += dux, y += duy)
		{
			if(data.posHasPiece(new ChessGamePoint(x, y)))
			{
				return null;
			}
		}
		return this.behavior.purposeMove(currentPosition, newPosition, piece);
	}

}
