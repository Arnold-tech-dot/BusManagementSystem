package busmanagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusSystem {

    // ── System data stores ───────────────────────────────────────────────────
    private static List<BusManager> buses = new ArrayList<>();
    private static List<Passenger> passengers = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();
    private static List<Schedule> schedules = new ArrayList<>();
    private static List<Maintenance> maintenanceRecords = new ArrayList<>();

    private static InputValidator validator = new InputValidator();
    private static Scanner scanner = new Scanner(System.in);

    // ID counters
    private static int busIDCounter = 1;
    private static int passengerIDCounter = 1;
    private static int bookingIDCounter = 1;
    private static int ticketIDCounter = 1;
    private static int paymentIDCounter = 1;
    private static int scheduleIDCounter = 1;
    private static int maintenanceIDCounter = 1;

    // ── Entry point ──────────────────────────────────────────────────────────

    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = promptInput("Enter your choice");
            switch (choice.trim()) {
                case "1" -> registerBus();
                case "2" -> registerPassenger();
                case "3" -> addRoute();
                case "4" -> makeBooking();
                case "5" -> processPaymentMenu();
                case "6" -> trackBus();
                case "7" -> viewReports();
                case "8" -> scheduleMaintenanceMenu();
                case "9" -> displayAllBuses();
                case "10" -> displayAllPassengers();
                case "0" -> {
                    System.out.println("\n  Thank you for using the Bus Management System. Goodbye!\n");
                    running = false;
                }
                default -> System.out.println("[ERROR] Invalid option. Please choose from the menu.");
            }
        }
        scanner.close();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  REGISTER A BUS
    // ════════════════════════════════════════════════════════════════════════

    private static void registerBus() {
        printSectionHeader("REGISTER NEW BUS");

        String busType;
        do {
            busType = promptInput("Bus type (CITY / EXPRESS / LUXURY / SCHOOL / TOURIST / ELECTRIC)");
        } while (!validator.isValidBusType(busType));

        String vehicleName = promptNonEmpty("Bus name / model");

        String capacityStr;
        do {
            capacityStr = promptInput("Seating capacity");
        } while (!validator.isPositiveInt(capacityStr, "Capacity"));
        int capacity = Integer.parseInt(capacityStr.trim());

        String fuelType;
        do {
            fuelType = promptNonEmpty("Fuel type (e.g., Diesel, Petrol, Electric)");
        } while (fuelType.isEmpty());

        String location = promptNonEmpty("Current location / depot");
        String driverName = promptNonEmpty("Assigned driver name");

        String busNumber;
        do {
            busNumber = promptNonEmpty("Bus number plate");
        } while (!validator.isUniqueID(busNumber, "Bus Number"));
        validator.registerID(busNumber);

        String routeAssigned = promptNonEmpty("Route assigned (or 'None')");

        // Extra type-specific detail
        String extraDetail = "";
        switch (busType.toUpperCase().trim()) {
            case "CITY" -> extraDetail = promptInput("Has air conditioning? (true/false)");
            case "EXPRESS" -> {
                String stops;
                do { stops = promptInput("Number of stops"); }
                while (!validator.isNonNegativeInt(stops, "Number of stops"));
                extraDetail = stops;
            }
            case "LUXURY" -> extraDetail = promptInput("Has Wi-Fi? (true/false)");
            case "SCHOOL" -> extraDetail = promptNonEmpty("School name");
            case "TOURIST" -> extraDetail = promptNonEmpty("Tour package name");
            case "ELECTRIC" -> {
                String bat;
                do { bat = promptInput("Battery capacity (kWh)"); }
                while (!validator.isPositiveDouble(bat, "Battery capacity"));
                extraDetail = bat;
            }
        }

        String vehicleID = "VEH" + String.format("%03d", busIDCounter++);
        BusManager bus = BusFactory.createBus(busType, vehicleID, vehicleName, capacity,
                fuelType, location, driverName, busNumber, routeAssigned, extraDetail);
        buses.add(bus);

        System.out.println("\n✔ Bus registered successfully!");
        System.out.println(bus);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  REGISTER A PASSENGER
    // ════════════════════════════════════════════════════════════════════════

    private static void registerPassenger() {
        printSectionHeader("REGISTER NEW PASSENGER");

        String name = promptNonEmpty("Passenger full name");

        String phone;
        do {
            phone = promptInput("Phone number (e.g., +250788123456)");
        } while (!validator.isValidPhone(phone));

        String email;
        do {
            email = promptInput("Email address");
        } while (!validator.isValidEmail(email));

        String passengerID = "P" + String.format("%03d", passengerIDCounter++);
        Passenger passenger = new Passenger(passengerID, name, phone, email);
        passengers.add(passenger);

        System.out.println("\n✔ Passenger registered successfully!");
        System.out.println(passenger);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ADD A ROUTE
    // ════════════════════════════════════════════════════════════════════════

    private static void addRoute() {
        printSectionHeader("ADD NEW ROUTE");

        String start = promptNonEmpty("Start location");
        String destination = promptNonEmpty("Destination");

        String distStr;
        do {
            distStr = promptInput("Distance (km)");
        } while (!validator.isPositiveDouble(distStr, "Distance"));
        double distance = Double.parseDouble(distStr.trim());

        String estimatedTime = promptNonEmpty("Estimated travel time (e.g., 2h 30m)");

        String routeID = "R" + String.format("%03d", routes.size() + 1);
        Route route = new Route(routeID, start, destination, distance, estimatedTime);
        routes.add(route);

        System.out.println("\n✔ Route added successfully!");
        System.out.println(route);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MAKE A BOOKING
    // ════════════════════════════════════════════════════════════════════════

    private static void makeBooking() {
        printSectionHeader("MAKE A BOOKING");

        if (buses.isEmpty()) {
            System.out.println("[INFO] No buses registered yet. Please register a bus first.");
            return;
        }
        if (passengers.isEmpty()) {
            System.out.println("[INFO] No passengers registered yet. Please register a passenger first.");
            return;
        }

        // Show available buses
        System.out.println("  Available Buses:");
        for (int i = 0; i < buses.size(); i++) {
            BusManager b = buses.get(i);
            System.out.println("  [" + (i + 1) + "] " + b.getBusNumber() +
                    " (" + b.getVehicleName() + ") | Route: " + b.getRouteAssigned() +
                    " | Seats left: " + (b.getCapacity() - b.getBookedSeats().size()) +
                    "/" + b.getCapacity());
        }

        String busIndexStr;
        int busIndex;
        do {
            busIndexStr = promptInput("Select bus number (1-" + buses.size() + ")");
            if (!validator.isPositiveInt(busIndexStr, "Bus selection")) { busIndex = -1; continue; }
            busIndex = Integer.parseInt(busIndexStr.trim()) - 1;
            if (busIndex < 0 || busIndex >= buses.size()) {
                System.out.println("[Validation ERROR] Choice out of range.");
                busIndex = -1;
            }
        } while (busIndex < 0);

        BusManager selectedBus = buses.get(busIndex);

        // Check availability
        if (selectedBus.getBookedSeats().size() >= selectedBus.getCapacity()) {
            System.out.println("[ERROR] This bus is fully booked. Overbooking is not allowed.");
            return;
        }

        // Show available passengers
        System.out.println("\n  Registered Passengers:");
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + passengers.get(i).getPassengerID() +
                    " - " + passengers.get(i).getName());
        }

        String passIndexStr;
        int passIndex;
        do {
            passIndexStr = promptInput("Select passenger (1-" + passengers.size() + ")");
            if (!validator.isPositiveInt(passIndexStr, "Passenger selection")) { passIndex = -1; continue; }
            passIndex = Integer.parseInt(passIndexStr.trim()) - 1;
            if (passIndex < 0 || passIndex >= passengers.size()) {
                System.out.println("[Validation ERROR] Choice out of range.");
                passIndex = -1;
            }
        } while (passIndex < 0);

        Passenger selectedPassenger = passengers.get(passIndex);

        // Seat selection
        String seatStr;
        int seatNumber;
        do {
            seatStr = promptInput("Seat number (1-" + selectedBus.getCapacity() + ")");
            if (!validator.isValidSeatNumber(seatStr, selectedBus.getCapacity(), "Seat number")) {
                seatNumber = -1; continue;
            }
            seatNumber = Integer.parseInt(seatStr.trim());
            if (selectedBus.getBookedSeats().contains(seatNumber)) {
                System.out.println("[Validation ERROR] Seat " + seatNumber + " is already taken. Choose another.");
                seatNumber = -1;
            }
        } while (seatNumber < 0);

        // Calculate fare
        String routeID = selectedBus.getRouteAssigned().isEmpty() ? "GENERAL" : selectedBus.getRouteAssigned();
        double baseFare = selectedBus.calculateFare(routeID);

        // Create ticket
        String ticketID = "T" + String.format("%03d", ticketIDCounter++);
        Ticket ticket = new Ticket(ticketID, selectedPassenger, selectedBus, seatNumber, 0);
        double finalFare = ticket.calculateTicketPrice(baseFare, seatNumber);

        // Book the seat
        boolean booked = selectedBus.bookSeat(selectedPassenger, seatNumber);
        if (!booked) return;

        // Create and confirm booking
        String bookingID = "B" + String.format("%03d", bookingIDCounter++);
        Booking booking = new Booking(bookingID, selectedPassenger, ticket, LocalDate.now(), "PENDING");
        booking.confirmBooking();
        bookings.add(booking);

        // Summary
        System.out.println("\n══════════ BOOKING SUMMARY ══════════");
        System.out.println("  Booking ID   : " + bookingID);
        System.out.println("  Passenger    : " + selectedPassenger.getName());
        System.out.println("  Bus          : " + selectedBus.getBusNumber());
        System.out.println("  Route        : " + selectedBus.getRouteAssigned());
        System.out.println("  Seat         : " + seatNumber);
        System.out.println("  Ticket ID    : " + ticketID);
        System.out.println("  Fare         : $" + String.format("%.2f", finalFare));
        System.out.println("  Date         : " + LocalDate.now());
        System.out.println("  Status       : " + booking.getStatus());
        System.out.println("═════════════════════════════════════\n");

        // Prompt payment
        System.out.println("  Would you like to pay now? (yes/no)");
        String pay = scanner.nextLine().trim();
        if (pay.equalsIgnoreCase("yes") || pay.equalsIgnoreCase("y")) {
            processPaymentForBooking(finalFare, bookingID);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PROCESS PAYMENT
    // ════════════════════════════════════════════════════════════════════════

    private static void processPaymentForBooking(double amount, String bookingID) {
        System.out.println("\n── Payment for Booking " + bookingID + " ──");
        String method;
        do {
            method = promptInput("Payment method (CASH / CARD / MOBILE)");
        } while (!validator.isValidPaymentMethod(method));

        String paymentID = "PAY" + String.format("%03d", paymentIDCounter++);
        Payment payment = new Payment(paymentID, amount, method.toUpperCase(), "PENDING");
        boolean success = payment.processPayment();
        payments.add(payment);

        System.out.println("\n── Payment Status ──");
        System.out.println(payment);
        if (success) {
            System.out.println("✔ Payment successful! Enjoy your journey.");
        } else {
            System.out.println("✘ Payment failed. Please try again at the counter.");
        }
    }

    private static void processPaymentMenu() {
        printSectionHeader("PROCESS PAYMENT");
        String amountStr;
        do {
            amountStr = promptInput("Amount to pay ($)");
        } while (!validator.isPositiveDouble(amountStr, "Amount"));

        double amount = Double.parseDouble(amountStr.trim());
        processPaymentForBooking(amount, "MANUAL");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  TRACK BUS
    // ════════════════════════════════════════════════════════════════════════

    private static void trackBus() {
        printSectionHeader("TRACK BUS");
        if (buses.isEmpty()) {
            System.out.println("[INFO] No buses registered yet.");
            return;
        }
        System.out.println("  Registered Buses:");
        for (int i = 0; i < buses.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + buses.get(i).getBusNumber());
        }
        String indexStr;
        int index;
        do {
            indexStr = promptInput("Select bus (1-" + buses.size() + ")");
            if (!validator.isPositiveInt(indexStr, "Selection")) { index = -1; continue; }
            index = Integer.parseInt(indexStr.trim()) - 1;
            if (index < 0 || index >= buses.size()) {
                System.out.println("[Validation ERROR] Out of range."); index = -1;
            }
        } while (index < 0);

        BusManager bus = buses.get(index);
        System.out.println("\n── Tracking Bus: " + bus.getBusNumber() + " ──");
        bus.trackLocation();
        System.out.println("  Status    : " + bus.getStatus());
        System.out.println("  Route     : " + bus.getRouteAssigned());
        System.out.println("  Driver    : " + bus.getDriverName());
        System.out.println("  Seats     : " + bus.getBookedSeats().size() + "/" + bus.getCapacity());

        String update = promptInput("Update location? (yes/no)");
        if (update.equalsIgnoreCase("yes") || update.equalsIgnoreCase("y")) {
            String newLoc = promptNonEmpty("New location");
            bus.updateLocation(newLoc);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  REPORTS
    // ════════════════════════════════════════════════════════════════════════

    private static void viewReports() {
        printSectionHeader("REPORTS");
        ReportGenerator rg = new ReportGenerator(bookings, payments, buses, passengers);
        System.out.println("  [1] Daily Bookings Report");
        System.out.println("  [2] Revenue Report");
        System.out.println("  [3] Bus Usage Report");
        System.out.println("  [4] Passenger Statistics Report");
        System.out.println("  [5] Full System Report");
        String choice = promptInput("Select report");
        switch (choice.trim()) {
            case "1" -> rg.generateDailyBookingsReport();
            case "2" -> rg.generateRevenueReport();
            case "3" -> rg.generateBusUsageReport();
            case "4" -> rg.generatePassengerStatisticsReport();
            case "5" -> rg.generateFullReport();
            default -> System.out.println("[ERROR] Invalid choice.");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MAINTENANCE
    // ════════════════════════════════════════════════════════════════════════

    private static void scheduleMaintenanceMenu() {
        printSectionHeader("SCHEDULE MAINTENANCE");
        if (buses.isEmpty()) {
            System.out.println("[INFO] No buses registered.");
            return;
        }
        System.out.println("  Buses:");
        for (int i = 0; i < buses.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + buses.get(i).getBusNumber());
        }
        String indexStr;
        int index;
        do {
            indexStr = promptInput("Select bus (1-" + buses.size() + ")");
            if (!validator.isPositiveInt(indexStr, "Selection")) { index = -1; continue; }
            index = Integer.parseInt(indexStr.trim()) - 1;
            if (index < 0 || index >= buses.size()) {
                System.out.println("[Validation ERROR] Out of range."); index = -1;
            }
        } while (index < 0);

        BusManager bus = buses.get(index);
        String desc = promptNonEmpty("Maintenance description");
        String maintenanceID = "MNT" + String.format("%03d", maintenanceIDCounter++);
        Maintenance m = new Maintenance(maintenanceID, bus, LocalDate.now().plusDays(1), desc);
        m.scheduleMaintenance(LocalDate.now().plusDays(1), desc);
        maintenanceRecords.add(m);
        bus.performMaintenanceCheck();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  DISPLAY ALL
    // ════════════════════════════════════════════════════════════════════════

    private static void displayAllBuses() {
        printSectionHeader("ALL REGISTERED BUSES");
        if (buses.isEmpty()) {
            System.out.println("  No buses registered.");
            return;
        }
        for (BusManager b : buses) {
            System.out.println("\n" + b.generateVehicleReport());
        }
    }

    private static void displayAllPassengers() {
        printSectionHeader("ALL REGISTERED PASSENGERS");
        if (passengers.isEmpty()) {
            System.out.println("  No passengers registered.");
            return;
        }
        for (Passenger p : passengers) {
            System.out.println("  " + p);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  UTILITY METHODS
    // ════════════════════════════════════════════════════════════════════════

    private static String promptInput(String message) {
        System.out.print("  > " + message + ": ");
        return scanner.nextLine();
    }

    private static String promptNonEmpty(String fieldName) {
        String value;
        do {
            value = promptInput(fieldName);
            if (!validator.isNotEmpty(value, fieldName)) value = "";
        } while (value.isEmpty());
        return value.trim();
    }

    private static void printBanner() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         NATIONAL BUS MANAGEMENT SYSTEM           ║");
        System.out.println("║         Powered by OOP Java  |  Group C          ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
    }

    private static void printMainMenu() {
        System.out.println("\n┌──────────────── MAIN MENU ───────────────────┐");
        System.out.println("│  [1]  Register a Bus                          │");
        System.out.println("│  [2]  Register a Passenger                    │");
        System.out.println("│  [3]  Add a Route                             │");
        System.out.println("│  [4]  Make a Booking                          │");
        System.out.println("│  [5]  Process a Payment                       │");
        System.out.println("│  [6]  Track a Bus                             │");
        System.out.println("│  [7]  View Reports                            │");
        System.out.println("│  [8]  Schedule Maintenance                    │");
        System.out.println("│  [9]  Display All Buses                       │");
        System.out.println("│  [10] Display All Passengers                  │");
        System.out.println("│  [0]  Exit                                    │");
        System.out.println("└───────────────────────────────────────────────┘");
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n══════════════ " + title + " ══════════════");
    }
}
