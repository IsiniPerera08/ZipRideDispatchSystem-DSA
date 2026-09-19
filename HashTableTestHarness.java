public class HashTableTestHarness {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args){
        System.out.println("\n======================================");
        System.out.println("    MODULE 2 HASH TABLE TESTS");
        System.out.println("======================================");

        testPassengerOperations();
        testCollisionHandling();
        testDeleteOperation();

        System.out.println("\n======================================");
        System.out.println("Passed: " + passed + "/" + total);
        System.out.println("======================================");
    }

    private static void check(String testName, boolean result){
        total++;
        if(result){
            passed++;System.out.println("PASS: " + testName);
        }else{
            System.out.println("FAIL: " + testName);
        }
    }

    private static void testCollisionHandling(){
        System.out.println("\n-- Collision Handling --");
        HashTable table = new HashTable(11);
        table.insert(11,"A");
        table.insert(22,"B");
        table.insert(33,"C");
        check("Collision records inserted",table.getCount() == 3);
        check("Search key 22",table.search(22) != null);
    }

    private static void testDeleteOperation(){
        System.out.println("\n-- Delete Operation --");
        HashTable table = new HashTable(31);
        Passenger p =new Passenger(2001,"Kenula","Galle",2);

        table.insert(p.getPassengerId(),p);
        table.delete(2001);
        check("Passenger deleted",table.search(2001) == null);
    }

    private static void testPassengerOperations(){
        System.out.println("\n-- Passenger Operations --");
        HashTable table = new HashTable(41);

        table.insert(1001,new Passenger(1001,"Shali","Colombo",3));
        table.insert(1002,new Passenger(1002,"Nili","Kandy",5));
        table.insert(1003,new Passenger(1003,"Amaya","Galle",2));
        table.insert(1004,new Passenger(1004,"Kavindu","Matara",4));
        table.insert(1005,new Passenger(1005,"Sahan","Jaffna",1));

        table.insert(1006,new Passenger(1006,"Nethmi","Colombo",3));
        table.insert(1007,new Passenger(1007,"Pasindu","Kandy",2));
        table.insert(1008,new Passenger(1008,"Ishara","Galle",4));
        table.insert(1009,new Passenger(1009,"Nadeesha","Matara",5));
        table.insert(1010,new Passenger(1010,"Rashmi","Jaffna",1));

        table.insert(1011,new Passenger(1011,"Dinuka","Colombo",2));
        table.insert(1012,new Passenger(1012,"Tharushi","Kandy",4));
        table.insert(1013,new Passenger(1013,"Supun","Galle",3));
        table.insert(1014,new Passenger(1014,"Chamodi","Matara",5));
        table.insert(1015,new Passenger(1015,"Kasun","Jaffna",1));

        table.insert(1016,new Passenger(1016,"Malshi","Colombo",4));
        table.insert(1017,new Passenger(1017,"Praveen","Kandy",3));
        table.insert(1018,new Passenger(1018,"Ashani","Galle",2));
        table.insert(1019,new Passenger(1019,"Vihanga","Matara",5));
        table.insert(1020,new Passenger(1020,"Dilmi","Jaffna",1));

        check("20 passengers inserted", table.getCount() == 20);
        check("Search existing passenger", table.search(1001) != null);
        check("Search missing passenger", table.search(9999) == null);        
        long start = System.nanoTime();
        table.search(1001);
        long end = System.nanoTime();
        System.out.println("Search Time: " + (end - start) + " ns");
    }
}
