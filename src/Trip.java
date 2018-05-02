import java.util.*;

/**
 * 
 * a state class to store information of the state
 */
public class Trip {
	private final ArrayList<Edge> uncompletedShipment;
	private final List<Edge> tripPath; 
	private final Trip cameFrom;
	private final int tripGCost; //what matters
	private final int tripFCost; // gcost + heuristic
	private final Node from;
	private final Node to;

	
	public List<Edge> getTripPath() {
		return tripPath;
	}

	public int getTripGCost() {
		return tripGCost;
	}

	public int getTripFCost() {
		return tripFCost;
	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	public Trip(ArrayList<Edge> uncompletedShipment, List<Edge> tripPath, int tripGCost, int tripFCost, Node from, Node to, Trip cameFrom) {
		this.tripPath = tripPath;
		this.tripGCost = tripGCost;
		this.tripFCost = tripFCost;
		this.from = from;
		this.to = to;
		this.uncompletedShipment = uncompletedShipment;
		this.cameFrom = cameFrom;
	}

	
	
	public void removeUncompletedShipment(Edge completedShipment) {
		uncompletedShipment.remove(completedShipment);
	}
	
	
	public ArrayList<Edge> getListOfUncompletedShipment() {
		return uncompletedShipment;
	}

	public Trip getCameFrom() {
		return cameFrom;
	}	
}
