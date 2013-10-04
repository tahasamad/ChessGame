package GameElements.Behaviors;

import GameElements.Non_Visual_Piece;

public class BehaviorFactory {
	
	public static Behavior makeBehaviorForType(Non_Visual_Piece model)
	{
		Behavior behavior;
		CompositeBehavior compositeBehavior;
		DiagnolBehavior diagnolBehavior;
		HorizontalBehavior horizontalBehavior;
		VerticalBehavior verticalBehavior;
		if (model != null) 
		{
        	switch (model.getType())
        	{
            	case Pawn:
            		horizontalBehavior = new HorizontalBehavior();
            		verticalBehavior = new VerticalBehavior();
            		compositeBehavior = new CompositeBehavior();
            		compositeBehavior.addBehavior(horizontalBehavior);
            		compositeBehavior.addBehavior(verticalBehavior);
            		behavior = compositeBehavior;
            		break;
            	case Rook:
            		horizontalBehavior = new HorizontalBehavior();
            		verticalBehavior = new VerticalBehavior();
            		compositeBehavior = new CompositeBehavior();
            		compositeBehavior.addBehavior(horizontalBehavior);
            		compositeBehavior.addBehavior(verticalBehavior);
            		behavior = compositeBehavior;
            		break;
            	case Bishop:
            		behavior = new DiagnolBehavior();
            		break;
            	case Knight:
            		behavior = new HorizontalBehavior();
            		break;
            	case King:
            		OneStepDiagonalBehavior oneStepDiagonalBehavior = new OneStepDiagonalBehavior();
            		OneStepHorizontalBeahvior oneStepHorizontalBehavior = new OneStepHorizontalBeahvior();
            		OneStepVerticalBehavior oneStepVerticalBehavior = new OneStepVerticalBehavior();
            		compositeBehavior = new CompositeBehavior();
            		compositeBehavior.addBehavior(oneStepDiagonalBehavior);
            		compositeBehavior.addBehavior(oneStepHorizontalBehavior);
            		compositeBehavior.addBehavior(oneStepVerticalBehavior);
            		behavior = compositeBehavior;
            		break;
            	case Queen:
            		diagnolBehavior = new DiagnolBehavior();
            		horizontalBehavior = new HorizontalBehavior();
            		verticalBehavior = new VerticalBehavior();
            		compositeBehavior = new CompositeBehavior();
            		compositeBehavior.addBehavior(diagnolBehavior);
            		compositeBehavior.addBehavior(horizontalBehavior);
            		compositeBehavior.addBehavior(verticalBehavior);
            		behavior = compositeBehavior;
            		break;
            	default:
            		throw new Error("Can not determine types");
        	}
            return behavior;
		}
		return null;
	}
}