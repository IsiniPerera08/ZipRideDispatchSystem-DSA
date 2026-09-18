//Name: Isini Ayansa Perera
//Student Id: 23601321
//File: RideRequest.java
//Purpose: Represents a ride request and calculates its priority based on 
//         membership tier and pickup time for heap-based scheduling.

public class RideRequest {

    private int passengerId;
    private String passengerName;
    private int membershipTier;
    private int pickupTime;
    private int driverIdAssigned;
    private String driverNameAssigned;
    private double priority;

    // CONSTRUCTOR- creates a ride request and calculates its initial priority
    public RideRequest(int passengerId,String passengerName,int membershipTier,int pickupTime, int driverIdAssigned,String driverNameAssigned){
        if(passengerId <= 0){
            throw new IllegalArgumentException("Passenger ID must be greater than 0...");
        }
        if(passengerName == null|| passengerName.trim().equals("")){
            throw new IllegalArgumentException("Passenger name cannot be empty...");
        }
        if(membershipTier < 1|| membershipTier > 5){
            throw new IllegalArgumentException("Membership tier must be between 1 and 5...");
        }
        if(pickupTime <= 0){
            throw new IllegalArgumentException("Pickup time must be a positive...");
        }
        if(driverIdAssigned <= 0){
            throw new IllegalArgumentException("Driver ID must be greater than 0...");
        }
        if(driverNameAssigned == null|| driverNameAssigned.trim().equals("")){
            throw new IllegalArgumentException("Driver name cannot be empty...");
        }
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.membershipTier = membershipTier;
        this.pickupTime = pickupTime;
        this.driverIdAssigned = driverIdAssigned;
        this.driverNameAssigned = driverNameAssigned;
        recalculatePriority();
    }

    // ACCESSORS
    public int getPassengerId(){
        return passengerId;
    }
    public String getPassengerName(){
        return passengerName;
    }
    public int getMembershipTier(){
        return membershipTier;
    }
    public int getPickupTime(){
        return pickupTime;
    }
    public int getDriverIdAssigned(){
        return driverIdAssigned;
    }
    public String getDriverNameAssigned(){
        return driverNameAssigned;
    }
    public double getPriority(){
        return priority;
    }

    // MUTATORS
    //updates the membership tier and recalculates priority
    public void setMembershipTier(int membershipTier){
        if(membershipTier < 1|| membershipTier > 5){
            throw new IllegalArgumentException("Membership tier must be between 1 and 5...");
        }
        this.membershipTier = membershipTier;
        recalculatePriority();
    }
    
    //updates the pickup time and recalculates priority.
    public void setPickupTime(int pickupTime){
        if(pickupTime <= 0){
            throw new IllegalArgumentException("Pickup time must be a positive...");
        }
        this.pickupTime = pickupTime;
        recalculatePriority();
    }
    
    //assign a driver associated with the ride request
    public void setAssignedDriver(int driverId,String driverName){
        if(driverId <= 0){
            throw new IllegalArgumentException("Driver ID must be greater than 0...");
        }
        if(driverName == null|| driverName.trim().equals("")){
            throw new IllegalArgumentException("Driver name cannot be empty.");
        }
        driverIdAssigned = driverId;
        driverNameAssigned = driverName;
    }

    // RECALCULATE PRIORITY- computes ride priority using the assignment formula:
    //          Priority = (6 - MembershipTier) + (1000 / PickupTime)
    private void recalculatePriority(){
        priority =(6 - membershipTier)+ (1000.0 / pickupTime);
    }

    // DISPLAY
    @Override
    public String toString(){
        return "PassengerID: "+passengerId+" | Name: "+passengerName+" | Tier: "+membershipTier+" | Pickup Time: "+pickupTime+" mins"
            +" | Driver: "+driverNameAssigned+" ("+driverIdAssigned+")"+" | Priority: "+String.format("%.2f", priority);
    }
}
