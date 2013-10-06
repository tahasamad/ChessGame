package GameElements.Behaviors;

import ChessGameKenai.Chess_Data;
import ChessGameKenai.SquareModel;
import GameElements.ElementColor;
import GameElements.Non_Visual_Piece;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class PawnBehavior implements Behavior{
	private VerticalBehavior verticalBehavior = new VerticalBehavior();
	private OneStepDiagonalBehavior oneStepDiagonalBehavior = new OneStepDiagonalBehavior();
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		Chess_Data data = Chess_Data.getChessData();
		Non_Visual_Piece pieceModel = piece.getPieceModel();
		int diffY = newPosition.y - currentPosition.y;
		boolean retVal = false;
		if(data.posHasPiece(newPosition))
		{
			if(pieceModel.getColor() == ElementColor.White)
			{
				if(diffY < 0)
				{
					retVal = this.oneStepDiagonalBehavior.purposeMove(currentPosition, newPosition, piece);
				}
			}
			else
			{
				if(diffY > 0)
				{
					retVal = this.oneStepDiagonalBehavior.purposeMove(currentPosition, newPosition, piece);
				}
			}
		}
		else
		{
			//////Normal Move////////
			if(pieceModel.getColor() == ElementColor.White)
			{
				if(diffY < 0)//Correct Direction
				{
					retVal = this.normalMove(currentPosition, newPosition, piece);
				}
			}
			else
			{
				if(diffY > 0)//Correct Direction
				{
					retVal = this.normalMove(currentPosition, newPosition, piece);
				}
			}
			///////EnPassant Move/////////
			if(!retVal)
			{
				if(pieceModel.getColor() == ElementColor.White)
				{
					if(diffY < 0)
					{
						ChessGamePoint enPassantPoint = data.getEnPessant(ElementColor.Black);			
						retVal = this.enPassantMove(currentPosition, newPosition, piece, enPassantPoint, -1);
					}
				}
				else
				{	
					if(diffY > 0)
					{
						ChessGamePoint enPassantPoint = data.getEnPessant(ElementColor.White);
						retVal = this.enPassantMove(currentPosition, newPosition, piece, enPassantPoint, +1);
					}
				}
			}
		}
		if(retVal)
		{
			int yPos = this.getYPosForPromotion(pieceModel.getColor());
			if(yPos == newPosition.y)
			{
				pieceModel.isQueenFromPawn(true);
				piece.updateView();
			}
		}
		return retVal;
	}
	
	private boolean normalMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		Chess_Data data = Chess_Data.getChessData();
		Non_Visual_Piece pieceModel = piece.getPieceModel();
		int diffY = newPosition.y - currentPosition.y;
		int range = 1;
		if(!pieceModel.isMoved())
		{
			range = 2;
		}
		int adsDiffY = Math.abs(diffY);
		if(adsDiffY > 0 && adsDiffY <= range)
		{
			if(this.verticalBehavior.purposeMove(currentPosition, newPosition, piece))
			{
				if(adsDiffY == 2)
				{
					data.setEnPessant(pieceModel.getColor(), newPosition.clone());
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean enPassantMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece, ChessGamePoint enPassantPoint, int diff)
	{
		Chess_Data data = Chess_Data.getChessData();
		if(enPassantPoint != null)
		{
			if(newPosition.x == enPassantPoint.x && newPosition.y == (enPassantPoint.y + diff))
			{
				if(this.oneStepDiagonalBehavior.purposeMove(currentPosition, newPosition, piece))
				{
					SquareModel enPassantSquareModel = data.getSquareModel(enPassantPoint.x, enPassantPoint.y);
					Non_Visual_Piece enPassantPieceModel = enPassantSquareModel.getPiece().getPieceModel();
					enPassantPieceModel.setIsCaptured(true);
					data.addToCapturedPieces(enPassantPieceModel);
					enPassantSquareModel.setPiece(null);
					//Not a wining move
					data.notifyView();
					return true;
				}
			}
		}
		return false;
	}
	
	private int getYPosForPromotion(ElementColor color)
	{
		if(color == ElementColor.White)
		{
			return 0;
		}
		else
		{
			return (Chess_Data.getChessData().getDimension() - 1);
		}
	}
	
}
