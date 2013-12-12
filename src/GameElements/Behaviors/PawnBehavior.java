package GameElements.Behaviors;

import ChessGameKenai.ChessData;
import ChessGameKenai.SquareModel;
import GameElements.ElementColor;
import GameElements.NonVisualPiece;
import GameElements.Piece;
import Utils.ChessGamePoint;

public class PawnBehavior implements Behavior{
	private VerticalBehavior verticalBehavior = new VerticalBehavior();
	private OneStepDiagonalBehavior oneStepDiagonalBehavior = new OneStepDiagonalBehavior();
	@Override
	public BehaviorResult proposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		ChessData data = ChessData.getChessData();
		NonVisualPiece pieceModel = piece.getPieceModel();
		int diffY = newPosition.getY() - currentPosition.getY();
		BehaviorResult retVal = null;
		if(data.posHasPiece(newPosition))
		{
			if(isPieceColorWhite(pieceModel))
			{
				if(diffY < 0)
				{
					retVal = this.oneStepDiagonalBehavior.proposeMove(currentPosition, newPosition, piece);
				}
			}
			else
			{
				if(diffY > 0)
				{
					retVal = this.oneStepDiagonalBehavior.proposeMove(currentPosition, newPosition, piece);
				}
			}
		}
		else
		{
			//////Normal Move////////
			if(isPieceColorWhite(pieceModel))
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
			if(retVal == null)
			{
				if(isPieceColorWhite(pieceModel))
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
		if(retVal != null)
		{
			int yPos = this.getYPosForPromotion(pieceModel);
			if(yPos == newPosition.getY())
			{
				pieceModel.isQueenFromPawn(true);
				piece.updateView();
				SquareModel pawnToQueenSquareModel = data.getSquareModel(newPosition.getX(), newPosition.getY());
				pawnToQueenSquareModel.setPiece(piece);
				if(!retVal.getSquareModels().contains(pawnToQueenSquareModel))
				{
					retVal.getSquareModels().add(pawnToQueenSquareModel);
				}
			}
		}
		return retVal;
	}
	
	private BehaviorResult normalMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece)
	{
		ChessData data = ChessData.getChessData();
		NonVisualPiece pieceModel = piece.getPieceModel();
		int diffY = newPosition.getY() - currentPosition.getY();
		int range = 1;
		if(!pieceModel.isMoved())
		{
			range = 2;
		}
		int adsDiffY = Math.abs(diffY);
		if(adsDiffY > 0 && adsDiffY <= range)
		{
			BehaviorResult result = this.verticalBehavior.proposeMove(currentPosition, newPosition, piece);
			if(result != null)
			{
				if(adsDiffY == 2)
				{
					data.setEnPessant(pieceModel.getColor(), newPosition.clone());
				}
				return result;
			}
		}
		return null;
	}
	
	private BehaviorResult enPassantMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece, ChessGamePoint enPassantPoint, int diff)
	{
		ChessData data = ChessData.getChessData();
		if(enPassantPoint != null)
		{
			if(newPosition.getX() == enPassantPoint.getX() && newPosition.getY() == (enPassantPoint.getY() + diff))
			{
				BehaviorResult result = this.oneStepDiagonalBehavior.proposeMove(currentPosition, newPosition, piece);
				if(result != null)
				{
					SquareModel enPassantSquareModel = data.getSquareModel(enPassantPoint.getX(), enPassantPoint.getY());
					NonVisualPiece enPassantPieceModel = enPassantSquareModel.getPiece().getPieceModel();
					enPassantPieceModel.setIsCaptured(true);
					data.addToCapturedPieces(enPassantPieceModel);
					enPassantSquareModel.setPiece(null);
					result.getSquareModels().add(enPassantSquareModel);
					return result;
				}
			}
		}
		return null;
	}
	
	private int getYPosForPromotion(NonVisualPiece pieceModel)
	{
		if(isPieceColorWhite (pieceModel))
		{
			return 0;
		}
		else
		{
			return (ChessData.getChessData().getDimension() - 1);
		}
	}
	
	private boolean isPieceColorWhite (NonVisualPiece pieceModel)
	{
		return (pieceModel.getColor() == ElementColor.White);
	}
	
}
