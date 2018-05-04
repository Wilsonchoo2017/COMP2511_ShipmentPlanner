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
	/**
	 * @param to
	 * @return the weight of edge.
	 */
	public int getEdgeWeight(Node to) {
		for(Edge e :  listOfEdges) if(this.equals(e.getFrom()) && to.equals(e.getTo())){
			return e.getWeight();
		}
		return -1;
	}
	

	/**
	 * find the edge from this to dest
	 * @param to
	 * @return null if cant found, otherwise the edge
	 */
	public Edge findEdge(Node to) {
		for (Edge e: listOfEdges) if (e.getFrom().equals(this) && e.getTo().equals(to)) {
			return e;
		}
		return null;
	}
	
	/**
	 * get the adjacent node of the Edge e
	 * if node = true, return to
	 * if node = false, return from
	 * @param e
	 * @param node
	 * @return returns the adjacent node
	 */
	public Node getNodeFromEdge(Edge e, Boolean node) {
		return e.getAdjacent(node);
	}
	
	//debugging
	public List<Edge> getListOfEdges() {
		return listOfEdges;
	}
	
	/**
	 * name is unique, (from the spec)
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
	/**
	 * prints node's name
	 */
	public String toString() {
		return  this.name;
	}
}
