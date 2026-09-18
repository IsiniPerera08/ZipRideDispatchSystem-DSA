//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: GraphNode.java
//Purpose:  Represents a location (vertex) in the road network graph, stores the location label, 
//          adjacency list of connected roads & additional attributes used for graph traversal algorithms.
//reference: lecture slides

public class GraphNode {
    private String label;
    private boolean visited;
    private LinkedList edgeList;
    private int distance;
    private GraphNode prev;

    //CONSTRUCTOR
    public GraphNode(String label){
        if(label==null || label.trim().equals("")){
            throw new IllegalArgumentException("Label cannot be empty...");
        }
        this.label= label.trim();
        this.edgeList= new LinkedList();
        this.visited= false;
        this.distance= Integer.MAX_VALUE;
        this.prev= null;
    }

    //ACCESSORS-GETTERS
    public String getLabel(){
        return label;
    }
    public LinkedList getEdgeList(){
        return edgeList;
    }
    public boolean getVisited(){
        return visited;
    }
    public int getDistance(){
        return distance;
    }
    public GraphNode getPrev(){
        return prev;
    }

    //MUTATORS- SETTERS
    public void setVisited(boolean visited){
        this.visited= visited;
    }
    public void setDistance(int dist){
        // if(dist<0){
        //     throw new IllegalArgumentException("Distance need to be positive...");
        // }
        distance= dist;
    }
    public void setPrev(GraphNode prev){
        this.prev= prev;
    }
    public void setEdgeList(LinkedList edgeList){
        this.edgeList = edgeList;
    }


    //EDGE OPERATIONS

    //ADD
    public void addEdge(GraphNode location, int weight){
        if(location== null){
            throw new IllegalArgumentException("Location cannot be empty.");
        }
        if(weight <=0){
            throw new IllegalArgumentException("Edge weight must be positive.");
        }
        if(hasEdge(location)){
            System.out.println("Edge already exists between " +label+ " and " +location.getLabel()+".");
            return;
        }
        GraphEdge newEdge= new GraphEdge(location, weight);
        edgeList.insertLast(newEdge);
    }

    //HAS
    public boolean hasEdge(GraphNode node){
        boolean found= false;
        ListNode curr= edgeList.getHead();
        while(curr!= null && !found){
            GraphEdge edge= (GraphEdge)curr.getValue();
            if(edge.getLocation().getLabel().equals(node.getLabel())){
                found= true;
            }curr= curr.getNext();
        }
        return found;
    }

    //DISPLAY
    public void displayEdges(){
        ListNode current= edgeList.getHead();
        if(current== null){
            System.out.println("No roads");
            return;
        }
        while(current != null){
            GraphEdge edge= (GraphEdge)current.getValue();
            System.out.println(edge.getLocation().getLabel()+ " -> " +edge.getWeight()+ " min");
            current= current.getNext();
        }
    }
}