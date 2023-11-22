package iluvus.backend.api.dto;

public class LocationDto {
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    private String address;

    public LocationDto() {
    }

    public LocationDto(String address, String city, String state, String country, Integer zipCode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.address = address;
    }

    public LocationDto(String location) {
        String[] locationArray = location.split(",");
        this.address = locationArray[0];
        this.city = locationArray[1];
        this.state = locationArray[2];
        this.country = locationArray[3];
        this.zipCode = Integer.parseInt(locationArray[4]);
    }

    public void setLocation(String location) {
        String[] locationArray = location.split(",");
        this.address = locationArray[0];
        this.city = locationArray[1];
        this.state = locationArray[2];
        this.country = locationArray[3];
        this.zipCode = Integer.parseInt(locationArray[4]);
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

