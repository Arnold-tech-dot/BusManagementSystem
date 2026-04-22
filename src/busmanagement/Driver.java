package busmanagement;

public class Driver {
    private String driverID;
    private String name;
    private String licenseNumber;
    private int experienceYears;

    public Driver() {
        this.driverID = "D000";
        this.name = "Unknown";
        this.licenseNumber = "LIC000";
        this.experienceYears = 0;
    }

    public Driver(String driverID, String name, String licenseNumber, int experienceYears) {
        this.driverID = driverID;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
    }

    public String getDriverID() { return driverID; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getExperienceYears() { return experienceYears; }

    public void setDriverID(String driverID) { this.driverID = driverID; }
    public void setName(String name) { this.name = name; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    @Override
    public String toString() {
        return "Driver{" +
                "driverID='" + driverID + '\'' +
                ", name='" + name + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }
}
