package busmanagement;

public abstract class Vehicle {
    private String vehicleID;
    private String vehicleName;
    private int capacity;
    private String fuelType;
    private String currentLocation;
    private String status;

    // Default constructor
    public Vehicle() {
        this.vehicleID = "V000";
        this.vehicleName = "Unknown";
        this.capacity = 0;
        this.fuelType = "Unknown";
        this.currentLocation = "Depot";
        this.status = "Inactive";
    }

    // Parameterized constructor
    public Vehicle(String vehicleID, String vehicleName, int capacity, String fuelType,
                   String currentLocation, String status) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.capacity = capacity;
        this.fuelType = fuelType;
        this.currentLocation = currentLocation;
        this.status = status;
    }

    // Getters
    public String getVehicleID() { return vehicleID; }
    public String getVehicleName() { return vehicleName; }
    public int getCapacity() { return capacity; }
    public String getFuelType() { return fuelType; }
    public String getCurrentLocation() { return currentLocation; }
    public String getStatus() { return status; }

    // Setters
    public void setVehicleID(String vehicleID) { this.vehicleID = vehicleID; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public void setStatus(String status) { this.status = status; }

    // Abstract methods
    public abstract void startVehicle();
    public abstract void stopVehicle();
    public abstract double calculateFuelConsumption();
    public abstract boolean checkAvailability();
    public abstract void assignRoute(String route);
    public abstract void updateLocation(String newLocation);
    public abstract void performMaintenanceCheck();
    public abstract String generateVehicleReport();

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", capacity=" + capacity +
                ", fuelType='" + fuelType + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
