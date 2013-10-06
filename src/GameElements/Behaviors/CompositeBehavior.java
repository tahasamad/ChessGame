package GameElements.Behaviors;

import java.util.ArrayList;
import java.util.Iterator;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class CompositeBehavior implements Behavior{

	private ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
	
	@Override
	public BehaviorResult purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		Iterator<Behavior> it = this.behaviors.iterator();
		while(it.hasNext())
		{
			Behavior behavior = it.next();
			BehaviorResult result = behavior.purposeMove(currentPosition, newPosition, piece);
			if(result != null)
			{
				return result;
			}
		}
		return null;
	}
	
	public void addBehavior(Behavior behavior) {
		if(!this.behaviors.contains(behavior))
		{
			this.behaviors.add(behavior);
		}
	}
	
	public void removeBehavior(Behavior behavior) {
		this.behaviors.remove(behavior);
	}
	
}
