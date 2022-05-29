package com.devpro.models;


import com.devpro.models.LatLng;

public class Location {
    private LatLng location;
    private String line1;
    private String line2;
    private String city;
    private String postal_code;
    private String country;
    private String desctiption;
    private String services;
    private String phone;
    private String owner;

    public Location () {
        location = new LatLng(0, 0);
    }

    public Location(LatLng location, String line1, String line2, String city, String postal_code, String country, String desctiption, String services, String phone, String owner) {
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.postal_code = postal_code;
        this.country = country;
        this.desctiption = desctiption;
        this.services = services;
        this.phone = phone;
        this.owner = owner;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return "Location{" +
                "location=" + location +
                ", line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", city='" + city + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", country='" + country + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
