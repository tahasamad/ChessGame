package Utils;

public class ChessGameRect {
	private ChessGamePoint point;
	private ChessGameSize size;

	
	public ChessGameRect() {
		this.point = new ChessGamePoint();
		this.size = new ChessGameSize();
	}

	public ChessGameRect(ChessGamePoint point, ChessGameSize size) {
		this.point = point;
		this.size = size;
	}
	
	public ChessGameRect(int x, int y, int width, int height) {
		this.point = new ChessGamePoint(x, y);
		this.size = new ChessGameSize(width, height);
	}
	
	public ChessGameRect clone() {
		return new ChessGameRect(this.point, this.size);
	}
	
	public ChessGamePoint getChessGamePoint ()
	{
		return point;
	}
	
	public ChessGameSize getChessGameSize ()
	{
		return size;
	}
}
