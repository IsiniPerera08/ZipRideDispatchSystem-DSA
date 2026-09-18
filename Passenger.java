// Author : Isini  Ayansa Perera
//Student ID : 23601321
// Date  : 2026
// File : Passenger.java
//Purpose  : Stores passenger details including ID, name, pickup location, 
//           and membership tier used for ride prioritisation.

public class Passenger {
    private int passengerId;
    private String passengerName;
    private String pickLocation;
    private int membershipTier;

    public Passenger(int passengerId, String passengerName, String pickLocation, int membershipTier){
        if(passengerId<=0){
            throw new IllegalArgumentException("Invalid passenger Id...");
        }if(passengerName == null|| passengerName.trim().equals("")){
            throw new IllegalArgumentException("Passenger name cannot be empty...");
        }if(pickLocation == null || pickLocation.trim().equals("")){
            throw new IllegalArgumentException("Pickup location cannot be empty.");
        }if(membershipTier < 1 || membershipTier > 5){
            throw new IllegalArgumentException("Membership tier must be between 1 and 5.");
        }
        this.passengerId= passengerId;
        this.passengerName= passengerName;
        this.pickLocation= pickLocation;
        this.membershipTier= membershipTier;
    }

    //GETTERS
    public int getPassengerId(){
        return passengerId;
    }
    public String getPassengerName(){
        return passengerName;
    }
    public String getPickUpLocation(){
        return pickLocation;
    }

    public int getMembershipTier(){
        return membershipTier;
    }

    //SETTERS
    //updates the passengers membership tier after validation
    public void setMembershipTier(int membershipTier){
        if(membershipTier < 1 || membershipTier > 5){
            throw new IllegalArgumentException("Membership tier must be between 1 and 5...");
        }
        this.membershipTier = membershipTier;
    }

    @Override
    public String toString(){
        return "PassengerID: " +passengerId+ " | PassengerName: " +passengerName+ " | pickUp: "+pickLocation+ " | MembershipTier: "+membershipTier;
    }  
}
