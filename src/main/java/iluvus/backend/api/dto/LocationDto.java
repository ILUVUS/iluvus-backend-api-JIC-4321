package iluvus.backend.api.dto;

public class LocationDto {
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String address;

    public LocationDto() {
    }

    public LocationDto(String address, String city, String state, String country, String zipCode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.address = address;
    }

    public LocationDto(String location) {
        String[] locationArray = location.split(",");
        this.address = locationArray[0].trim();
        this.city = locationArray[1].trim();
        this.state = locationArray[2].trim();
        this.country = locationArray[3].trim();
        this.zipCode = locationArray[4].trim();
    }

    public void setLocation(String location) {
        String[] locationArray = location.split(",");
        this.address = locationArray[0].trim();
        this.city = locationArray[1].trim();
        this.state = locationArray[2].trim();
        this.country = locationArray[3].trim();
        this.zipCode = locationArray[4].trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

