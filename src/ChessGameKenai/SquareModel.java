package ChessGameKenai;

import GameElements.Piece;
import GameElements.ElementColor;
import Utils.ChessGamePoint;

public class SquareModel {
	
    private ChessGamePoint position;
    private ElementColor baseColor;
    private Piece piece;
    
    public SquareModel(ChessGamePoint position, ElementColor baseColor, Piece piece)
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

	public ElementColor getBaseColor() {
		return this.baseColor;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
    
}
