import java.util.*;

public interface HeuristicSearchStrategy {
	public int calculateHeuristicValue(ArrayList<Edge> listOfShipment, Graph g);
	
}

class CalculateUsingNumberOfGoal implements HeuristicSearchStrategy{
	
	public int calculateHeuristicValue(ArrayList<Edge> listOfShipment, Graph g) {
		int WeightMinValue = Integer.MAX_VALUE;
		int refuellingMaxValue = 0;
		int refuellingMinValue = Integer.MAX_VALUE;
		
		for(Node x: g.getListOfNodes()) {
			for(Node y: x.getNeighborNode()) {
				
				if(WeightMinValue > g.getEdgeWeight(x, y)) {
					WeightMinValue = g.getEdgeWeight(x, y);
				}
			}
		}
		for(Edge e: listOfShipment) {
			if(refuellingMinValue > g.getEdgeRefuellingCost(e, true)) {
				refuellingMinValue = g.getEdgeRefuellingCost(e, true);
			}
			if(refuellingMaxValue < g.getEdgeRefuellingCost(e, true)) {
				refuellingMaxValue = g.getEdgeRefuellingCost(e, true);
			}
		}
		
		int refuellingDifference = Math.abs(refuellingMaxValue - refuellingMinValue);
		int ratio = 0;


	
		if( WeightMinValue < refuellingDifference && WeightMinValue != 0) {
			ratio = refuellingDifference/WeightMinValue;
			return  (WeightMinValue+ refuellingMinValue) * listOfShipment.size() / (ratio * listOfShipment.size());
		} else {
			if(refuellingDifference == 0) {
				return  (WeightMinValue + refuellingMinValue) * listOfShipment.size();
			}
			return  (WeightMinValue + refuellingMinValue) * listOfShipment.size();
		}
	}

	

}
