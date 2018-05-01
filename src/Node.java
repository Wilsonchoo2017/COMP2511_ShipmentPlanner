import java.util.*;

public class Node {
	private List<Edge> listOfEdges;
	private String name;
	private int refuellingTime;
	
	/**
	 * constructor for Node
	 * @param name is unique to the node
	 * @param refuellingTime From spec
	 * Other information should be added later
	 */
	public Node(String name, int refuellingTime) {
		this.name = name;
		this.refuellingTime = refuellingTime;

		this.listOfEdges = new ArrayList<Edge>();
	}
	
	public String getName() {
		return this.name;
	}
	public int getRefuellingTime() {
		return this.refuellingTime;
	}

	/**
	 * adds edge into the List neighborEdge
	 * if edge has already existed, then skip
	 * @param edge
	 */
	public void addEdge(Node to, int weight) {
		Edge edge = new Edge(this, to, weight);
		if(this.listOfEdges.contains(edge)) {
			return;
		}
		this.listOfEdges.add(edge);
	}
	
	public int getEdgeWeight(Node to) {
		for(Edge e :  listOfEdges) if(this.equals(e.getFrom()) && to.equals(e.getTo())){
			return e.getWeight();
		}
		return -1;
	}
	
	public List<Node> getNeighborNode() {
		List<Node> listOfNeighborNode = new ArrayList<Node>();
		for(Edge e: listOfEdges) {
			listOfNeighborNode.add(e.getTo());
		}
		return listOfNeighborNode;
	}

	public Edge findEdge(Node to) {
		for (Edge e: listOfEdges) if (e.getFrom().equals(this) && e.getTo().equals(to)) {
			return e;
		}
		return null;
	}
	
	public Node getNodeFromEdge(Edge e, Boolean node) {
		return e.getAdjacent(node);
	}
	
	//debugging
	public List<Edge> getListOfEdges() {
		return listOfEdges;
	}
	
	/**
	 * name is unique, (from the spec)
	 * 
	 */
	@Override
	public boolean equals(Object  o) {
		if ( o == this) return true;
		if (getClass().equals(o.getClass())) {
			Node another = (Node) o;
			return this.name.equals(another.name) && this.refuellingTime == another.refuellingTime;
		}
		return false;
	}	
	
	public String toString() {
		return  this.name;
	}
	/* debuging
	public String toString() {
		return "Name: " + this.name + " Refuelling Time: " + this.refuellingTime + " List of Edges are: " +
				listOfEdges;
	}*/ 

}
