public class Driver{
    private int driverId;
    private String driverName;
    private GraphNode currLocation;
    private String availabilityStatus;

    //CONSTRUCTOR
    public Driver(int driverId, String driverName, GraphNode currLocation, String availabilityStatus){
        if(driverId<=0){
            throw new IllegalArgumentException("Driver Id invalid...");
        }if(driverName == null || driverName.trim().equals("")){
            throw new IllegalArgumentException("Driver name cannot be empty.");
        }if(currLocation == null){
            throw new IllegalArgumentException("Current location cannot be null.");
        }if(availabilityStatus == null || (!availabilityStatus.equals("Available") && 
        !availabilityStatus.equals("Busy") && !availabilityStatus.equals("Offline"))){
        throw new IllegalArgumentException("Status must be -> Available / Busy / Offline");
    }
    this.driverId= driverId;
    this.driverName= driverName;
    this.currLocation= currLocation;
    this.availabilityStatus= availabilityStatus;
    }
    
    //GETTERS
    public int getDriverId(){
        return driverId;
    }

    public String getDriverName(){
        return driverName;
    }

    public GraphNode getCurrLocation(){
        return currLocation;
    }

    public String getAvailabilityStatus(){
        return availabilityStatus;
    }

    //SETTERS
    //update the drivers availability status after validating
    public void setAvailabilityStatus(String status){
        if(status == null){
            throw new IllegalArgumentException("Status must be -> Available / Busy / Offline");
        }
        status = status.trim();
        if(status.equalsIgnoreCase("available")){
            this.availabilityStatus = "Available";
        }
        else if(status.equalsIgnoreCase("busy")){
            this.availabilityStatus = "Busy";
        }
        else if(status.equalsIgnoreCase("offline")){
            this.availabilityStatus = "Offline";
        }
        else{
            throw new IllegalArgumentException("Status must be -> Available / Busy / Offline");
        }
    }

    //curr location of driver updated
    public void setCurrLocation(GraphNode location){
        if(location == null){
            throw new IllegalArgumentException("Location cannot be null.");
        }
        this.currLocation = location;
    }

    @Override
    public String toString(){
        return "DriverId: "+driverId+ " | driverName: "+driverName+ " | location: "+ currLocation.getLabel()+ " | status: " +availabilityStatus;
    }
}
