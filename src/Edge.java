import java.util.*;

public class Edge implements Cloneable{
	private Node from;
	private Node to;
	private int weight;
	
	public Edge(Node from, Node to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	
	/**
	 * 
	 * @param node
	 * @return either from node or to node
	 */
	public Node getAdjacent(Boolean node) {		
		return node.equals(true) ? from : to;
	}

	
	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	public int getWeight() {
		return this.weight;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Edge) {
			Edge another = (Edge) o;
			return this.equals(another.from) && this.equals(another.to) && this.equals(another.weight);
		}
		return false;
	}
	
}
