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
