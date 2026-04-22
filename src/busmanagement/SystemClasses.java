package busmanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ─────────────────────────────────────────────────────────────
//  Ticket
// ─────────────────────────────────────────────────────────────
class Ticket {
    private String ticketID;
    private Passenger passenger;
    private BusManager bus;
    private int seatNumber;
    private double price;

    public Ticket() {
        this.ticketID = "T000";
        this.passenger = null;
        this.bus = null;
        this.seatNumber = 0;
        this.price = 0.0;
    }

    public Ticket(String ticketID, Passenger passenger, BusManager bus,
                  int seatNumber, double price) {
        this.ticketID = ticketID;
        this.passenger = passenger;
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getTicketID() { return ticketID; }
    public Passenger getPassenger() { return passenger; }
    public BusManager getBus() { return bus; }
    public int getSeatNumber() { return seatNumber; }
    public double getPrice() { return price; }

    public void setTicketID(String ticketID) { this.ticketID = ticketID; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }
    public void setBus(BusManager bus) { this.bus = bus; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public void setPrice(double price) { this.price = price; }

    public double calculateTicketPrice(double basePrice, int seatNumber) {
        // Premium seats (first 5 rows) cost 20% more
        double finalPrice = (seatNumber <= 5) ? basePrice * 1.20 : basePrice;
        this.price = finalPrice;
        return finalPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID='" + ticketID + '\'' +
                ", passenger=" + (passenger != null ? passenger.getName() : "None") +
                ", bus=" + (bus != null ? bus.getBusNumber() : "None") +
                ", seatNumber=" + seatNumber +
                ", price=$" + String.format("%.2f", price) +
                '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  Booking
// ─────────────────────────────────────────────────────────────
class Booking {
    private String bookingID;
    private Passenger passenger;
    private Ticket ticket;
    private LocalDate bookingDate;
    private String status; // CONFIRMED, CANCELLED, PENDING

    public Booking() {
        this.bookingID = "B000";
        this.passenger = null;
        this.ticket = null;
        this.bookingDate = LocalDate.now();
        this.status = "PENDING";
    }

    public Booking(String bookingID, Passenger passenger, Ticket ticket,
                   LocalDate bookingDate, String status) {
        this.bookingID = bookingID;
        this.passenger = passenger;
        this.ticket = ticket;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public String getBookingID() { return bookingID; }
    public Passenger getPassenger() { return passenger; }
    public Ticket getTicket() { return ticket; }
    public LocalDate getBookingDate() { return bookingDate; }
    public String getStatus() { return status; }

    public void setBookingID(String bookingID) { this.bookingID = bookingID; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public void setStatus(String status) { this.status = status; }

    public void confirmBooking() {
        this.status = "CONFIRMED";
        System.out.println("[Booking] Booking " + bookingID + " CONFIRMED for " +
                (passenger != null ? passenger.getName() : "passenger") + ".");
    }

    public void cancelBooking() {
        this.status = "CANCELLED";
        System.out.println("[Booking] Booking " + bookingID + " has been CANCELLED.");
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID='" + bookingID + '\'' +
                ", passenger=" + (passenger != null ? passenger.getName() : "None") +
                ", ticketID=" + (ticket != null ? ticket.getTicketID() : "None") +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  Payment
// ─────────────────────────────────────────────────────────────
class Payment {
    private String paymentID;
    private double amount;
    private String paymentMethod; // CASH, CARD, MOBILE
    private String paymentStatus; // PENDING, COMPLETED, FAILED, REFUNDED

    public Payment() {
        this.paymentID = "PAY000";
        this.amount = 0.0;
        this.paymentMethod = "CASH";
        this.paymentStatus = "PENDING";
    }

    public Payment(String paymentID, double amount, String paymentMethod, String paymentStatus) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentID() { return paymentID; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }

    public void setPaymentID(String paymentID) { this.paymentID = paymentID; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public boolean processPayment() {
        if (amount <= 0) {
            System.out.println("[Payment] ERROR: Invalid amount $" + amount);
            this.paymentStatus = "FAILED";
            return false;
        }
        // Simulate payment processing
        System.out.println("[Payment] Processing $" + String.format("%.2f", amount) +
                " via " + paymentMethod + "...");
        this.paymentStatus = "COMPLETED";
        System.out.println("[Payment] Payment " + paymentID + " COMPLETED successfully.");
        return true;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", amount=$" + String.format("%.2f", amount) +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  Schedule
// ─────────────────────────────────────────────────────────────
class Schedule {
    private String scheduleID;
    private BusManager bus;
    private String departureTime;
    private String arrivalTime;
    private Route route;

    public Schedule() {
        this.scheduleID = "SCH000";
        this.bus = null;
        this.departureTime = "00:00";
        this.arrivalTime = "00:00";
        this.route = null;
    }

    public Schedule(String scheduleID, BusManager bus, String departureTime,
                    String arrivalTime, Route route) {
        this.scheduleID = scheduleID;
        this.bus = bus;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
    }

    public String getScheduleID() { return scheduleID; }
    public BusManager getBus() { return bus; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public Route getRoute() { return route; }

    public void setScheduleID(String scheduleID) { this.scheduleID = scheduleID; }
    public void setBus(BusManager bus) { this.bus = bus; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setRoute(Route route) { this.route = route; }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleID='" + scheduleID + '\'' +
                ", bus=" + (bus != null ? bus.getBusNumber() : "None") +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", route=" + (route != null ? route.getRouteID() : "None") +
                '}';
    }
}

// ─────────────────────────────────────────────────────────────
//  Maintenance
// ─────────────────────────────────────────────────────────────
class Maintenance {
    private String maintenanceID;
    private Vehicle vehicle;
    private LocalDate maintenanceDate;
    private String description;

    public Maintenance() {
        this.maintenanceID = "MNT000";
        this.vehicle = null;
        this.maintenanceDate = LocalDate.now();
        this.description = "Routine check";
    }

    public Maintenance(String maintenanceID, Vehicle vehicle,
                       LocalDate maintenanceDate, String description) {
        this.maintenanceID = maintenanceID;
        this.vehicle = vehicle;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
    }

    public String getMaintenanceID() { return maintenanceID; }
    public Vehicle getVehicle() { return vehicle; }
    public LocalDate getMaintenanceDate() { return maintenanceDate; }
    public String getDescription() { return description; }

    public void setMaintenanceID(String maintenanceID) { this.maintenanceID = maintenanceID; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public void setMaintenanceDate(LocalDate maintenanceDate) { this.maintenanceDate = maintenanceDate; }
    public void setDescription(String description) { this.description = description; }

    public void scheduleMaintenance(LocalDate date, String desc) {
        this.maintenanceDate = date;
        this.description = desc;
        System.out.println("[Maintenance] Scheduled: " + desc + " on " + date +
                " for vehicle " + (vehicle != null ? vehicle.getVehicleID() : "N/A"));
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenanceID='" + maintenanceID + '\'' +
                ", vehicle=" + (vehicle != null ? vehicle.getVehicleID() : "None") +
                ", maintenanceDate=" + maintenanceDate +
                ", description='" + description + '\'' +
                '}';
    }
}
