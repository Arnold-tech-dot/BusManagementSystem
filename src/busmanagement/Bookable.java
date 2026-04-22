package busmanagement;

public interface Bookable {
    boolean bookSeat(Passenger passenger, int seatNumber);
    boolean cancelBooking(String bookingID);
    double calculateFare(String routeID);
}
