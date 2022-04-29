package models;

public class Location {
    private Long longitude;
    private Long latitude;
    private String street;
    private String city;
    private String country;
    private String number;

    public Location(Long longitude, Long latitude, String street, String city, String country, String number) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.city = city;
        this.country = country;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }
}
