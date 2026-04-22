package busmanagement;

public class Passenger {
    private String passengerID;
    private String name;
    private String phoneNumber;
    private String email;

    public Passenger() {
        this.passengerID = "P000";
        this.name = "Unknown";
        this.phoneNumber = "0000000000";
        this.email = "unknown@email.com";
    }

    public Passenger(String passengerID, String name, String phoneNumber, String email) {
        this.passengerID = passengerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPassengerID() { return passengerID; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    public void setPassengerID(String passengerID) { this.passengerID = passengerID; }
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerID='" + passengerID + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
