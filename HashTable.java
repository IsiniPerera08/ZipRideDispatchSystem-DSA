import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HashTable {
    private HashEntry[] hashArray;
    private int count;

    public HashTable(int size){
        if(size <= 0){
            throw new IllegalArgumentException("Size must be greater than 0...");
        }
        hashArray = new HashEntry[size];
        count = 0;
    }

    private int hash(int key){
        return Math.abs(key) % hashArray.length;
    }


    // ACCESSORS
    public int getCount(){
        return count;
    }
    public int getTableSize(){
        return hashArray.length;
    }
    public HashEntry[] getHashArray(){
        return hashArray;
    }

    
    // INSERT- Adds a new key-value pair into the hash table, uses linear probing to resolve collisions.
    public void insert(int key, Object val){
        if(val == null){
            throw new IllegalArgumentException("Value cannot be null...");
        }
        if(count == hashArray.length){
            throw new IllegalStateException("Hash table is full...");
        }
        int hashIdx = hash(key);
        int firstDeleted = -1;
        while(hashArray[hashIdx] != null){
            // Show collision information
            System.out.println("Collision at index " + hashIdx);
            // Duplicate key found
            if(!hashArray[hashIdx].getDeleted() && hashArray[hashIdx].getKey() == key){
                System.out.println("ERROR: Duplicate key "+ key+ " already exists. Record rejected.");
                return;
            }
            // Remember first deleted slot
            if(hashArray[hashIdx].getDeleted() && firstDeleted == -1){
                firstDeleted = hashIdx;
            }
            //LINEAR PROBING
            hashIdx = (hashIdx + 1) % hashArray.length;
        }
        int insertIdx =(firstDeleted != -1) ? firstDeleted: hashIdx;
        hashArray[insertIdx] = new HashEntry(key, val);
        count++;
        
        // System.out.println("Inserted record with key "+ key+ " at index "+ insertIdx);
        // if(count % 10 == 0){
        //     System.out.printf("Load factor after %d inserts: %.2f%n",count,getLoadFactor());
        // }
    }

    // SEARCH-searcfhes for a record using its unique key, follows the same linear probing sequence used during insertion.
    public Object search(int key){
        Object foundVal = null;
        int hashIdx = hash(key);
        int startIdx = hashIdx;
        boolean finished = false;
        while(!finished){
            if(hashArray[hashIdx] == null){
                finished = true;
            }else if(!hashArray[hashIdx].getDeleted() &&
                    hashArray[hashIdx].getKey() == key){

                foundVal = hashArray[hashIdx].getVal();
                finished = true;
            }else{
                hashIdx = (hashIdx + 1) % hashArray.length;
                if(hashIdx == startIdx){
                    finished = true;
                }
            }
        }
        return foundVal;
    }

    // DELETE- marks a record as deleted while keeping the probing chain, 
    //              required for search and insertion operations
    public boolean delete(int key){
        boolean deleted = false;
        int hashIdx = hash(key);
        int startIdx = hashIdx;
        boolean finished = false;
        while(!finished){
            if(hashArray[hashIdx] == null){
                finished = true;
            }else if(!hashArray[hashIdx].getDeleted() && hashArray[hashIdx].getKey() == key){
                hashArray[hashIdx].setDeleted(true);
                count--;
                deleted = true;
                finished = true;
            }else{
                hashIdx = (hashIdx + 1) % hashArray.length;
                if(hashIdx == startIdx){
                    finished = true;
                }
            }
        }
        return deleted;
    }

    //HAS KEY- returns true if the specified key exists in the hash table
    public boolean keyExists(int key){
        return search(key) != null;
    }

    // LOAD FACTOR- calculates the current load factor of the hash table, used to monitor table
    public double getLoadFactor(){
        return (double) count / hashArray.length;
    }

    // DISPLAY TABLE -the entire table including empty, occupied, and deleted slots
    public void displayTable(){
        System.out.println("\nHash Table:");
        for(int i = 0; i < hashArray.length; i++){
            System.out.print(i + ": ");
            if(hashArray[i] == null){
                System.out.println("[EMPTY]");
            }else if(hashArray[i].getDeleted()){
                System.out.println("[DELETED]");
            }else{
                System.out.println(hashArray[i].toString());
            }
        }
    }

    // COLLISION DEMO- shows the hashIdxgenerated for a key and,
    //               displays the contents of the corresponding slot.
    public void showCollision(int key){
        int idx = hash(key);
        System.out.println( "Key " + key + " hashes to index " + idx);
        System.out.println("Slot " + idx + ": " +(hashArray[idx] == null ? "[EMPTY]" : hashArray[idx].toString()));
    }

    // RETURN ALL RECORDS- collects all records from hashtable and return as linked list
    public LinkedList getAll(){
        LinkedList result = new LinkedList();
        for(int i = 0; i < hashArray.length; i++){
            if(hashArray[i] != null && !hashArray[i].getDeleted()){
                result.insertLast(hashArray[i].getVal());
            }
        }
        return result;
    }

    //DISPLAY RECORDS- Displays all active records currently stored, in the hashtable
    public void displayRecords(){
        HashEntry[] entries = getHashArray();
        for (int i = 0; i < entries.length; i++){
            if (entries[i] != null && !entries[i].getDeleted()){
                System.out.println(entries[i].getVal());
            }
        }
    }


    // =========================================================
    // CSV LOADERS
    // =========================================================

    public void loadPassengersFromCSV(String filename){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String location = data[2].trim();
                int tier = Integer.parseInt(data[3].trim());
                insert(id,new Passenger(id,name,location,tier));
            }
            br.close();
            System.out.println("Passengers loaded successfully.");
        }catch(Exception e){
            System.out.println("Error loading passengers.csv : "+ e.getMessage());
        }
    }

    public void loadDriversFromCSV(String filename, Graph graph){
        try{
            BufferedReader br =new BufferedReader(new FileReader(filename));
            br.readLine();
            String line;
            while((line = br.readLine())!= null){
                String[] data = line.split(",");
                int id =Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String location = data[2].trim();
                String status = data[3].trim();
                insert(id,new Driver(id,name,graph.findVertex(location),status));
            }
            br.close();
            System.out.println("Drivers loaded successfully.");
        }catch(Exception e){
            System.out.println("Error loading drivers.csv : " + e.getMessage());
        }
    }

    // =========================================================
    // CSV SAVERS
    // =========================================================

    public void savePassengersToCSV(String filename){
        try{
            FileWriter fw =new FileWriter(filename);
            fw.write("PassengerID,PassengerName,PickupLocation,MembershipTier\n");
            HashEntry[] entries =getHashArray();
            for(int i = 0; i < entries.length; i++){
                if (entries[i] != null && !entries[i].getDeleted()){
                    Passenger p =(Passenger) entries[i].getVal();
                    fw.write(p.getPassengerId() + ","+ p.getPassengerName() + ","+ p.getPickUpLocation() + ","+ p.getMembershipTier()+ "\n");
                }
            }
            fw.close();
        }catch (IOException e){
            System.out.println("Error saving passengers.csv");
        }
    }

    public void saveDriversToCSV(String filename){
        try{
            FileWriter fw =new FileWriter(filename);
            fw.write("DriverID,DriverName,Location,Status\n");
            HashEntry[] entries =getHashArray();
            for(int i = 0; i < entries.length; i++){
                if (entries[i] != null && !entries[i].getDeleted()) {
                    Driver d =(Driver) entries[i].getVal();
                    fw.write(d.getDriverId() + ","+ d.getDriverName() + ","+ d.getCurrLocation().getLabel() + "," + d.getAvailabilityStatus() + "\n");                
                }
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Error saving drivers.csv");
        }
    }
}
