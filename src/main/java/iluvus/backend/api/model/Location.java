package iluvus.backend.api.model;

public class Location {
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String address;

    public Location() {
    }

    public Location(String city, String state, String country, String zipCode, String address) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.address = address;
    }
}
