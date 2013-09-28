package Utils;

public class ChessGamePoint {
	public int x;
	public int y;
	
	public ChessGamePoint()
	{
		this.x = -1;
		this.y = -1;
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