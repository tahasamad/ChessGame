package ChessGameKenai;

import GameElements.Piece;

public class ProposedMoveResult {
	
	boolean isValidMove = false, hasKilled = false;
	Piece killedPiece = null;
	
	public boolean hasKilled() {
		return hasKilled;
	}

	public void setHasKilled(boolean hasKilled) {
		this.hasKilled = hasKilled;
	}

	public boolean isValidMove() {
		return isValidMove;
	}

	public void setIsValidMove(boolean isValidMove) {
		this.isValidMove = isValidMove;
	}

	public Piece getKilledPiece() {
		return killedPiece;
	}

	public void setKilledPiece(Piece killedPiece) {
		this.killedPiece = killedPiece;
	}

	public ProposedMoveResult()
	{
		
	}
}
