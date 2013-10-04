package GameElements.Behaviors;

import GameElements.Piece;
import Utils.ChessGamePoint;
import Utils.ChessGameUtils;

public class DiagnolBehavior implements Behavior {
	
	private Behavior basicBehavior = new BasicBehavior();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		if(ChessGameUtils.isInGridBounds(newPosition))
		{
			int xDiff = Math.abs(newPosition.x - currentPosition.x);
			int yDiff = Math.abs(newPosition.y - currentPosition.y);
			if(xDiff == yDiff)
			{
				this.basicBehavior.purposeMove(currentPosition, newPosition, piece);
				return true;
			}
		}	
		return false;
	}
}
