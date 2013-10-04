package ChessGameKenai;

import GameElements.Piece;
import GameElements.PieceColor;
import Utils.ChessGamePoint;

public class SquareModel {
	
    private ChessGamePoint position;
    private PieceColor baseColor;
    private Piece piece;
    
    public SquareModel(ChessGamePoint position, PieceColor baseColor, Piece piece)
    {
    	if(position == null)
    	{
    		throw new Error("Can not accept parameter position = null");
    	}
    	this.position = position;
    	this.baseColor = baseColor;
    	this.piece = piece;
    }

	public ChessGamePoint getPosition() {
		return this.position.clone();
	}

	public PieceColor getBaseColor() {
		return this.baseColor;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
    
}
