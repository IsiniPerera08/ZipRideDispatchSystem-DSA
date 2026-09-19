public class GraphAlgo {
    private static boolean firstNode;
    
    // marks all vertices as unvisited before running,
    // gtaph traversal algorithms such as BFS or DFS.
    public static void resetVisited(Graph graph){
        ListNode curr= graph.getVertices().getHead();
        while(curr != null){
            GraphNode node = (GraphNode)curr.getValue();
            node.setVisited(false);
            curr= curr.getNext();
        }
    }

    //to reset distance- Initialises all vertices with infinite distance and
    //               clears previous-node references before Dijkstra's algorithm.
    public static void resetDistance(Graph graph){
        ListNode curr= graph.getVertices().getHead();
        while(curr!= null){
            GraphNode node= (GraphNode)curr.getValue();
            node.setDistance(Integer.MAX_VALUE);
            node.setPrev(null);
            curr= curr.getNext();
        }
    }

    //BFS- traverse the graph level by level 
    public static void breadthFirstSearch(Graph graph, String start){
        GraphNode startNode = graph.findVertex(start);
        if(startNode == null){
            throw new IllegalArgumentException("Starting location not found: " + start);
        }
        resetVisited(graph);
        Queue queue = new Queue();
        startNode.setVisited(true);
        queue.enqueue(startNode);
        queue.enqueue(null); //used null as a level separator to group nodes by depth
        int level = 0;
        System.out.println("\nBFS Traversal from: " + start);
        System.out.println();
        System.out.println("Level 0:");
        while(!queue.isEmpty()){
            Object item = queue.dequeue();
            if(item == null){
                if(!queue.isEmpty()){
                    level++;
                    System.out.println();
                    System.out.println("Level " + level + ":");
                    queue.enqueue(null);
                }   
            }
            else{
                GraphNode currNode = (GraphNode)item;
                System.out.println("  " + currNode.getLabel());
                ListNode currEdge = currNode.getEdgeList().getHead();
                while(currEdge != null){
                    GraphEdge edge = (GraphEdge)currEdge.getValue();
                    GraphNode neighbour = edge.getLocation();
                    if(!neighbour.getVisited()){
                        neighbour.setVisited(true);
                        queue.enqueue(neighbour);
                    }
                    currEdge = currEdge.getNext();
                }
            }   
        }
        System.out.println();

        // //unreachable nodes
        // ListNode curr= graph.getVertices().getHead();
        // while(curr!= null){
        //     GraphNode node= (GraphNode)curr.getValue();
        //     if(!node.getVisited()){
        //         System.out.println("\nUnreachable Locations:");
        //     }
        //     curr= curr.getNext();
        // }
    }
    
    //DFS- traverse the graph by going to the most far
    public static void depthFirstSearch(Graph graph, String start){
        GraphNode startNode = graph.findVertex(start);
        if(startNode == null){
            throw new IllegalArgumentException("Starting location not found: " + start);
        }
        resetVisited(graph);
        firstNode = true;
        System.out.println("DFS Traversal from: " + start);
        depthFirstSearchRec(startNode);
        System.out.println();
    }

    // Recursive DFS helper method
    private static void depthFirstSearchRec(GraphNode node){
        node.setVisited(true);
        if(firstNode){
            System.out.print(node.getLabel());
            firstNode = false;
        }else{
            System.out.print(", " + node.getLabel());
        }
        ListNode currEdge = node.getEdgeList().getHead();
        while(currEdge != null){
            GraphEdge edge = (GraphEdge)currEdge.getValue();
            GraphNode neighbour = edge.getLocation();
            if(!neighbour.getVisited()){
                depthFirstSearchRec(neighbour);
            }
            currEdge = currEdge.getNext();
        }   
    }

    //Check if cyclic- Checks whether the graph contains a cycle and 
    //                displays the nodes involved in the detected cycle.
    public static boolean hasCycle(Graph graph){
        resetVisited(graph);
        ListNode curr= graph.getVertices().getHead();
        while(curr!=null){
            GraphNode node= (GraphNode)curr.getValue();
            if(!node.getVisited()){
                LinkedList path= new LinkedList(); 
                String[] cycleNodes= new String[1];
                if(cycleDetectPath(node, null, path, cycleNodes)){
                    System.out.println("\nCycle Detection: Detected!");
                    System.out.println("Nodes involved: "+cycleNodes[0]);
                    return true;
                }
            }
            curr= curr.getNext();
        }
        System.out.println("\nCycle Detection: Not detected!");
        return false;
    }

    // Start cycle detection from each unvisited vertex
    private static boolean cycleDetectPath(GraphNode current, GraphNode parent, LinkedList path, String[]cycleNodes){
        current.setVisited(true);
        path.insertLast(current.getLabel());
        ListNode currEdge= current.getEdgeList().getHead();
        while(currEdge!= null){
            GraphEdge edge= (GraphEdge)currEdge.getValue();
            GraphNode neighbour= edge.getLocation();
            //Unvisited
            if(!neighbour.getVisited()){
                if(cycleDetectPath(neighbour, current, path,cycleNodes)){
                    return true;
                }
            }
            //Visited but not parent
            else if(neighbour!=parent){
                String cycleStart= neighbour.getLabel();
                String result= "";
                boolean started= false;
                ListNode paths= path.getHead();

                while(paths!=null){
                    String labels= (String)paths.getValue();
                    if(labels.equals(cycleStart)){
                        started= true;
                    }
                    if(started){
                        result= result+labels+" -> ";
                    }
                    paths= paths.getNext();
                }
                result= result+cycleStart;
                cycleNodes[0]= result;
                return true;
            }
            currEdge= currEdge.getNext();
        }
        //backtrack
        path.removeLast();
        return false;
    }

    //low unvisited- reurns the unvisited vertex with the smallest currently known distance.
    private static GraphNode getLowDistNode(Graph graph){
        GraphNode lowNode= null;
        ListNode curr= graph.getVertices().getHead();
        while(curr!= null){
            GraphNode node= (GraphNode)curr.getValue();
            if(!node.getVisited()){
                if(lowNode==null || node.getDistance()< lowNode.getDistance()){
                    lowNode= node;
                }
            }
            curr= curr.getNext();
        }
        return lowNode;
    }



    //Dijkstra- Calculates the shortest path and driving time between two 
    //        locations in the road network.
    public static int dijkstra(Graph graph, String start, String destination){
        GraphNode startNode= graph.findVertex(start);
        GraphNode endNode= graph.findVertex(destination);
        if(startNode==null || endNode== null){
            throw new IllegalArgumentException("Invalid location/s: "+start+" , " +destination);
        }
        // Assignment special case:
        // same location gives T = 1, to avoid division by zero
        if(start.equals(destination)){
            System.out.println();
            System.out.println(start+ " -> "+destination);
            System.out.println("Already at the destination, driving time is 0 mins");
            return 0;
        }
        resetVisited(graph);
        resetDistance(graph);
        startNode.setDistance(0); //starting vertex has distance 0
        GraphNode current= getLowDistNode(graph);
        while(current != null && current.getDistance() != Integer.MAX_VALUE){
            current.setVisited(true);
            ListNode currEdge= current.getEdgeList().getHead();
            while(currEdge!= null){
                GraphEdge edge= (GraphEdge)currEdge.getValue();
                GraphNode neighbour= edge.getLocation();
                if(!neighbour.getVisited()){
                    //the guard against overflow when dist is MAX_VALUE
                    if(current.getDistance()!= Integer.MAX_VALUE){
                        int newDistance= current.getDistance()+edge.getWeight();
                        if(newDistance < neighbour.getDistance()){
                            neighbour.setDistance(newDistance);
                            neighbour.setPrev(current);
                        }
                    }
                }
                currEdge= currEdge.getNext();
            }
            current= getLowDistNode(graph);
        }
        System.out.println("\nDijkstra: "+start+ " -> "+destination);
        outputShortPath(endNode);
        return endNode.getDistance();
    }

    //reconstructs and displays the shortest route from source to destination.
    private static void outputShortPath(GraphNode destination){
        if(destination.getDistance()== Integer.MAX_VALUE){
            System.out.println("No path found, is unreachable...");
            return;
        }
        Stack stack= new Stack();
        GraphNode current= destination;
        while(current!= null){
            stack.push(current);
            current= current.getPrev();
        }
        System.out.print("shortest path: ");
        while(!stack.isEmpty()){
            GraphNode node= (GraphNode)stack.pop();
            System.out.print(node.getLabel());
            if(!stack.isEmpty()){
                System.out.print(" -> ");
            }
        }System.out.println();
        System.out.println("Total driving time: " +destination.getDistance()+ " mins");
    }
    
    // returns distance only, no printing- Used by Module 3 (driver selection) and Module 4 (dataset generation).
    public static int getShortestDistance(Graph graph, String start, String destination){
        if (start.equals(destination)) {
            return 1; // T = 1 to avoid division by zero in priority formula
        }
        GraphNode startNode = graph.findVertex(start);
        GraphNode endNode   = graph.findVertex(destination);
        if (startNode == null || endNode == null) {
            return Integer.MAX_VALUE;
        }
        resetVisited(graph);
        resetDistance(graph);
        startNode.setDistance(0);
        GraphNode current = getLowDistNode(graph);
        while (current != null && current.getDistance() != Integer.MAX_VALUE) {
            current.setVisited(true);
            ListNode currEdge = current.getEdgeList().getHead();
            while (currEdge != null) {
                GraphEdge edge= (GraphEdge) currEdge.getValue();
                GraphNode neighbour = edge.getLocation();
                if (!neighbour.getVisited()) {
                    int newDist = current.getDistance() + edge.getWeight();
                    if (newDist < neighbour.getDistance()) {
                        neighbour.setDistance(newDist);
                        neighbour.setPrev(current);
                    }
                }
                currEdge = currEdge.getNext();
            }   
            current = getLowDistNode(graph);
        }
        return endNode.getDistance();
    }

    // finding best driver
    public static Driver findBestDriver(Graph graph,HashTable driverTable,Passenger passenger){
        Driver bestDriver = null;
        int bestTime =Integer.MAX_VALUE;
        HashEntry[] entries =driverTable.getHashArray();
        for(int i = 0; i < entries.length; i++){
            if(entries[i] != null &&!entries[i].getDeleted()){
                Driver driver =(Driver) entries[i].getVal();
                if(driver.getAvailabilityStatus().equals("Available")){
                    int time =GraphAlgo.getShortestDistance(graph,driver.getCurrLocation().getLabel(),passenger.getPickUpLocation());
                    if(time < bestTime){
                        bestTime = time;
                        bestDriver = driver;
                    }
                }
            }
        }
        return bestDriver;
    }
}
