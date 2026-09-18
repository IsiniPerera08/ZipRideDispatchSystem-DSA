//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: HeapTestHarness.java
//Purpose: Tests ride scheduling heap operations.

public class HeapTestHarness {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args){
        System.out.println("\n======================================");
        System.out.println("      MODULE 3 HEAP TESTS");
        System.out.println("======================================");

        testInsertion();
        testPeek();
        testDispatch();
        testPriorityCalculation();

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

    private static void testInsertion(){
        System.out.println("\n-- Heap Insertions (10 Inserts) --");
        Heap heap = new Heap(20);
        RideRequest[] rides = {
            new RideRequest(1001,"Shali",5,10,501,"DriverA"),
            new RideRequest(1002,"Nili",2,20,502,"DriverB"),
            new RideRequest(1003,"Kenula",4,5,503,"DriverC"),
            new RideRequest(1004,"Amaya",3,12,504,"DriverD"),
            new RideRequest(1005,"Kavindu",1,25,505,"DriverE"),
            new RideRequest(1006,"Rashmi",5,8,506,"DriverF"),
            new RideRequest(1007,"Sahan",4,15,507,"DriverG"),
            new RideRequest(1008,"Ishara",2,18,508,"DriverH"),
            new RideRequest(1009,"Dinuka",3,11,509,"DriverI"),
            new RideRequest(1010,"Malshi",5,6,510,"DriverJ")
        };
        for(int i = 0; i < rides.length; i++){
            System.out.println("\nInsert #" + (i + 1));
            heap.insert(rides[i]);
        }
        check("10 rides inserted",heap.getCount() == 10);
    }

    private static void testPeek(){
        System.out.println("\n-- Heap Peek --");
        Heap heap = createHeapWith10Rides();
        RideRequest topRide =heap.peek();
        check("Peek returns ride",topRide != null);
    }

    private static void testDispatch(){
        System.out.println("\n-- Heap Extractions (5 Dispatches) --");
        Heap heap = createHeapWith10Rides();
        for(int i = 1; i <= 5; i++){
            System.out.println("\nDispatch #" + i);
            heap.removeMax();
        }
        check("5 rides dispatched",heap.getCount() == 5);
    }

    private static void testPriorityCalculation(){
        System.out.println("\n-- Priority Calculation --");
        RideRequest ride =new RideRequest(3001,"TestPassenger",5,10,601,"DriverX");
        check("Priority generated",ride.getPriority() > 0);
    }
    
    private static Heap createHeapWith10Rides(){
        Heap heap = new Heap(20);
        heap.insert(new RideRequest(1001,"Shali",5,10,501,"DriverA"));
        heap.insert(new RideRequest(1002,"Nili",2,20,502,"DriverB"));
        heap.insert(new RideRequest(1003,"Kenula",4,5,503,"DriverC"));
        heap.insert(new RideRequest(1004,"Amaya",3,12,504,"DriverD"));
        heap.insert(new RideRequest(1005,"Kavindu",1,25,505,"DriverE"));
        heap.insert(new RideRequest(1006,"Rashmi",5,8,506,"DriverF"));
        heap.insert(new RideRequest(1007,"Sahanya",4,15,507,"DriverG"));
        heap.insert(new RideRequest(1008,"Ishara",2,18,508,"DriverH"));
        heap.insert(new RideRequest(1009,"Dinuka",3,11,509,"DriverI"));
        heap.insert(new RideRequest(1010,"Malshi",5,6,510,"DriverJ"));
        return heap;
    }
}