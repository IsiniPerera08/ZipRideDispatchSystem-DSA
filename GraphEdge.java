//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: GraphEdge.java
//Purpose: Represents a road (edge) connecting two locations in the graph,stores the 
//          destination location and the travel time (weight) associated with the road.

public class GraphEdge {
    private GraphNode location;
    private int weight;
    
    public GraphEdge(GraphNode location, int weight){
        if(location==null){
            throw new IllegalArgumentException("Location cannot be empty...");
        }
        if(weight<=0){
            throw new IllegalArgumentException("Weight must be positive...");
        }
        this.location= location;
        this.weight= weight;
    }

    //ACCESSORS-GETTERS
    public GraphNode getLocation(){
        return location;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString(){
        return location.getLabel()+" -> " +weight +" mins";
    }
}