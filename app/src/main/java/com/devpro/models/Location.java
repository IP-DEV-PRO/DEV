package com.devpro.models;


public class Location {
    private LatLng location;
    private String street;
    private String city;
    private String country;
    private String number;

    public Location () {
        location = new LatLng(0, 0);
    }

    public Location(LatLng location, String street, String city, String country, String number) {
        this.location = location;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
