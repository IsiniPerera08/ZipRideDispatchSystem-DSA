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
