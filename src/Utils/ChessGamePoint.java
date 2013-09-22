package Utils;

public class ChessGamePoint {
	public int x;
	public int y;
	
	public ChessGamePoint()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public ChessGamePoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public ChessGamePoint clone()
	{
		return new ChessGamePoint(this.x, this.y);
	}

}
