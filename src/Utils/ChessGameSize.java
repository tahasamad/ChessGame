package Utils;

public class ChessGameSize {
	private int width;
	private int height;
	
	public ChessGameSize() {
		this.width = 0;
		this.height = 0;
	}
	
	public ChessGameSize(int width, int height) {
		this.width = width;
		this.height = width;
	}
	
	public ChessGameSize clone()
	{
		return new ChessGameSize(this.width, this.height);
	}
	
	public int getWidth ()
	{
		return width;
	}
	
	public int getHeight ()
	{
		return height;
	}
}
