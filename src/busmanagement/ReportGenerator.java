package busmanagement;

import java.time.LocalDate;
import java.util.List;

public class ReportGenerator {

    private List<Booking> bookings;
    private List<Payment> payments;
    private List<BusManager> buses;
    private List<Passenger> passengers;

    public ReportGenerator(List<Booking> bookings, List<Payment> payments,
                           List<BusManager> buses, List<Passenger> passengers) {
        this.bookings = bookings;
        this.payments = payments;
        this.buses = buses;
        this.passengers = passengers;
    }

    // ── Daily Bookings Report ────────────────────────────────────────────────

    public void generateDailyBookingsReport() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║      DAILY BOOKINGS REPORT           ║");
        System.out.println("║  Date: " + LocalDate.now() + "               ║");
        System.out.println("╚══════════════════════════════════════╝");
        if (bookings.isEmpty()) {
            System.out.println("  No bookings recorded today.");
            return;
        }
        int confirmed = 0, cancelled = 0, pending = 0;
        for (Booking b : bookings) {
            System.out.println("  [" + b.getBookingID() + "] " +
                    (b.getPassenger() != null ? b.getPassenger().getName() : "N/A") +
                    " | Ticket: " + (b.getTicket() != null ? b.getTicket().getTicketID() : "N/A") +
                    " | Status: " + b.getStatus());
            switch (b.getStatus()) {
                case "CONFIRMED" -> confirmed++;
                case "CANCELLED" -> cancelled++;
                default -> pending++;
            }
        }
        System.out.println("─────────────────────────────────────");
        System.out.println("  Total    : " + bookings.size());
        System.out.println("  Confirmed: " + confirmed);
        System.out.println("  Cancelled: " + cancelled);
        System.out.println("  Pending  : " + pending);
        System.out.println("═══════════════════════════════════════\n");
    }

    // ── Revenue Report ───────────────────────────────────────────────────────

    public void generateRevenueReport() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         REVENUE REPORT               ║");
        System.out.println("╚══════════════════════════════════════╝");
        double totalRevenue = 0.0;
        double pendingRevenue = 0.0;
        for (Payment p : payments) {
            System.out.println("  [" + p.getPaymentID() + "] $" +
                    String.format("%.2f", p.getAmount()) +
                    " via " + p.getPaymentMethod() +
                    " | Status: " + p.getPaymentStatus());
            if ("COMPLETED".equals(p.getPaymentStatus())) {
                totalRevenue += p.getAmount();
            } else if ("PENDING".equals(p.getPaymentStatus())) {
                pendingRevenue += p.getAmount();
            }
        }
        System.out.println("─────────────────────────────────────");
        System.out.println("  Total Revenue  : $" + String.format("%.2f", totalRevenue));
        System.out.println("  Pending Revenue: $" + String.format("%.2f", pendingRevenue));
        System.out.println("═══════════════════════════════════════\n");
    }

    // ── Bus Usage Report ─────────────────────────────────────────────────────

    public void generateBusUsageReport() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         BUS USAGE REPORT             ║");
        System.out.println("╚══════════════════════════════════════╝");
        for (BusManager bus : buses) {
            int booked = bus.getBookedSeats().size();
            int cap = bus.getCapacity();
            double util = (cap > 0) ? ((double) booked / cap) * 100 : 0;
            System.out.printf("  Bus: %-10s | Route: %-15s | Seats: %d/%d (%.1f%%) | Status: %s%n",
                    bus.getBusNumber(), bus.getRouteAssigned(), booked, cap, util, bus.getStatus());
        }
        System.out.println("═══════════════════════════════════════\n");
    }

    // ── Passenger Statistics Report ──────────────────────────────────────────

    public void generatePassengerStatisticsReport() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    PASSENGER STATISTICS REPORT       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Total Registered Passengers: " + passengers.size());
        for (Passenger p : passengers) {
            long passengerBookings = bookings.stream()
                    .filter(b -> b.getPassenger() != null &&
                            b.getPassenger().getPassengerID().equals(p.getPassengerID()))
                    .count();
            System.out.println("  [" + p.getPassengerID() + "] " + p.getName() +
                    " | Bookings: " + passengerBookings +
                    " | Phone: " + p.getPhoneNumber());
        }
        System.out.println("═══════════════════════════════════════\n");
    }

    // ── Full System Report ───────────────────────────────────────────────────

    public void generateFullReport() {
        generateDailyBookingsReport();
        generateRevenueReport();
        generateBusUsageReport();
        generatePassengerStatisticsReport();
    }
}
