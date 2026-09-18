// Author : Isini Ayansa Perera
//Student ID : 23601321
// Date : 2026
//File: HashEntry.java
//Purpose: Represents a single entry in the hash table, stores a key-value pair &
//          a deletion flag used for open-addressing hash table operations.
// reference- lec notes and practical submission

public class HashEntry {

    private int key;
    private Object val;
    private boolean deleted;

    public HashEntry(int key, Object val){
        this.key= key;
        this.val= val;
        deleted= false;
    } 
    
    //GETTERS
    public int getKey(){
        return key;
    }

    public Object getVal(){
        return val;
    }

    public boolean getDeleted(){
        return deleted;
    }

    public void setDeleted(boolean deleted){
        this.deleted= deleted;
    }

    //SETTERS
    public void setVal(Object val){
        this.val = val;
    }


    @Override
    public String toString(){
        if(deleted){
            return "Key: " + key + " [DELETED]";
        }
        return "Key: " + key + " | " + val.toString();
    }
}