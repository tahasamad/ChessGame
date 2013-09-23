package Utils;

public class ChessGameSize {
	public int width;
	public int height;
	
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
}
