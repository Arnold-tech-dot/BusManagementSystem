package busmanagement;

// ─────────────────────────────────────────────────────────────
//  1. CityBus
// ─────────────────────────────────────────────────────────────
class CityBus extends BusManager {
    private boolean hasAirConditioning;

    public CityBus() {
        super();
        this.hasAirConditioning = false;
    }

    public CityBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                   String currentLocation, String status,
                   String driverName, String busNumber, String routeAssigned,
                   boolean hasAirConditioning) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.hasAirConditioning = hasAirConditioning;
    }

    public boolean isHasAirConditioning() { return hasAirConditioning; }
    public void setHasAirConditioning(boolean hasAirConditioning) {
        this.hasAirConditioning = hasAirConditioning;
    }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[CityBus] City bus " + getBusNumber() + " started on urban route.");
        if (hasAirConditioning) System.out.println("[CityBus] Air conditioning activated.");
    }

    @Override
    public double calculateFuelConsumption() {
        // City buses stop frequently – higher consumption
        double consumption = (getCapacity() / 10.0) * 0.45;
        System.out.println("[CityBus] City fuel consumption (stop-start): " + consumption + " L/km");
        return consumption;
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type            : City Bus\n" +
                "Air Conditioning: " + (hasAirConditioning ? "Yes" : "No") + "\n";
    }

    @Override
    public double calculateFare(String routeID) {
        double fare = 1.50; // flat city fare
        System.out.println("[CityBus] City fare for route " + routeID + ": $" + fare);
        return fare;
    }

    @Override
    public String toString() {
        return "CityBus{" + super.toString() +
                ", hasAirConditioning=" + hasAirConditioning + '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  2. ExpressBus
// ─────────────────────────────────────────────────────────────
class ExpressBus extends BusManager {
    private int numberOfStops;

    public ExpressBus() {
        super();
        this.numberOfStops = 0;
    }

    public ExpressBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                      String currentLocation, String status,
                      String driverName, String busNumber, String routeAssigned,
                      int numberOfStops) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.numberOfStops = numberOfStops;
    }

    public int getNumberOfStops() { return numberOfStops; }
    public void setNumberOfStops(int numberOfStops) { this.numberOfStops = numberOfStops; }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[ExpressBus] Express bus " + getBusNumber() + " started. Limited stops: " + numberOfStops);
    }

    @Override
    public double calculateFuelConsumption() {
        // Fewer stops = better efficiency
        double consumption = (getCapacity() / 10.0) * 0.25;
        System.out.println("[ExpressBus] Express fuel consumption: " + consumption + " L/km");
        return consumption;
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type          : Express Bus\n" +
                "Number of Stops: " + numberOfStops + "\n";
    }

    @Override
    public double calculateFare(String routeID) {
        double fare = 3.00;
        System.out.println("[ExpressBus] Express fare for route " + routeID + ": $" + fare);
        return fare;
    }

    @Override
    public String toString() {
        return "ExpressBus{" + super.toString() +
                ", numberOfStops=" + numberOfStops + '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  3. LuxuryBus
// ─────────────────────────────────────────────────────────────
class LuxuryBus extends BusManager {
    private boolean hasWifi;

    public LuxuryBus() {
        super();
        this.hasWifi = false;
    }

    public LuxuryBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                     String currentLocation, String status,
                     String driverName, String busNumber, String routeAssigned,
                     boolean hasWifi) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.hasWifi = hasWifi;
    }

    public boolean isHasWifi() { return hasWifi; }
    public void setHasWifi(boolean hasWifi) { this.hasWifi = hasWifi; }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[LuxuryBus] Luxury bus " + getBusNumber() + " started. Premium service active.");
        if (hasWifi) System.out.println("[LuxuryBus] Wi-Fi enabled for passengers.");
    }

    @Override
    public void performMaintenanceCheck() {
        System.out.println("[LuxuryBus] Performing PREMIUM maintenance on bus " + getBusNumber() + "...");
        System.out.println("  - Engine         : OK");
        System.out.println("  - Brakes         : OK");
        System.out.println("  - Interior/Seats : OK");
        System.out.println("  - Entertainment  : OK");
        System.out.println("  - Wi-Fi System   : " + (hasWifi ? "OK" : "N/A"));
        System.out.println("[LuxuryBus] Premium maintenance check complete.");
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type   : Luxury Bus\n" +
                "Wi-Fi  : " + (hasWifi ? "Yes" : "No") + "\n";
    }

    @Override
    public double calculateFare(String routeID) {
        double fare = 10.00;
        System.out.println("[LuxuryBus] Luxury fare for route " + routeID + ": $" + fare);
        return fare;
    }

    @Override
    public String toString() {
        return "LuxuryBus{" + super.toString() +
                ", hasWifi=" + hasWifi + '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  4. SchoolBus
// ─────────────────────────────────────────────────────────────
class SchoolBus extends BusManager {
    private String schoolName;

    public SchoolBus() {
        super();
        this.schoolName = "Unknown School";
    }

    public SchoolBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                     String currentLocation, String status,
                     String driverName, String busNumber, String routeAssigned,
                     String schoolName) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.schoolName = schoolName;
    }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[SchoolBus] School bus " + getBusNumber() + " for " + schoolName + " has started.");
        System.out.println("[SchoolBus] Safety protocols engaged for student transport.");
    }

    @Override
    public boolean checkAvailability() {
        // School buses only available during school hours
        boolean available = "Available".equalsIgnoreCase(getStatus());
        System.out.println("[SchoolBus] School bus availability (school hours only): " + available);
        return available;
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type        : School Bus\n" +
                "School Name : " + schoolName + "\n";
    }

    @Override
    public double calculateFare(String routeID) {
        double fare = 0.50; // subsidised school fare
        System.out.println("[SchoolBus] School fare for route " + routeID + ": $" + fare);
        return fare;
    }

    @Override
    public String toString() {
        return "SchoolBus{" + super.toString() +
                ", schoolName='" + schoolName + '\'' + '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  5. TouristBus
// ─────────────────────────────────────────────────────────────
class TouristBus extends BusManager {
    private String tourPackage;

    public TouristBus() {
        super();
        this.tourPackage = "Standard";
    }

    public TouristBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                      String currentLocation, String status,
                      String driverName, String busNumber, String routeAssigned,
                      String tourPackage) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.tourPackage = tourPackage;
    }

    public String getTourPackage() { return tourPackage; }
    public void setTourPackage(String tourPackage) { this.tourPackage = tourPackage; }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[TouristBus] Tourist bus " + getBusNumber() + " started. Tour package: " + tourPackage);
        System.out.println("[TouristBus] Welcome aboard! Scenic route ahead.");
    }

    @Override
    public void updateLocation(String newLocation) {
        setCurrentLocation(newLocation);
        System.out.println("[TouristBus] Now arriving at tourist attraction: " + newLocation);
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type         : Tourist Bus\n" +
                "Tour Package : " + tourPackage + "\n";
    }

    @Override
    public double calculateFare(String routeID) {
        double fare = 15.00;
        System.out.println("[TouristBus] Tourist package fare for route " + routeID + ": $" + fare);
        return fare;
    }

    @Override
    public String toString() {
        return "TouristBus{" + super.toString() +
                ", tourPackage='" + tourPackage + '\'' + '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  6. ElectricBus
// ─────────────────────────────────────────────────────────────
class ElectricBus extends BusManager {
    private double batteryCapacityKWh;

    public ElectricBus() {
        super();
        this.batteryCapacityKWh = 0.0;
    }

    public ElectricBus(String vehicleID, String vehicleName, int capacity, String fuelType,
                       String currentLocation, String status,
                       String driverName, String busNumber, String routeAssigned,
                       double batteryCapacityKWh) {
        super(vehicleID, vehicleName, capacity, fuelType, currentLocation, status,
              driverName, busNumber, routeAssigned);
        this.batteryCapacityKWh = batteryCapacityKWh;
    }

    public double getBatteryCapacityKWh() { return batteryCapacityKWh; }
    public void setBatteryCapacityKWh(double batteryCapacityKWh) {
        this.batteryCapacityKWh = batteryCapacityKWh;
    }

    @Override
    public void startVehicle() {
        setStatus("Running");
        System.out.println("[ElectricBus] Electric bus " + getBusNumber() + " started silently.");
        System.out.println("[ElectricBus] Battery capacity: " + batteryCapacityKWh + " kWh. Zero emissions.");
    }

    @Override
    public double calculateFuelConsumption() {
        // Electric – consumes kWh, not fuel
        double consumption = 0.0;
        System.out.println("[ElectricBus] Zero fuel consumption. Energy usage: ~" +
                (batteryCapacityKWh * 0.1) + " kWh/km");
        return consumption;
    }

    @Override
    public void performMaintenanceCheck() {
        System.out.println("[ElectricBus] Electric maintenance check on bus " + getBusNumber() + "...");
        System.out.println("  - Battery Health  : OK (" + batteryCapacityKWh + " kWh)");
        System.out.println("  - Charging Port   : OK");
        System.out.println("  - Motor           : OK");
        System.out.println("  - Regenerative Braking: OK");
        System.out.println("[ElectricBus] Electric maintenance check complete.");
    }

    @Override
    public String generateVehicleReport() {
        return super.generateVehicleReport() +
                "Type              : Electric Bus\n" +
                "Battery Capacity  : " + batteryCapacityKWh + " kWh\n";
    }

    @Override
    public String toString() {
        return "ElectricBus{" + super.toString() +
                ", batteryCapacityKWh=" + batteryCapacityKWh + '}';
    }
}
