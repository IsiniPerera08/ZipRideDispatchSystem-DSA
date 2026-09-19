import java.util.Scanner;

public class Menu{

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();
        HashTable passengerTable = new HashTable(47);
        HashTable driverTable = new HashTable(47);
        Heap heap = new Heap(100);
        graph.loadRoadsFromCSV("road.csv");
        passengerTable.loadPassengersFromCSV("passengers.csv");
        driverTable.loadDriversFromCSV("drivers.csv",graph);

        boolean running = true;
        while(running){
            printMainMenu();
            try{
                int choice = Integer.parseInt(sc.nextLine());
                switch(choice){
                    case 1:
                        roadNetworkMenu(sc, graph);
                        break;
                    case 2:
                        passengerMenu(sc, passengerTable, graph);
                        break;
                    case 3:
                        driverMenu(sc, driverTable, graph);
                        break;
                    case 4:
                        rideMenu(sc, graph,passengerTable, driverTable,heap);
                        break;
                    case 5:
                        sortingMenu(sc, graph,passengerTable,driverTable);
                            break;
                    case 0:
                        System.out.println("\nThank you for using the ZipRide Dispatch System... Have a nice day!");
                        running = false;
                        break;
                    default:
                        System.out.println( "Invalid choice.");
                    }
            }catch(NumberFormatException e){
                System.out.println("Please enter valid choice...");
            }
            // sc.close();
        }
    }   


    //========================================
    //===========Road network Menu===========
    private static void roadNetworkMenu(Scanner sc,Graph graph){
        boolean back= false;
        while(!back){
            printRoadNetworkMenu();
            try{
            int choice=Integer.parseInt(sc.nextLine());
            switch(choice){
                case 1:
                    System.out.println();
                    graph.displayGraph();
                    break;
                case 2:
                    System.out.println( "\nAvailable Locations:");
                    graph.displayLocations();
                    break;
                case 3:
                    System.out.print("Enter new location: ");
                    String newLocation = sc.nextLine().trim();
                    if(newLocation.equals("")){
                        System.out.println("Location name cannot be empty...");
                    }else if(graph.findVertex(newLocation) != null){
                        System.out.println("Location already exists...");
                    }else{
                        graph.addVertex(newLocation);
                        System.out.println("Location added successfully...");
                    }
                    break;
                case 4:
                    try{
                        System.out.print("Enter start location: ");
                        String start = sc.nextLine().trim();
                        System.out.print("Enter destination location: ");
                        String end = sc.nextLine().trim();
                        if(start.equals("") || end.equals("")){
                            System.out.println("Locations cannot be empty...");
                        }else if(graph.findVertex(start) == null){
                            System.out.println("Start location does not exist...");
                        }else if(graph.findVertex(end) == null){
                            System.out.println("Destination location does not exist...");
                        }else{
                            System.out.print("Enter travel time (minutes): ");
                            int weight = Integer.parseInt(sc.nextLine());
                            if(weight <= 0){
                                System.out.println("Travel time must be greater than 0...");
                            }else{
                                graph.addEdge(start,end,weight);
                                System.out.println("Road added successfully...");
                            }
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Travel time must be a number...");
                    }catch(Exception e){
                        System.out.println("Unable to add road...");
                    }
                    break;
                case 5:
                    try{
                        System.out.print("Enter location to remove: ");
                        String location = sc.nextLine().trim();
                        if(location.equals("")){
                            System.out.println("Location cannot be empty...");
                        }else{
                            graph.removeVertex(location);
                        }
                    }catch(Exception e){
                        System.out.println("Unable to remove location...");
                    }
                    break;
                case 6:
                    try{
                        System.out.print("Enter start location: ");
                        String start = sc.nextLine().trim();
                        System.out.print("Enter destination location: ");
                        String end = sc.nextLine().trim();
                        if(start.equals("") || end.equals("")){
                            System.out.println("Locations cannot be empty...");
                        }else{
                            graph.removeEdge(start,end);
                            System.out.println("Road removed successfully...");
                        }
                    }catch(Exception e){
                        System.out.println("Unable to remove road...");
                    }
                    break;
                case 7:
                    System.out.print("Enter starting location: ");
                    String bfsStart = sc.nextLine().trim();
                    if(bfsStart.equals("")){
                        System.out.println("Please enter a starting location...");
                    }else{
                        try{
                            GraphAlgo.breadthFirstSearch(graph,bfsStart);
                        }catch(Exception e){
                            System.out.println("Location '"+bfsStart +"' does not exist in the road network...");
                        }
                    }
                    break;
                case 8:
                    System.out.print("Enter starting location: ");
                    String dfsStart= sc.nextLine().trim();
                    if(dfsStart.equals("")){
                        System.out.println("Please enter a starting location...");
                    }else{
                        try{
                            GraphAlgo.depthFirstSearch(graph,dfsStart);
                        }
                        catch(Exception e){
                            System.out.println("Location '"+dfsStart+"' does not exist in the road network.");
                        }
                    }
                    break;
                case 9:
                    GraphAlgo.hasCycle(graph);
                    break;
                case 10:
                    System.out.print("Source location: ");
                    String source = sc.nextLine().trim();
                    System.out.print("Destination location: ");
                    String destination = sc.nextLine().trim();
                    if(source.equals("") || destination.equals("")){
                        System.out.println("Source and destination locations cannot be empty.");
                    }else{
                        try{
                            GraphAlgo.dijkstra(graph,source,destination);
                        }catch(Exception e){
                            System.out.println("Invalid location entered, try again...");
                        }
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice...");
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid choice... Enter again!");
        }
        }
    }

    //========================================
    //===========Passenger Management Menu============
    private static void passengerMenu(Scanner sc, HashTable passengerTable, Graph graph){
        boolean back = false;
        while(!back){
            printPassengerMenu();
            try{
                int choice =Integer.parseInt(sc.nextLine());
                switch(choice){
                    //Display all passengers
                    case 1:
                        passengerTable.displayRecords();
                        break;
                    //Search passenger
                    case 2:
                        System.out.print("Enter Passenger ID: ");
                        try{
                            int searchId = Integer.parseInt(sc.nextLine());
                            Object result = passengerTable.search(searchId);
                            if(result == null){
                                System.out.println("Passenger not found...");
                            }else{
                                System.out.println(result);
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Invalid Passenger ID...");
                        }
                        break;
                    //Add passenger
                    case 3:
                        try{
                            System.out.print("Passenger ID: ");
                            String idInput = sc.nextLine().trim();
                            if(idInput.equals("")){
                                System.out.println("Passenger ID cannot be empty...");
                            }else{
                                int id = Integer.parseInt(idInput);
                                System.out.print("Passenger Name: ");
                                String name = sc.nextLine().trim();
                                if(name.equals("")){
                                    System.out.println("Passenger name cannot be empty...");
                                }else{
                                    System.out.print("Pickup Location: ");
                                    String location = sc.nextLine().trim();
                                    if(location.equals("")){
                                        System.out.println("Pickup location cannot be empty...");
                                    }else if(graph.findVertex(location) == null){
                                        System.out.println("Invalid pickup location, try again...");
                                    }else{
                                        System.out.print("Membership Tier (1-5): ");
                                        int tier = Integer.parseInt(sc.nextLine());
                                        if(tier < 1 || tier > 5){
                                            System.out.println("Membership Tier must be between 1 and 5...");
                                        }else{
                                            if(passengerTable.keyExists(id)){
                                                System.out.println("Passenger ID already exists. Passenger not added...");
                                            }else{
                                                Passenger passenger = new Passenger(id,name,location,tier);
                                                passengerTable.insert(id,passenger);
                                                System.out.println("Passenger added successfully...");
                                            }
                                        }
                                    }
                                }
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Passenger ID and Membership Tier must be numbers...");
                        }catch(Exception e){
                            System.out.println("Unable to add passenger, try again!");
                        }
                        break;
                    //Delete passenger
                    case 4:
                        try{
                            System.out.print("Enter Passenger ID: ");
                            int deleteId = Integer.parseInt(sc.nextLine());
                            boolean deleted = passengerTable.delete(deleteId);
                            if(deleted){
                                System.out.println("Passenger deleted successfully...");
                            }else{
                                System.out.println("Passenger not found...");
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Invalid Passenger ID...");
                        }
                        break;
                    case 5:
                        try{
                            System.out.print("Enter Passenger ID: ");
                            int passengerId = Integer.parseInt(sc.nextLine());
                            Passenger passenger =
                            (Passenger)passengerTable.search(passengerId);
                            if(passenger == null){
                                System.out.println("Passenger not found...");
                            }
                            System.out.print("Enter New Membership Tier (1-5): ");
                            int newTier = Integer.parseInt(sc.nextLine());
                            passenger.setMembershipTier(newTier);
                            System.out.println("Membership tier updated successfully.");
                            System.out.println("Updated Passenger:");
                            System.out.println(passenger);
                        }catch(Exception e){
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Enter filename to save passenger data: ");
                        String filename = sc.nextLine().trim();
                        if(filename.equals("")){
                            System.out.println("Filename cannot be empty...");
                        }else{
                            if(!filename.toLowerCase().endsWith(".csv")){
                                filename = filename + ".csv";
                            }
                            passengerTable.savePassengersToCSV(filename);
                            System.out.println("Passenger data saved successfully to " + filename);
                        }
                        break;
                    //Back
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice...");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid choice... Enter again!");
            }
        }
    }

    // =======================================================
    // ================DRIVER MANAGEMENT MENU================
    private static void driverMenu(Scanner sc, HashTable driverTable, Graph graph){
        boolean back = false;
        while(!back){
            printDriverMenu();
            try{
                int choice = Integer.parseInt(sc.nextLine());
                switch(choice){
                    // Display all drivers
                    case 1:
                        driverTable.displayRecords();
                        break;
                    // Search driver
                    case 2:
                        System.out.print("Enter Driver ID: ");
                        try{
                            int searchId = Integer.parseInt(sc.nextLine());
                            Object result = driverTable.search(searchId);
                            if(result == null){
                                System.out.println("Driver not found...");
                            }else{
                                System.out.println(result);
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Invalid Driver ID...");
                        }
                        break;  
                    case 3:
                        try{
                            System.out.print("Driver ID: ");
                            int id = Integer.parseInt(sc.nextLine());
                            System.out.print("Driver Name: ");
                            String name = sc.nextLine().trim();
                            if(name.equals("")){
                                System.out.println("Driver name cannot be empty...");
                            }else{
                                System.out.print("Current Location: ");
                                String location = sc.nextLine().trim();
                                GraphNode node = graph.findVertex(location);
                                if(node == null){
                                    System.out.println("Invalid location...");
                                }else{
                                    System.out.print("Status (Available/Busy/Offline): ");
                                    String status = sc.nextLine().trim();
                                    if(driverTable.keyExists(id)){
                                        System.out.println("Driver ID already exists so driver not added...");
                                    }else{
                                        Driver driver = new Driver(id,name,node,status);
                                        driverTable.insert(id,driver);
                                        System.out.println("Driver added successfully...");
                                    }
                                }
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Driver ID must be a number...");
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        break;    
                    case 4:
                        try{
                            System.out.print("Enter Driver ID: ");
                            int deleteId = Integer.parseInt(sc.nextLine());
                            boolean deleted = driverTable.delete(deleteId);
                            if(deleted){
                                System.out.println("Driver deleted successfully...");
                            }else{
                                System.out.println("Driver not found...");
                            }
                        }catch(NumberFormatException e){
                            System.out.println("Invalid Driver ID...");
                        }
                        break;         
                    // Update driver status
                    case 5:
                        try{
                            System.out.print("Enter Driver ID: ");
                            int driverId = Integer.parseInt(sc.nextLine());
                            Driver driver = (Driver)driverTable.search(driverId);
                            if(driver == null){
                                System.out.println("Driver not found...");
                            }else{
                                System.out.println("\nCurrent Status: " + driver.getAvailabilityStatus());
                                System.out.println("\nAvailable Status Values:");
                                System.out.println("Available");
                                System.out.println("Busy");
                                System.out.println("Offline");
                                System.out.print("\nEnter New Status: ");
                                String status = sc.nextLine().trim();
                                if(status.equalsIgnoreCase("Available")|| status.equalsIgnoreCase("Busy")|| status.equalsIgnoreCase("Offline")){
                                    driver.setAvailabilityStatus(status);
                                    System.out.println("Driver status updated...");
                                }else{
                                    System.out.println("Invalid status. Please enter Available, Busy or Offline...");
                                }
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("Invalid Driver ID...");
                        }
                        catch(Exception e){
                            System.out.println("Unable to update driver status...");
                        }
                        break;
                    case 6:
                        System.out.print("Enter filename to save driver data: ");
                        String filename = sc.nextLine().trim();
                        if(filename.equals("")){
                            System.out.println("Filename cannot be empty...");
                        }else{
                            if(!filename.toLowerCase().endsWith(".csv")){
                                filename = filename + ".csv";
                            }
                            driverTable.saveDriversToCSV(filename);
                            System.out.println("Driver data saved successfully to " + filename);
                        }
                        break;
                    // Back
                    case 0:
                        back = true;
                            break;
                    default:
                        System.out.println("Invalid choice... Please Enter again!");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid choice... enter again!");
            }
        }
    }

    // ==============================================
    // =============RIDE SCHEDULING MENU=============
    private static void rideMenu(Scanner sc,Graph graph,HashTable passengerTable,HashTable driverTable,Heap heap){
        boolean back = false;
        while(!back){
            printRideMenu();
            try{
                int choice =Integer.parseInt(sc.nextLine());
                switch(choice){
                    // Create ride request
                    case 1:
                        try{
                            System.out.print("Enter Passenger ID: ");
                            int passengerId =Integer.parseInt(sc.nextLine());
                            Passenger passenger =(Passenger)passengerTable.search(passengerId);
                            if(passenger == null){
                                System.out.println("Passenger not found...");
                            }else{
                                Driver bestDriver =GraphAlgo.findBestDriver(graph,driverTable,passenger);
                                if(bestDriver == null){
                                    System.out.println("No available drivers...");                            
                                }else{
                                    int pickupTime =GraphAlgo.getShortestDistance(graph,bestDriver.getCurrLocation().getLabel(),passenger.getPickUpLocation());
                                    if(pickupTime == Integer.MAX_VALUE){
                                        pickupTime = 30; // unreachable route fallback
                                    }
                                    if(pickupTime <= 0){
                                        pickupTime = 1; // same location special case
                                    }                                    
                                    RideRequest request =new RideRequest(passenger.getPassengerId(),passenger.getPassengerName(),passenger.getMembershipTier(),pickupTime,bestDriver.getDriverId(),bestDriver.getDriverName());
                                    double priority = (6 - passenger.getMembershipTier()) + (1000.0 / pickupTime);
                                    System.out.println("\nAssigned Driver: "+ bestDriver.getDriverName());
                                    System.out.println("Pickup Time: "+ pickupTime+ " mins");
                                    System.out.println("Priority = (6-" + passenger.getMembershipTier() +")+ 1000/" + pickupTime +" = " + priority);                                
                                    bestDriver.setAvailabilityStatus("Busy");
                                    heap.insert(request);
                                }
                            }
                        }catch(Exception e){
                            System.out.println("Error caught: "+e.getMessage());
                        }
                        break;
                    // View priority queue
                    case 2:
                        heap.displayHeap();
                        break;
                    // View highest priority ride
                    case 3:
                        try{
                            System.out.println(heap.peek());
                        }catch(Exception e){
                            System.out.println("No ride requests available in the priority queue...");
                        }
                        break;
                    // Dispatch next ride
                    case 4:
                    try{
                        heap.removeMax();
                    }catch(Exception e){
                        System.out.println("No ride requests available...");
                    }
                    break;
                    // Back
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                    }
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid choice... Enter again!");
                }
            }
        }

    // ====================================
    // ===========SORTING MENU===========
    private static void sortingMenu(Scanner sc,Graph graph,HashTable passengerTable,HashTable driverTable){
        boolean back = false;
        while(!back){
            printSortingMenu();
            try{
                int choice = Integer.parseInt(sc.nextLine());
                switch(choice){
                    case 1:
                        RideRequest[] mergeData =SortingTestHarness.generateDataset(100,graph,passengerTable,driverTable);
                        Sorting.resetCount();
                        Sorting.mergeSort(mergeData,0,mergeData.length - 1);
                        System.out.println("\nMerge Sort Completed...");
                        System.out.println("Comparisons: "+ Sorting.getCompCount());
                        System.out.println("Sorted correctly: "+ Sorting.isSorted(mergeData));
                        Sorting.printFirstAndLast(mergeData,5);
                        break;
                    case 2:
                        RideRequest[] quickData =SortingTestHarness.generateDataset(100,graph,passengerTable,driverTable);
                        Sorting.resetCount();
                        Sorting.quickSort(quickData,0,quickData.length - 1);
                        System.out.println("\nQuick Sort Completed...");
                        System.out.println("Comparisons: "+ Sorting.getCompCount());
                        System.out.println("Sorted correctly: "+ Sorting.isSorted(quickData));
                        Sorting.printFirstAndLast(quickData,5);
                        break;
                    case 3:
                        SortingTestHarness.runAnalysis(graph,passengerTable,driverTable);
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice...");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid choice... Enter again!");
            }
        }
    }

    // =========================================================
    // MENUS
    // =========================================================

    private static void printMainMenu() {
        System.out.println("\n======================================");
        System.out.println("      RIDE SHARING MANAGEMENT");
        System.out.println("======================================");
        System.out.println("1> Road Network");
        System.out.println("2> Passenger Management");
        System.out.println("3> Driver Management");
        System.out.println("4> Ride Scheduling");
        System.out.println("5> Sorting & Performance Analysis");
        System.out.println("0> Exit");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void printRoadNetworkMenu() {
        System.out.println("\n======================================");
        System.out.println("          ROAD NETWORK");
        System.out.println("======================================");
        System.out.println("1> Display Road Network");
        System.out.println("2> Display Locations");
        System.out.println("3> Add Location (Node)");
        System.out.println("4> Add Road (Edge)");
        System.out.println("5> Remove Location (Node)");
        System.out.println("6> Remove Road (Edge)");
        System.out.println("7> Breadth First Search (BFS)");
        System.out.println("8> Depth First Search (DFS)");
        System.out.println("9> Check for Cycles");
        System.out.println("10> Find Shortest Path");
        System.out.println("0> Back");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void printPassengerMenu() {
        System.out.println("\n======================================");
        System.out.println("       PASSENGER MANAGEMENT");
        System.out.println("======================================");
        System.out.println("1> Display All Passengers");
        System.out.println("2> Search Passenger");
        System.out.println("3> Add Passenger");
        System.out.println("4> Delete Passenger");
        System.out.println("5> Update Membership Tier");
        System.out.println("6> Save passengers to CSV");
        System.out.println("0> Back");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void printDriverMenu() {
        System.out.println("\n======================================");
        System.out.println("         DRIVER MANAGEMENT");
        System.out.println("======================================");
        System.out.println("1> Display All Drivers");
        System.out.println("2> Search Driver");
        System.out.println("3> Add Driver");
        System.out.println("4> Delete Driver");
        System.out.println("5> Update Driver Status");
        System.out.println("6> Save drivers to CSV");
        System.out.println("0> Back");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void printRideMenu() {
        System.out.println("\n======================================");
        System.out.println("         RIDE SCHEDULING");
        System.out.println("======================================");
        System.out.println("1> Create Ride Request");
        System.out.println("2> View Priority Queue");
        System.out.println("3> View Highest Priority Ride");
        System.out.println("4> Dispatch Next Ride");
        System.out.println("0> Back");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void printSortingMenu() {
        System.out.println("\n======================================");
        System.out.println("      SORTING & ANALYSIS");
        System.out.println("======================================");
        System.out.println("1> Run Merge Sort");
        System.out.println("2> Run Quick Sort");
        System.out.println("3> Compare Merge Sort and Quick Sort");
        System.out.println("0> Back");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }
}
