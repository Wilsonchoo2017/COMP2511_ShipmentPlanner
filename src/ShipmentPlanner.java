import java.io.*;
import java.util.*;

/**
 * Using A* algo and find the shortest path to complete the required shipments.
 * @author Choo Yee Hang z5157656
 */
public class ShipmentPlanner implements Cloneable{
	private ArrayList<Edge> listOfShipment = new ArrayList<Edge>();
	private Graph g = new Graph();
	private int nodesExpanded = 0;
	HeuristicSearchStrategy HSS;
	
	public static void main(String[] args) {
		ShipmentPlanner system = new ShipmentPlanner();
		Scanner sc = null;
	    try
	    {
	    	sc = new Scanner(new File(args[0]));    // args[0] is the first command line argument
	        // Read input from the scanner here
	        while (sc.hasNextLine()) {
	        	String i = sc.nextLine();
	            String[] arg = i.trim().split("\\s+");
	                /*
	                 * Creates Arraylist that doesn't have any Comments
	                 * Then saves into arg
	                 */
	            ArrayList<String> noComment = new ArrayList<String>();
	            for(String index :arg) {
	            	if(index.equals("#")) break;
	            		noComment.add(index);
	            }
	            arg = new String[noComment.size()];
	            noComment.toArray(arg);
		        if (arg.length == 0) continue;
		        system.executeCommand(arg);
		            
	          }      
	          
	          //start find path
	          
	          Node start = system.g.findNode("Sydney");


	          List<Edge> path = system.findPath(start);
	          system.printOutput(path);
	     }
	     
	     catch (FileNotFoundException e)
	     {
	         System.out.println(e.getMessage());
	     }
	     finally
	     {
	         if (sc != null) sc.close();
	     }

	}
	
	/**
	 * a method that execute the 3 commands from input: "Refuelling, Time and Shipment"
	 * refueling - adds node
	 * time - adds edge
	 * shipment - finds the edge and add it to the arraylist of listOfShipment
	 * @param arg
	 */
	public void executeCommand(String[] arg) {
		switch(arg[0]) {
			case "Refuelling":
				g.addNode(arg[2], Integer.parseInt(arg[1]));
				break;
			case "Time":
				if(g.findNode(arg[2]) == null || g.findNode(arg[3]) == null ) {
					return;
				}
				g.addEdge(arg[2], arg[3], Integer.parseInt(arg[1]));
				break;
			case "Shipment" :
				Node from = g.findNode(arg[1]);
				Node to = g.findNode(arg[2]);
				Edge e = g.findEdge(from, to);
				listOfShipment.add(e);
				break;
		}
	}
	
	/**
	 * this method handles the output that needs to be printed out.
	 * No other place in the project should print.
	 * @param path
	 */
	public void printOutput(List<Edge> path) {
		System.out.println(nodesExpanded + " nodes expanded");
		if(path == null) {
			return;
		}
		int cost = calculateGCost(path);
		System.out.println("cost = " + cost);
		for(Edge e: path) {
			System.out.println("Ship " + g.edgeFrom(e) + " to " + g.edgeTo(e));
		}
	}
	
	/**
	 * Uses A* algo to find the optimal path;
	 * @param start
	 * @return ArrayList<Edge> path
	 */
	public List<Edge> findPath(Node start) {		
		Comparator<Trip> fScorecomparator = new Comparator<Trip>() {
            @Override
            public int compare(Trip o1, Trip o2) {
                if (o1.getTripFCost() < o2.getTripFCost())   return -1;
                if (o1.getTripFCost() > o2.getTripFCost())   return 1;
                return 0;
            }
        };

        //openSET
		final PriorityQueue<Trip> PQTrip = new PriorityQueue<Trip>(fScorecomparator);
		//closeSET
		Set<Trip> exploredTrips = new HashSet<Trip>();

		//put starting node into queue
		int GCost = calculateGCost(null);
		int FCost = calculateFCost(GCost, listOfShipment);
		Trip newT = new Trip(listOfShipment, null, GCost, FCost, null,start, null);
		PQTrip.add(newT);
		exploredTrips.add(newT);


		while(!PQTrip.isEmpty()){
			Trip current = PQTrip.poll();
			Node currentNode = current.getTo();
			nodesExpanded++;
			if(!PQTrip.isEmpty()) {
				for(Edge e: current.getTripPath()) if(current.getListOfUncompletedShipment().contains(e)){
					current.removeUncompletedShipment(e);
				}
				exploredTrips.add(current);
			}
			
			for(Edge e: current.getListOfUncompletedShipment()) {
				Node shipmentFrom = g.edgeFrom(e);
				Node shipmentTo = g.edgeTo(e);
				List<Edge> currentToShipment = new ArrayList<Edge>();
				if(!g.getNodeName(currentNode).equals(g.getNodeName(shipmentFrom)) ) {
					Edge currentToFrom = g.findEdge(currentNode, shipmentFrom);
					currentToShipment.add(currentToFrom);
				}
				currentToShipment.add(e);
				
				ArrayList<Edge> clone = (ArrayList<Edge>) current.getListOfUncompletedShipment().clone();
				GCost = calculateGCost(reorderPath(current)) + calculateGCost(currentToShipment);
				FCost = calculateFCost(GCost, clone);
				newT = new Trip(clone, currentToShipment, GCost, FCost,currentNode, shipmentTo, current);
						
				if(exploredTrips.contains(newT) && current.getTripFCost() >= FCost) {
					continue;
				} else if(!PQTrip.contains(newT)|| current.getTripFCost() < FCost){
					if(PQTrip.contains(newT)) {
						PQTrip.remove(newT);
					}
					PQTrip.add(newT);
				}		
			}
			if(current.getListOfUncompletedShipment().size() == 0) {
				return reorderPath(current); 
			}
		}
			
		return null; 
			
	}
	
	/**
	 * reverse the path using collections.reverse(path)
	 * @param current
	 * @return reversedPath
	 */
	public List<Edge> reorderPath(Trip current) {
		List<Edge> path = new ArrayList<Edge>();
		while(current.getCameFrom() != null) {
			for(int i = current.getTripPath().size() - 1; i >= 0 ; i-- ) {
				path.add(current.getTripPath().get(i));
			}
			current = current.getCameFrom();
		}
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * 
	 * @param tripPath
	 * @return g cost for the tripPath
	 */
	public int calculateGCost(List<Edge> tripPath) {
		int cost = 0;
		if(tripPath == null) return 0;
		for(Edge e: tripPath) {
			Node from = g.edgeFrom(e);
			Node to = g.edgeTo(e);
			cost = cost + from.getEdgeWeight(to) + from.getRefuellingTime();
		}
		return cost;
	}

	/**
	 * uses heuristic defined in teh heuristic class
	 * @param GCost
	 * @param listOfUncompletedShipment
	 * @return gCost + hCost
	 */
	public int calculateFCost(int GCost, ArrayList<Edge> listOfUncompletedShipment) {
		HSS = new CalculateUsingNumberOfGoal();
		return GCost + HSS.calculateHeuristicValue(listOfUncompletedShipment, g);
	}
	
}