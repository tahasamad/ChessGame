package GameElements.Behaviors;

import java.util.ArrayList;

import ChessGameKenai.SquareModel;

public class BehaviorResult {
	
	private boolean validMove;
	private ArrayList<SquareModel> squareModels;
	
	public BehaviorResult(boolean validMove, ArrayList<SquareModel> squareModels) {
		this.validMove = validMove;
		this.squareModels = squareModels;
	}
	public boolean isValidMove() {
		return validMove;
	}
	
	public void setValidMove(boolean validMove) {
		this.validMove = validMove;
	}
	
	public ArrayList<SquareModel> getSquareModels() {
		return squareModels;
	}
	
	public void setSquareModels(ArrayList<SquareModel> squareModels) {
		this.squareModels = squareModels;
	}
	
}
