import java.util.*;

public interface HeuristicSearchStrategy {
	public int calculateHeuristicValue(ArrayList<Edge> listOfShipment, Graph g);
	
}
/**
 * this heuristic basically just gets the least cost of both edge's weight and the node refuelling cost
 * this is admissable as the path needs to go through every shipment in the arraylist at least once. 
 * therefore getting the least cost will always be lower than the actual cost.
 * 
 */
class CalculateUsingNumberOfGoal implements HeuristicSearchStrategy{
	
	public int calculateHeuristicValue(ArrayList<Edge> listOfShipment, Graph g) {
		int WeightMinValue = Integer.MAX_VALUE;
		int refuellingMinValue = Integer.MAX_VALUE;
		
		for(Edge e: listOfShipment) {
			if(refuellingMinValue > g.getEdgeRefuellingCost(e, true)) {
				refuellingMinValue = g.getEdgeRefuellingCost(e, true);
			}
			if(WeightMinValue > g.getEdgeWeight(g.edgeFrom(e), g.edgeTo(e)))  {
				WeightMinValue =  g.getEdgeWeight(g.edgeFrom(e), g.edgeTo(e));
			}
		}
		return  (WeightMinValue + refuellingMinValue) * listOfShipment.size();
	}
}
