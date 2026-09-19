public class Sorting {
    private static int compCount = 0; //comparison counter used to measure algorithm performance
    public static void resetCount(){ //resets comparison count before a new sorting run
        compCount = 0;
    }
    public static int getCompCount(){ //returns the total number of comparisons performed
        return compCount;
    }

    //  MERGE SORT (Top-Down): Recursively splits array in half, sorts each half, then merges.
    //  Time:O(n log n) all cases -> Space: O(n) for temp arrays.
    public static void mergeSort(RideRequest[] arr, int left, int right){
        if(left < right){
            int mid= left+(right - left)/2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(RideRequest[]arr, int left,int mid,int right) {
        int leftSize= mid - left + 1;
        int rightSize= right - mid;

        // Temporary arrays for left and right halves
        RideRequest[]leftArr= new RideRequest[leftSize];
        RideRequest[]rightArr= new RideRequest[rightSize];

        for(int i= 0; i<leftSize; i++){
            leftArr[i]= arr[left +i];
        }
        for(int j= 0; j<rightSize; j++){
            rightArr[j]= arr[mid +1 + j];
        }
        int i= 0, j= 0, k= left;
        while(i<leftSize && j<rightSize){
            compCount++;
            if(leftArr[i].getPickupTime()<= rightArr[j].getPickupTime()){
                arr[k]= leftArr[i];
                i++;
            }else{
                arr[k]= rightArr[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        while(i< leftSize){
            arr[k]= leftArr[i];
            i++;
            k++;
        }
        while(j < rightSize){
            arr[k]= rightArr[j];
            j++;
            k++;
        }
    }

    //  QUICK SORT (Median-of-Three Pivot)- sorts ride requests using a median-of-three pivot strategy
    //                        to improve partition quality and reduce worst-case behaviour.
    public static void quickSort(RideRequest[]arr,int left,int right){
        if (right - left <= 0){
            return; // 0 or 1 elements
        }
        // 2 elements to compare and swap directly
        if (right - left == 1) {
            compCount++;
            if (arr[left].getPickupTime() > arr[right].getPickupTime()){
                swap(arr, left, right);
            }
            return;
        }

        int pivotPos= partition(arr, left, right);
        quickSort(arr,left, pivotPos- 1);
        quickSort(arr, pivotPos+1,right);
    }


    //PARTITION- rearranges elements around the selected pivot and 
    //         returns the pivots final position
    private static int partition(RideRequest[]arr,int left,int right){
        int mid= left+(right-left)/2;
        // Sort arr[left], arr[mid], arr[right] to find median
        compCount++;
        if(arr[left].getPickupTime()>arr[mid].getPickupTime()){
            swap(arr, left, mid);
        }
        compCount++;
        if(arr[left].getPickupTime() > arr[right].getPickupTime()){
            swap(arr, left, right);
        }
        compCount++;
        if(arr[mid].getPickupTime() > arr[right].getPickupTime()){
            swap(arr, mid, right);
        } 

        // After above: arr[left] <= arr[mid] <= arr[right]
        // Place pivot (median = arr[mid]) at right-1
        swap(arr,mid,right - 1);
        int pivotVal= arr[right-1].getPickupTime();

        // Partition between left+1 and right-2
        // arr[left] <= pivot acts as left sentinel (stops j-scan)
        // arr[right] >= pivot acts as right sentinel (stops i-scan)
        int i= left;
        int j= right-1;
        boolean done= false;

        while(!done){
            // Scan right until element >= pivot
            do{
                i++;
                compCount++;
            }while(arr[i].getPickupTime() < pivotVal);
            // Scan left until element <= pivot
            do{
                j--;
                compCount++;
            }while(j > left && arr[j].getPickupTime() > pivotVal);
            if (i >= j) {
                done = true;
            }else{
                swap(arr, i, j);
            }
        }
        // Restore pivot to final position
        swap(arr, i, right - 1);
        return i;
    }

    //SWAP-exchanges the positios of 2 ride requests in the arr
    private static void swap(RideRequest[]arr,int i,int j) {
        RideRequest temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ------------------------------------------------------------------ //
    //  HELPERS
    // ------------------------------------------------------------------ //

    // Returns copy of the array (same RideRequest objects)
    public static RideRequest[]copyArray(RideRequest[] original) {
        RideRequest[]copy =new RideRequest[original.length];
        for(int i= 0; i<original.length; i++) {
            copy[i]=original[i];
        }
        return copy;
    }

    //returns true if the array is sorted by pickupTime ascending
    public static boolean isSorted(RideRequest[] arr) {
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i].getPickupTime() > arr[i + 1].getPickupTime()){
                return false;
            }
        }
        return true;
    }

    //prints the first & last few elements of the array
    public static void printFirstAndLast(RideRequest[] arr, int n){
        System.out.println("First " + n + " records:");
        for(int i = 0; i < n && i < arr.length; i++){
            System.out.println("  [" + i + "] PickupTime=" + arr[i].getPickupTime()+ " mins | "+ arr[i].getPassengerName());        
        }
        System.out.println("Last " + n + " records:");
        int start = arr.length - n;
        if(start < 0){
            start = 0;
        }
        for(int i = start; i < arr.length; i++){
            System.out.println("  [" + i + "] PickupTime="+ arr[i].getPickupTime()+ " mins | "+ arr[i].getPassengerName());
        }
    }

    //GENERATE DATA SET
    public static RideRequest[] generateDataset(int size,Graph graph,HashTable passengerTable,HashTable driverTable){
        RideRequest[] data =new RideRequest[size];
        HashEntry[] passengers =passengerTable.getHashArray();
        HashEntry[] drivers =driverTable.getHashArray();
        int pIndex = 0;
        int dIndex = 0;
        for(int i = 0; i < size; i++){
            while(passengers[pIndex % passengers.length] == null|| passengers[pIndex % passengers.length].getDeleted()){
                pIndex++;
            }
            while(drivers[dIndex % drivers.length] == null || drivers[dIndex % drivers.length].getDeleted()){
                dIndex++;
            }
            
            Passenger p =(Passenger)passengers[pIndex % passengers.length].getVal();
            Driver d =(Driver)drivers[dIndex % drivers.length].getVal();
            int pickupTime =GraphAlgo.getShortestDistance(graph,d.getCurrLocation().getLabel(),p.getPickUpLocation());
            if(pickupTime== Integer.MAX_VALUE){
                pickupTime=30;
            }
            data[i] =new RideRequest(p.getPassengerId(),p.getPassengerName(),p.getMembershipTier(),pickupTime,d.getDriverId(),d.getDriverName());
            pIndex+=3;
            dIndex+=5;   
        }
        return data;
    }

}
