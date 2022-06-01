package com.devpro.models;


import com.devpro.models.LatLng;

import java.util.ArrayList;

public class Location {
    private LatLng location;
    private String line1;
    private String line2;
    private String city;
    private String postal_code;
    private String country;
    private String description;
    private ArrayList<String> services;
    private ArrayList<Request> requests;
    private String phone;
    private String owner;
    private String email;

    public Location () {
        location = new LatLng(0, 0);
    }

    public Location(LatLng location, String line1, String line2, String city, String postal_code, String country, String description, ArrayList<String> services, ArrayList<Request> requests, String phone, String owner, String email) {
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.postal_code = postal_code;
        this.country = country;
        this.description = description;
        this.services = services;
        this.requests = requests;
        this.phone = phone;
        this.owner = owner;
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }
}
