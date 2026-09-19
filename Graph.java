import java.io.BufferedReader;
import java.io.FileReader;

public class Graph {
    private LinkedList vertices;
    private int vertexCount;
    private int edgeCount;

    public Graph(){
        vertices= new LinkedList();
        vertexCount= 0;
        edgeCount= 0;
    }

    //GETTERS
    public LinkedList getVertices(){
        return vertices;
    }

    public int getVertexCount(){
        return vertexCount;
    }

    public int getEdgeCount(){
        return edgeCount;
    }
    

    //ADDING VERTEX
    public void addVertex(String label) {
        if (hasVertex(label)) {
            System.out.println("Location: " + label + " already exists.");
            return;
        }
        GraphNode newNode= new GraphNode(label);
        vertices.insertLast(newNode);
        vertexCount++;
    }

    //ADDING EDGE
    public void addEdge(String start, String end, int weight){
        if(weight <= 0){
            throw new IllegalArgumentException("Weight must be greater than 0...");
        }
        GraphNode startNode= findVertex(start);
        GraphNode endNode= findVertex(end);
        if(startNode== null||endNode==null){
            throw new IllegalArgumentException("Invalid location...");
        }
        startNode.addEdge(endNode, weight);
        endNode.addEdge(startNode, weight);
        edgeCount++;
    }

    //REMOVING VERTEX
    public void removeVertex(String label){
        GraphNode removeNode = findVertex(label);
        if(removeNode == null){
            throw new IllegalArgumentException("Location not found...");
        }
        ListNode curr = vertices.getHead();
        while(curr != null){
            GraphNode node = (GraphNode)curr.getValue();
            if(node != removeNode){
                removeEdgeFromNode(node,removeNode);
            }
            curr = curr.getNext();
        }
        LinkedList newVertices = new LinkedList();
        curr = vertices.getHead();
        while(curr != null){
            GraphNode node = (GraphNode)curr.getValue();
            if(node != removeNode){
                newVertices.insertLast(node);
            }
            curr = curr.getNext();
        }
        vertices = newVertices;
        vertexCount--;
        System.out.println("Location removed successfully...");
    }

    //REMOVING EDGE
    public void removeEdge(String start, String end){
        GraphNode startNode = findVertex(start);
        GraphNode endNode = findVertex(end);
        if(startNode == null || endNode == null){
            throw new IllegalArgumentException("Invalid location...");
        }
        removeEdgeFromNode(startNode,endNode);
        removeEdgeFromNode(endNode,startNode);
        edgeCount--;
    }


    //HELPERS
    public boolean hasVertex(String label) {
        return findVertex(label) != null;
    }

    public GraphNode findVertex(String label) {
        GraphNode foundNode= null;
        ListNode curr = vertices.getHead();
        while (curr != null && foundNode== null){
            GraphNode node = (GraphNode)curr.getValue();
            if (node.getLabel().trim().equalsIgnoreCase(label)) {
                foundNode= node;
            }
            curr = curr.getNext();
        }
        return foundNode;
    }

    public void displayGraph(){
        ListNode current = vertices.getHead();
        while(current != null){
            GraphNode node = (GraphNode)current.getValue();
            System.out.println("Location: " + node.getLabel());
            node.displayEdges();
            System.out.println();
            current = current.getNext();
        }
    }

    public void displayLocations() {
        ListNode curr = vertices.getHead();
        while(curr != null) {
            GraphNode node = (GraphNode) curr.getValue();
            System.out.println(node.getLabel());
            curr = curr.getNext();
        }
    }

    private void removeEdgeFromNode(GraphNode from, GraphNode to){
        LinkedList newEdges = new LinkedList();
        ListNode curr = from.getEdgeList().getHead();
        while(curr != null){
            GraphEdge edge = (GraphEdge)curr.getValue();
            if(!edge.getLocation().getLabel().equalsIgnoreCase(to.getLabel())){
                newEdges.insertLast(edge);
            }
            curr = curr.getNext();
        }
        from.setEdgeList(newEdges);
    }
    

    // ======================================
    // ==============CSV LOADERS==============
    public void loadRoadsFromCSV(String filename){
        try{
            BufferedReader br =new BufferedReader(new FileReader(filename));
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");
                String start =data[0].trim();
                String end =data[1].trim();
                int weight =Integer.parseInt(data[2].trim());
                if(!hasVertex(start)){
                    addVertex(start);
                }
                if(!hasVertex(end)){
                    addVertex(end);
                }
                if(!start.equals(end)){
                    addEdge(start,end,weight);
                }
            }
            br.close();
            System.out.println("Road network loaded successfully.");
            System.out.println("Locations loaded: "+ getVertexCount());
            System.out.println("Roads loaded: "+ getEdgeCount());
        }catch(Exception e){
            System.out.println("Error loading roads: "+ e.getMessage());
        }
    }
}
