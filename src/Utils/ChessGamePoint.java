package Utils;

import java.io.Serializable;

public class ChessGamePoint implements Serializable{
	private int x;
	private int y;
	
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
	
	public int getX ()
	{
		return x;
	}
	
	public int getY ()
	{
		return y;
	}

}
