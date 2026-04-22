package busmanagement;

public class Route {
    private String routeID;
    private String startLocation;
    private String destination;
    private double distance;
    private String estimatedTime;

    public Route() {
        this.routeID = "R000";
        this.startLocation = "Unknown";
        this.destination = "Unknown";
        this.distance = 0.0;
        this.estimatedTime = "00:00";
    }

    public Route(String routeID, String startLocation, String destination,
                 double distance, String estimatedTime) {
        this.routeID = routeID;
        this.startLocation = startLocation;
        this.destination = destination;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
    }

    public String getRouteID() { return routeID; }
    public String getStartLocation() { return startLocation; }
    public String getDestination() { return destination; }
    public double getDistance() { return distance; }
    public String getEstimatedTime() { return estimatedTime; }

    public void setRouteID(String routeID) { this.routeID = routeID; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }

    @Override
    public String toString() {
        return "Route{" +
                "routeID='" + routeID + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance + " km" +
                ", estimatedTime='" + estimatedTime + '\'' +
                '}';
    }
}
