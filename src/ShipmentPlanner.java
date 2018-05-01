import java.io.*;
import java.util.*;

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
		        system.excuteCommand(arg);
		            
	          }      
	          
	          //start debugging
//	          System.out.println(system.listOfShipment);
//	          for(Node n: system.g.getListOfNodes()) {
//		          System.out.println(n);
//		          for(Edge e: n.getListOfEdges()) {
//		        	  System.out.println(e);
//		          }
//	          }
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
	
	public void excuteCommand(String[] arg) {
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
	
	public void printOutput(List<Edge> path) {
		System.out.println(nodesExpanded + " nodes expanded");
		int cost = calculateGCost(path);
		System.out.println("cost = " + cost);
		for(Edge e: path) {
			System.out.println("Ship " + e.getFrom() + " to " + e.getTo());
		}
	}
	
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
		PriorityQueue<Trip> PQTrip = new PriorityQueue<Trip>(fScorecomparator);
		//closeset
		ArrayList<Trip> closeTrip = new ArrayList<Trip>();

		//put starting node into queue
		int GCost = calculateGCost(null);
		int FCost = calculateFCost(GCost, listOfShipment);
		Trip newT = new Trip(listOfShipment ,null, GCost, FCost, null,start, null);


		PQTrip.add(newT);
		
		//debug
		List<Trip> ListT = new ArrayList<Trip>();
		int n = 1;
		Trip last = newT;
		while(!PQTrip.isEmpty()){
			System.out.println("iteration:"+ n);
			boolean foundSmaller = false;
			Trip current = PQTrip.poll();
			Node currentNode = current.getTo();
			nodesExpanded++;
	
	
			if(!PQTrip.isEmpty()) {
				for(Edge e: current.getTripPath()) if(current.getListOfUncompletedShipment().contains(e)){
					current.removeUncompletedShipment(e);
					System.out.print("removing:");
					System.out.println(e);
				}
				System.out.print("Selecting: ");
				System.out.println("No = " + current.getListOfUncompletedShipment().size()+ " "+current);
			}
			
			
			for(Edge e: current.getListOfUncompletedShipment()) {
				Node shipmentFrom = e.getFrom();
				Node shipmentTo = e.getTo();
				List<Edge> currentToShipment = new ArrayList<Edge>();
				if(!g.getNodeName(currentNode).equals(g.getNodeName(shipmentFrom)) ) {
					Edge currentToFrom = g.findEdge(currentNode, shipmentFrom);
					currentToShipment.add(currentToFrom);
				}
				currentToShipment.add(e);
				
				ArrayList<Edge> clone = (ArrayList<Edge>) current.getListOfUncompletedShipment().clone();
			    GCost = calculateGCost(currentToShipment);
				FCost = calculateFCost(GCost, clone);

				newT = new Trip(clone, currentToShipment, GCost, GCost,currentNode, shipmentTo, current);
						
				
				//debug
				ListT.add(newT);
				
				
				if(PQTrip.isEmpty()) {
					PQTrip.add(newT);
					continue;
				}

				Trip topPQ = PQTrip.peek();
				if(topPQ.getTripFCost() <= current.getTripFCost()) {
					foundSmaller = true;
			//		System.out.println("SMALLER!");
					continue;
				}		
				if(PQTrip.contains(newT)) {
					if() {
						
					}
				}
				
	
			}

	
			if(current.getListOfUncompletedShipment().size() == 0 && foundSmaller == false) {
				last = current;
//				System.out.println("COMPLETED!");
//				System.out.println("Last: " + current);
				break;
				
			}
			
//			System.out.println("Changes:");
			for(Trip t: PQTrip) {
				System.out.println("No = " + t.getListOfUncompletedShipment().size() +" "+ t);
			}		

			n++;
		}
		
//		System.out.println("Camefrom:");
//		System.out.println(reorderPath(last));
			
		return reorderPath(last); 
			
	}
	
	public List<Edge> reorderPath(Trip current) {
		List<Edge> path = new ArrayList<Edge>();
		while(current.getCameFrom() != null) {

//			System.out.println(current.getTripPath());
			for(int i = current.getTripPath().size() - 1; i >= 0 ; i-- ) {
				path.add(current.getTripPath().get(i));
			}
			current = current.getCameFrom();
		}
		Collections.reverse(path);
		return path;
	}
	
	public int calculateGCost(List<Edge> tripPath) {
		int cost = 0;
		if(tripPath == null) return 0;
		for(Edge e: tripPath) {
			Node from = e.getFrom();
			Node to = e.getTo();
			cost = cost + from.getEdgeWeight(to) + from.getRefuellingTime();
		}
		return cost;
	}
	
	public int calculateFCost(int GCost, ArrayList<Edge> listOfUncompletedShipment) {
		HSS = new CalculateUsingNumberOfGoal();
		return GCost + HSS.calculateHeuristicValue(listOfUncompletedShipment, g);
	}
	
}