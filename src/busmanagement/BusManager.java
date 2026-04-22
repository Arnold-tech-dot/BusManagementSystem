package busmanagement;

import java.util.ArrayList;
import java.util.List;

public class BusManager extends Vehicle implements Bookable, Trackable {
    private String driverName;
    private String busNumber;
    private String routeAssigned;
    private List<Integer> bookedSeats;
    private List<String> bookingIDs;

    // Default constructor
    public BusManager() {
        super();
        this.driverName = "Unassigned";
        this.busNumber = "BUS000";
        this.routeAssigned = "None";
        this.bookedSeats = new ArrayList<>();
        this.bookingIDs = new ArrayList<>();
    }

    // Parameterized constructor
    public BusManager(String vehicleID, String vehicleName, int capacity, String fuelType,
                      String currentLocation, String status,
                      String driverName, String busNumber, String routeAssigned) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status);
        this.driverName = driverName;
        this.busNumber = busNumber;
        this.routeAssigned = routeAssigned;
        this.bookedSeats = new ArrayList<>();
        this.bookingIDs = new ArrayList<>();
    }

    // Getters
    public String getDriverName() { return driverName; }
    public String getBusNumber() { return busNumber; }
    public String getRouteAssigned() { return routeAssigned; }
    public List<Integer> getBookedSeats() { return bookedSeats; }

    // Setters
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setBusNumber(String busNumber) { this.busNumber = busNumber; }
    public void setRouteAssigned(String routeAssigned) { this.routeAssigned = routeAssigned; }

    // ── Vehicle abstract method implementations ──────────────────────────────

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[BusManager] Bus " + busNumber + " has started.");
    }

    @Override
    public void stopVehicle() {
        setStatus("Stopped");
        System.out.println("[BusManager] Bus " + busNumber + " has stopped.");
    }

    @Override
    public double calculateFuelConsumption() {
        // Base consumption: 0.3 liters per km per 10 capacity units
        double consumption = (getCapacity() / 10.0) * 0.3;
        System.out.println("[BusManager] Estimated fuel consumption: " + consumption + " L/km");
        return consumption;
    }

    @Override
    public boolean checkAvailability() {
        boolean available = "Available".equalsIgnoreCase(getStatus()) || "Running".equalsIgnoreCase(getStatus());
        System.out.println("[BusManager] Bus " + busNumber + " availability: " + available);
        return available;
    }

    @Override
    public void assignRoute(String route) {
        this.routeAssigned = route;
        System.out.println("[BusManager] Route '" + route + "' assigned to bus " + busNumber);
    }

    @Override
    public void updateLocation(String newLocation) {
        setCurrentLocation(newLocation);
        System.out.println("[BusManager] Bus " + busNumber + " location updated to: " + newLocation);
    }

    @Override
    public void performMaintenanceCheck() {
        System.out.println("[BusManager] Performing maintenance check on bus " + busNumber + "...");
        System.out.println("  - Engine: OK");
        System.out.println("  - Brakes: OK");
        System.out.println("  - Tyres: OK");
        System.out.println("  - Lights: OK");
        System.out.println("[BusManager] Maintenance check complete.");
    }

    @Override
    public String generateVehicleReport() {
        return "=== Vehicle Report ===\n" +
                "Bus Number : " + busNumber + "\n" +
                "Vehicle ID : " + getVehicleID() + "\n" +
                "Name       : " + getVehicleName() + "\n" +
                "Capacity   : " + getCapacity() + "\n" +
                "Fuel Type  : " + getFuelType() + "\n" +
                "Location   : " + getCurrentLocation() + "\n" +
                "Status     : " + getStatus() + "\n" +
                "Driver     : " + driverName + "\n" +
                "Route      : " + routeAssigned + "\n" +
                "Booked     : " + bookedSeats.size() + "/" + getCapacity() + " seats\n";
    }

    // ── Bookable interface implementations ───────────────────────────────────

    @Override
    public boolean bookSeat(Passenger passenger, int seatNumber) {
        if (bookedSeats.size() >= getCapacity()) {
            System.out.println("[ERROR] Bus " + busNumber + " is fully booked (overbooking prevented).");
            return false;
        }
        if (bookedSeats.contains(seatNumber)) {
            System.out.println("[ERROR] Seat " + seatNumber + " is already booked.");
            return false;
        }
        if (seatNumber < 1 || seatNumber > getCapacity()) {
            System.out.println("[ERROR] Invalid seat number: " + seatNumber);
            return false;
        }
        bookedSeats.add(seatNumber);
        System.out.println("[Booking] Seat " + seatNumber + " booked for " + passenger.getName());
        return true;
    }

    @Override
    public boolean cancelBooking(String bookingID) {
        if (bookingIDs.contains(bookingID)) {
            bookingIDs.remove(bookingID);
            System.out.println("[Booking] Booking " + bookingID + " cancelled.");
            return true;
        }
        System.out.println("[ERROR] Booking ID " + bookingID + " not found.");
        return false;
    }

    @Override
    public double calculateFare(String routeID) {
        // Base fare calculation
        double baseFare = 5.0;
        System.out.println("[Fare] Calculated fare for route " + routeID + ": $" + baseFare);
        return baseFare;
    }

    // ── Trackable interface implementations ──────────────────────────────────

    @Override
    public String trackLocation() {
        String location = getCurrentLocation();
        System.out.println("[Tracking] Bus " + busNumber + " is currently at: " + location);
        return location;
    }

    @Override
    public void updateStatus(String newStatus) {
        setStatus(newStatus);
        System.out.println("[Status] Bus " + busNumber + " status updated to: " + newStatus);
    }

    @Override
    public String toString() {
        return "BusManager{" +
                "vehicleID='" + getVehicleID() + '\'' +
                ", vehicleName='" + getVehicleName() + '\'' +
                ", capacity=" + getCapacity() +
                ", fuelType='" + getFuelType() + '\'' +
                ", currentLocation='" + getCurrentLocation() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", driverName='" + driverName + '\'' +
                ", busNumber='" + busNumber + '\'' +
                ", routeAssigned='" + routeAssigned + '\'' +
                '}';
    }
}
