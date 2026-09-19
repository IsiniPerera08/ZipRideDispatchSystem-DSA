public class SortingTestHarness {

    // Generate RideRequest dataset for testing
    public static RideRequest[] generateDataset(int size,Graph graph,HashTable passengerTable,HashTable driverTable){
        if(passengerTable.getCount() == 0){
            throw new IllegalStateException("No passenger records available...");
        }
        if(driverTable.getCount() == 0){
            throw new IllegalStateException("No driver records available...");
        }
        RideRequest[] data = new RideRequest[size];
        HashEntry[] passengers = passengerTable.getHashArray();
        HashEntry[] drivers = driverTable.getHashArray();
        int pIndex = 0;
        int dIndex = 0;
        for(int i = 0; i < size; i++){
            while(passengers[pIndex % passengers.length] == null || passengers[pIndex % passengers.length].getDeleted()){
                pIndex++;
            }
            while(drivers[dIndex % drivers.length] == null || drivers[dIndex % drivers.length].getDeleted()){
                dIndex++;                
            }
            Passenger p = (Passenger)passengers[pIndex % passengers.length].getVal();
            Driver d =(Driver)drivers[dIndex % drivers.length].getVal();
            int pickupTime =GraphAlgo.getShortestDistance(graph,d.getCurrLocation().getLabel(),p.getPickUpLocation());
            if(pickupTime == Integer.MAX_VALUE){    //Use a default pickup time if no route exists
                pickupTime = 30;
            }
            data[i] = new RideRequest(p.getPassengerId(),p.getPassengerName(),p.getMembershipTier(),pickupTime,d.getDriverId(),d.getDriverName());
            pIndex += 3;
            dIndex += 5;            
        }
        return data;
    }

    //RUN ANALYSIS-Compares merge & quick Sort uding datasets ofvarying sizes and arrangements (random, nearly sorted,and reversed).
    public static void runAnalysis(Graph graph, HashTable passengerTable, HashTable driverTable){
        int[] sizes = {100, 500, 1000};
        String[] conditions = {"RANDOM", "NEARLY_SORTED", "REVERSED"};

        for(int s = 0; s < sizes.length; s++){
            RideRequest[] base = generateDataset(sizes[s], graph, passengerTable, driverTable);

            // RANDOM
            RideRequest[] random = shuffle(Sorting.copyArray(base));
            // NEARLY SORTED
            RideRequest[] nearly = Sorting.copyArray(base);
            Sorting.mergeSort(nearly, 0, nearly.length - 1);
            Sorting.resetCount();
            displacePercent(nearly, 10);
            // REVERSED
            RideRequest[] reversed = Sorting.copyArray(base);
            Sorting.mergeSort(reversed, 0, reversed.length - 1);
            Sorting.resetCount();
            reverse(reversed);
            System.out.println("\n--- Size: " + sizes[s] + " ---");
            for(int c = 0; c < conditions.length; c++){
                RideRequest[] dataset;
                if(c == 0) dataset = random;
                else if(c == 1) dataset = nearly;
                else dataset = reversed;

                RideRequest[] m = Sorting.copyArray(dataset);
                RideRequest[] q = Sorting.copyArray(dataset);
                Sorting.resetCount();
                //execution times are measured in nanoseconds using System.nanoTime()
                long ms = System.nanoTime(); Sorting.mergeSort(m, 0, m.length-1); long me = System.nanoTime(); 
                int mc = Sorting.getCompCount();
                Sorting.resetCount();
                long qs = System.nanoTime(); Sorting.quickSort(q, 0, q.length-1); long qe = System.nanoTime();
                int qc = Sorting.getCompCount();
                System.out.println(conditions[c] + " | Merge: " + (me-ms) + "ns (" + mc + " comps) | Quick: " + (qe-qs) + "ns (" + qc + " comps)");
            }
        }
    }

    //SHUFFLE-randomly rearranges dataset elements
    private static RideRequest[] shuffle(RideRequest[] arr){
        java.util.Random rng = new java.util.Random(42L);
        for(int i = arr.length-1; i > 0; i--){
            int j = rng.nextInt(i+1);
            RideRequest t = arr[i]; arr[i] = arr[j]; arr[j] = t;
        }
        return arr;
    }

    //Randomly swaps percentage of elements to create nearly sorted (for testing)
    private static void displacePercent(RideRequest[] arr, int pct){
        java.util.Random rng = new java.util.Random(42L);
        int swaps = arr.length * pct / 100; //no.of random swaps to perform
        for(int i = 0; i < swaps; i++){
            int a = rng.nextInt(arr.length), b = rng.nextInt(arr.length);
            RideRequest t = arr[a]; arr[a] = arr[b]; arr[b] = t; //swap two randomly selected elements
        }
    }

    //REVERSE-Revers the dataset to create a reverse sorted test case for sorting analysis
    private static void reverse(RideRequest[] arr){
        int l = 0, r = arr.length-1;
        while(l < r){ 
            RideRequest t = arr[l]; arr[l] = arr[r]; arr[r] = t; l++; r--; 
        }
    }
}    
