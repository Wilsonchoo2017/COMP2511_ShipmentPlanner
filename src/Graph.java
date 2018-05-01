import java.util.*;

public class Graph {
	private List<Node> listOfNodes;
	private int size;

	/**
	 * initialise the graph
	 */
	public Graph() {
		this.listOfNodes = new ArrayList<Node>();
		this.size = 0;
	}

	/**
	 * creates a new node and add it to the list of Nodes
	 * @param name
	 * @param refuellingTime
	 */
	public void addNode(String name, int refuellingTime) {
		Node n = new Node(name, refuellingTime);
		this.listOfNodes.add(n);
		this.size = size++;
	}
	
	/**
	 * @return size of graph
	 */
	public int getSize() {
		return size;
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
	 * Gets the weight of the edge
	 * @param from
	 * @param to
	 * @return -1 if no edge, the weight otherwise
	 */
	public int getWeight(Node from, Node to) {
		return from.getEdgeWeight(to);
	}
	
	/**
	 * Gets Refuelling Cost of a Node
	 */
	public int getRefuellingTime(Node node) {
		return node.getRefuellingTime();
	}
	
	
	/**
	 * Gets all nodes that have edges coming from 'from'
	 * @param from
	 * @return null if node does not exist
	 */
	public List<Node> getAdjacent(Node from){
		List<Node> listOfAdjacentNodes = from.getNeighborNode();
		if (listOfAdjacentNodes.isEmpty()) return null;
		return listOfAdjacentNodes;
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
 * find the nodes that contains Edge e 
 *  then get's the node's refuelling time
 * @param e
 * @param node
 * @return 
 */
	
	public int getEdgeRefuellingCost(Edge e, Boolean node) {	
		Node n = findNodeInEdge(e);
		Node wantedN = n.getNodeFromEdge(e, node);
		return wantedN.getRefuellingTime();
	}
	
	/**
	 * find the edge Source: fromNode to Destination: toNode
	 * and gets it's weight
	 * @param fromNode
	 * @param toNode
	 * @return Edge's Weight
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
	

}
