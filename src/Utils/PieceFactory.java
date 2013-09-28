package Utils;

import ChessGameKenai.Board;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import GameElements.Pieces.Bishop;
import GameElements.Pieces.King;
import GameElements.Pieces.Knight;
import GameElements.Pieces.Pawn;
import GameElements.Pieces.Queen;
import GameElements.Pieces.Rook;

public class PieceFactory {

	public void getPiece ()
	{
		
	}
	
	public Piece makePiece (Non_Visual_Piece activePieceModel, Board board)
	{
		Piece piece;
		if (activePieceModel != null) 
		{
        	//TODO: May be apply factory here.
        	switch (activePieceModel.getType())
        	{
            	case Pawn:
            		piece = new Pawn (activePieceModel, board);
            		break;
            	case Rook:
            		piece = new Rook (activePieceModel, board);
            		break;
            	case Bishop:
            		piece = new Bishop (activePieceModel, board);
            		break;
            	case King:
            		piece = new King (activePieceModel, board);
            		break;
            	case Knight:
            		piece = new Knight (activePieceModel, board);
            		break;
            	case Queen:
            		piece = new Queen (activePieceModel, board);
            		break;
            	default:
            		piece = new Piece(activePieceModel, board);
            		break;
            		
        		
        	}

            return piece;
		}
		return null;
	}
}
