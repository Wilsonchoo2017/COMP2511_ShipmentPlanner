import java.util.*;

public class Graph {
	private List<Node> listOfNodes;

	
	/**
	 * initialise the graph
	 */
	public Graph() {
		this.listOfNodes = new ArrayList<Node>();
	}

	
	/**
	 * creates a new node and add it to the list of Nodes
	 * @param name
	 * @param refuellingTime
	 */
	public void addNode(String name, int refuellingTime) {
		Node n = new Node(name, refuellingTime);
		this.listOfNodes.add(n);
	}
	
	
	/**
	 * add edge between to and from.
	 * addEdge on both node to ensure it is undirected.
	 * @param from
	 * @param to
	 * @param weight
	 */
	public void addEdge(String from, String to, int weight) {
		Node fromNode = findNode(from);
		Node toNode = findNode(to);
		fromNode.addEdge(toNode, weight);
		toNode.addEdge(fromNode, weight);
	}
	
	
	/**
	 * Gets Refuelling Cost of a Node
	 */
	public int getRefuellingTime(Node node) {
		return node.getRefuellingTime();
	}
	
	
	/**
	 * @param Node's Name
	 * @return Node that matches the name
	 */
	public Node findNode(String from) {
		for(Node n : listOfNodes) if (n.getName().equals(from)){
			return n;
		}
		return null;
	}
	
	
	/**
	 * @param node
	 * @return node's name
	 */
	public String getNodeName(Node node) {
		return node.getName();
	}

	
	/**
	 * find the edge Source: fromNode to Destination: toNode
	 * @param fromNode
	 * @param toNode
	 * @return
	 */
	public Edge findEdge(Node fromNode, Node toNode) {
		if(fromNode == null || toNode == null) return null;
		return fromNode.findEdge(toNode);
	}
	
	
	/**
	 * @param e
	 * @return node that contains edge e
	 */
	public Node findNodeInEdge(Edge e) {
		for(Node n: listOfNodes) {
			for(Edge nEdge :n.getListOfEdges()) if(e.equals(nEdge)){
				return n;
			}
		}
		return null;
	}
	
	
	/**
	 * Gets the weight of the edge
	 * @param from
	 * @param to
	 * @return -1 if no edge, the weight otherwise
	 */
	public int getEdgeWeight(Node fromNode, Node toNode) {
		return fromNode.getEdgeWeight(toNode);
	}

	
	/**
	 * @return all the nodes exist in the graph
	 */
	public List<Node> getListOfNodes() {
		return listOfNodes;
	}
	
	
	/**
	 * Using the parameter e, we find the edge
	 * @param e
	 * @return edge's from node
	 */
	public Node edgeFrom(Edge e) {
		return findNodeInEdge(e);
	}
	
	
	/**
	 * Using the parameter e, we find the edge
	 * @param e
	 * @return edge's to node
	 */
	public Node edgeTo(Edge e) {
		Node n = findNodeInEdge(e);
		return n.getNodeFromEdge(e, false);
	}

}
