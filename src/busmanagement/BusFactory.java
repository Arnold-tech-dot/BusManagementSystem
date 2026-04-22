package busmanagement;

public class BusFactory {

    /**
     * Creates a BusManager subclass based on the bus type string.
     * Demonstrates polymorphism – caller uses BusManager reference.
     *
     * @param busType   one of: CITY, EXPRESS, LUXURY, SCHOOL, TOURIST, ELECTRIC
     * @param vehicleID unique vehicle ID
     * @param vehicleName display name
     * @param capacity  seating capacity
     * @param fuelType  fuel type
     * @param location  current location
     * @param driverName assigned driver
     * @param busNumber bus number plate
     * @param routeAssigned assigned route
     * @param extraDetail  extra attribute (e.g., school name, wifi "true/false", battery capacity)
     * @return the appropriate BusManager subclass instance
     */
    public static BusManager createBus(String busType,
                                       String vehicleID,
                                       String vehicleName,
                                       int capacity,
                                       String fuelType,
                                       String location,
                                       String driverName,
                                       String busNumber,
                                       String routeAssigned,
                                       String extraDetail) {

        switch (busType.toUpperCase().trim()) {
            case "CITY":
                boolean ac = Boolean.parseBoolean(extraDetail);
                System.out.println("[BusFactory] Creating CityBus: " + busNumber);
                return new CityBus(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned, ac);

            case "EXPRESS":
                int stops = 0;
                try { stops = Integer.parseInt(extraDetail); } catch (NumberFormatException ignored) {}
                System.out.println("[BusFactory] Creating ExpressBus: " + busNumber);
                return new ExpressBus(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned, stops);

            case "LUXURY":
                boolean wifi = Boolean.parseBoolean(extraDetail);
                System.out.println("[BusFactory] Creating LuxuryBus: " + busNumber);
                return new LuxuryBus(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned, wifi);

            case "SCHOOL":
                System.out.println("[BusFactory] Creating SchoolBus: " + busNumber);
                return new SchoolBus(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned,
                        extraDetail.isEmpty() ? "Unknown School" : extraDetail);

            case "TOURIST":
                System.out.println("[BusFactory] Creating TouristBus: " + busNumber);
                return new TouristBus(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned,
                        extraDetail.isEmpty() ? "Standard" : extraDetail);

            case "ELECTRIC":
                double battery = 0.0;
                try { battery = Double.parseDouble(extraDetail); } catch (NumberFormatException ignored) {}
                System.out.println("[BusFactory] Creating ElectricBus: " + busNumber);
                return new ElectricBus(vehicleID, vehicleName, capacity, "Electric",
                        location, "Available", driverName, busNumber, routeAssigned, battery);

            default:
                System.out.println("[BusFactory] Unknown bus type '" + busType + "'. Returning generic BusManager.");
                return new BusManager(vehicleID, vehicleName, capacity, fuelType,
                        location, "Available", driverName, busNumber, routeAssigned);
        }
    }

    /**
     * Overloaded convenience factory for buses with no extra detail.
     */
    public static BusManager createBus(String busType, String vehicleID, String vehicleName,
                                       int capacity, String fuelType, String location,
                                       String driverName, String busNumber, String routeAssigned) {
        return createBus(busType, vehicleID, vehicleName, capacity, fuelType,
                location, driverName, busNumber, routeAssigned, "");
    }
}
