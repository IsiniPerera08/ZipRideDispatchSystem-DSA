public class Heap{
    private RideRequest[] heapArray;
    private int count;

    public Heap(int maxSize){
        if(maxSize<= 0){
            throw new IllegalArgumentException("heap size should be positive...");
        }
        heapArray= new RideRequest[maxSize];
        count= 0;
    }

    //returns true if no ride requests in heap
    public boolean isEmpty(){
        return count == 0;
    }

    //returns true if the heap is full
    public boolean isFull(){ 
        return count == heapArray.length; 
    } 

    //returns the no.of ride requests in heap
    public int getCount(){ 
        return count; 
    }

    //TRICKLE UP- heap order after insertion by moving a ride request 
    //              upward until the heap property is satisfied.
    private void trickleUp(int index) {
        RideRequest temp= heapArray[index];
        boolean finished= false;
        while (index > 0 && !finished) {
            int parentIdx= (index - 1)/2;
            if(temp.getPriority() > heapArray[parentIdx].getPriority()){
                // Move parent down
                heapArray[index] = heapArray[parentIdx];
                index= parentIdx;
            }else{
                finished= true;
            }
        }
        // Place temp in its correct position
        heapArray[index]= temp;
    }
    
    //TRICKLE DOWN- heap order after removal by moving a ride request 
    //            downward until the heap property is satisfied.
    private void trickleDown(int index) {
        RideRequest temp= heapArray[index];
        boolean finished= false;
        while(!finished){
            int leftChild= (index * 2)+1;
            int rightChild= (index * 2)+2;
 
            if(leftChild>=count){
                //no children then stops
                finished= true;
            }else{
                //find the larger child
                int largeChild= leftChild;
                if(rightChild < count) {
                    if(heapArray[rightChild].getPriority()>heapArray[leftChild].getPriority()) {
                        largeChild= rightChild;
                    }
                }
                if(heapArray[largeChild].getPriority()> temp.getPriority()) {
                    //move larger child up
                    heapArray[index]= heapArray[largeChild];
                    index= largeChild;
                }else{
                    finished= true;
                }
            }
        }
        //place temp in its correct position
        heapArray[index] = temp;
    }

    //INSERT- adds new ride request to heap and positions it
    //      according to its priority.
    public void insert(RideRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Ride request cannot be null...");
        }
        if (isFull()) {
            throw new IllegalStateException("Heap is full...");
        }
 
        // Place at end, increment count, then trickle up
        heapArray[count] = request;
        count++;
        trickleUp(count - 1);

        System.out.println("\nInserting...\n "+ request.getPassengerName()+ " (Priority: "+ request.getPriority()+ ")");
        printHeap();
    }

    //PEEK- returns the highest priority ride request without removing it
    public RideRequest peek(){
        if(isEmpty()){
            throw new IllegalStateException("Heap is empty...");
        }
        return heapArray[0];
    }

    //REMOVE FOR DISPATCH-removes and returns the highest-priority ride request for ride dispatching.
    public RideRequest removeMax(){
        if(isEmpty()){
            throw new IllegalStateException("Heap is empty...");
        }
        RideRequest maxItem = heapArray[0];
        count--;
        heapArray[0] = heapArray[count];
        heapArray[count] = null;
        
        if(!isEmpty()){
            trickleDown(0);
        }
        System.out.println("\n[EXTRACT PRIORITY] Dispatching: "+ maxItem.getPassengerName()+ " (Priority: "+ maxItem.getPriority()+ ") | Driver: "+ maxItem.getDriverNameAssigned());        
        printHeap();
        return maxItem;
    }

    //PRINTING THE HEAP- diplays in array order, shows priorities and scheduling info
    public void printHeap() {
        System.out.println("Heap state [" + count + " requests]:");
        if(isEmpty()){
            System.out.println("  (empty)");
            return;
        }
        int i = 0;
        while(i < count){
            RideRequest ride = heapArray[i];
            System.out.println("[" + i + "] "+ ride.getPassengerName()+ " | Tier: " + ride.getMembershipTier()+ " | Time: " + ride.getPickupTime()+ " | Priority: "+ String.format("%.2f", ride.getPriority()));
            i++;
        }
    }

    //wrapper method used to display the current heap state
    public void displayHeap() {
        printHeap();
    }

    //returns the underlying heap array for testing and analysis
    public RideRequest[] getHeapArray(){
        return heapArray;
    }

    // Rebuild heap order after a priority change
    public void rebuildHeap(){
        for(int i = (count / 2) - 1; i >= 0; i--){
            trickleDown(i);
        }
    }
}
