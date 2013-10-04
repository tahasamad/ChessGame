package GameElements.Behaviors;

import java.util.ArrayList;
import java.util.Iterator;

import GameElements.Piece;
import Utils.ChessGamePoint;

public class CompositeBehavior implements Behavior{

	private ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
	
	@Override
	public boolean purposeMove(ChessGamePoint currentPosition, ChessGamePoint newPosition, Piece piece) {
		Iterator<Behavior> it = this.behaviors.iterator();
		while(it.hasNext())
		{
			Behavior behavior = it.next();
			if(behavior.purposeMove(currentPosition, newPosition, piece))
			{
				return true;
			}
		}
		return false;
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
