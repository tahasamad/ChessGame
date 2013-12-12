package GameElements.Behaviors;

import GameElements.NonVisualPiece;

public class BehaviorFactory {
	
	public static Behavior makeBehaviorForType(NonVisualPiece model)
	{
		Behavior behavior;
		CompositeBehavior compositeBehavior;
		DiagonalBehavior diagnolBehavior;
		HorizontalBehavior horizontalBehavior;
		VerticalBehavior verticalBehavior;
		if (model != null) 
		{
        	switch (model.getType())
        	{
            	case Pawn:
            		behavior = new PawnBehavior();
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
            		behavior = new DiagonalBehavior();
            		break;
            	case Knight:
            		behavior = new KnightBehavior();
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
            		diagnolBehavior = new DiagonalBehavior();
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
