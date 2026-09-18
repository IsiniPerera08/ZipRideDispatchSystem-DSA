//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: GraphTestHarness.java
//Purpose: Tests Module 1 graph operations including location management,
//         road management, traversals, cycle detection and shortest path.

public class GraphTestHarness {
    static int passed = 0;
    static int total = 0;
    public static void main(String[] args){
        System.out.println("\n======================================");
        System.out.println("       MODULE 1 GRAPH TESTS");
        System.out.println("======================================");

        testLocationOperations();
        testRoadOperations();
        testBFS();
        testDFS();
        testCycleDetection();
        testShortestPath();

        System.out.println("\n======================================");
        System.out.println("Passed: " + passed + "/" + total);
        System.out.println("======================================");
    }

    private static void check(String testName, boolean result){
        total++;
        if(result){
            passed++;
            System.out.println("PASS: " + testName);
        }else{
            System.out.println("FAIL: " + testName);
        }
    }

    // ==================================================
    // LOCATION TESTS
    // ==================================================
    private static void testLocationOperations(){ 
        System.out.println("\n-- Location Operations --"); 
        Graph graph = createGraph(); 
        check("Graph contains 8 locations", graph.getVertexCount() == 8); 
        check("Location Colombo exists", graph.hasVertex("Colombo")); 
        check("Location Airport exists", graph.hasVertex("Airport")); 
        graph.removeVertex("Airport"); 
        check("Airport removed successfully", 
        graph.findVertex("Airport") == null);
    }

    // ==================================================
    // ROAD TESTS
    // ==================================================
    private static void testRoadOperations(){ 
        System.out.println("\n-- Road Operations --");  
        Graph graph = createGraph();
        check("Graph contains 10 roads", graph.getEdgeCount() == 10); 
        int beforeRemove = graph.getEdgeCount();
        graph.removeEdge("Colombo", "Kandy"); 
        check("Road removed successfully",graph.getEdgeCount() == beforeRemove - 1);
        int beforeAdd = graph.getEdgeCount(); 
        graph.addEdge("Colombo", "Kandy", 10); 
        check("Road added successfully", graph.getEdgeCount() == beforeAdd + 1); 
    }

    // ==================================================
    // BFS
    // ==================================================
    private static void testBFS(){
        System.out.println("\n-- Breadth First Search --");
        Graph graph = createGraph();
        GraphAlgo.breadthFirstSearch(graph,"Colombo");
        check("BFS executed successfully",true);
    }

    // ==================================================
    // DFS
    // ==================================================
    private static void testDFS(){
        System.out.println("\n-- Depth First Search --");
        Graph graph = createGraph();
        GraphAlgo.depthFirstSearch(graph,"Colombo");
        check("DFS executed successfully",true);
    }

    // ==================================================
    // CYCLE DETECTION
    // ==================================================
    private static void testCycleDetection(){
        System.out.println("\n-- Cycle Detection --");
        Graph graph = createGraph();
        boolean cycle =GraphAlgo.hasCycle(graph);
        check("Cycle detected",cycle);
    }

    // ==================================================
    // SHORTEST PATH
    // ==================================================
    private static void testShortestPath(){
        System.out.println("\n-- Dijkstra Shortest Path --");
        Graph graph = createGraph();
        int distance =GraphAlgo.dijkstra(graph,"Colombo","Jaffna");
        check("Shortest path found",distance > 0);
    }
    // ==================================================
    // HELPER GRAPH
    // ==================================================
    private static Graph createGraph(){
        Graph graph = new Graph(); 
        // 8 Locations 
        graph.addVertex("Colombo"); 
        graph.addVertex("Kandy"); 
        graph.addVertex("Galle"); 
        graph.addVertex("Matara"); 
        graph.addVertex("Jaffna"); 
        graph.addVertex("Kurunegala"); 
        graph.addVertex("Negombo"); 
        graph.addVertex("Airport"); // isolated node 
        // 10 Roads 
        graph.addEdge( "Colombo", "Kandy", 10 ); 
        graph.addEdge( "Kandy", "Kurunegala", 15 ); 
        graph.addEdge( "Kurunegala", "Negombo", 12 ); 
        graph.addEdge( "Negombo", "Colombo", 8 ); 
        graph.addEdge( "Colombo", "Galle", 20 ); 
        graph.addEdge( "Galle", "Matara", 15 ); 
        graph.addEdge( "Matara", "Jaffna", 30 ); 
        graph.addEdge( "Kandy", "Jaffna", 25 ); 
        graph.addEdge( "Kurunegala", "Matara", 18 ); 
        graph.addEdge( "Negombo", "Galle", 22 ); 
        return graph; 
    } 
}