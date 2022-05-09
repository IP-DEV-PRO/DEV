package com.devpro.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Company {
    private String username;
    private CompanyType companyType;
    private String lastName;
    private String firstName;
    private String phone;
    private String cui;
    private List<Location> locationList;

    public Company() {}

    public Company(String username, CompanyType companyType, String cui, String lastName, String firstName, String phone, List<Location> locationList) {
        if (username == null) {
            this.username = "set-username";
        } else {
            this.username = username;
        }
        this.username = username;
        if (companyType == null) {
            this.companyType = CompanyType.NONE;
        } else {
            this.companyType = companyType;
        }
        this.lastName = lastName;
        this.firstName = firstName;
        if (locationList == null) {
            this.locationList = new ArrayList<>();
            this.locationList.add(new Location(new LatLng(0, 0), "no-street", "no-city", "no-country", "no-number"));
        } else {
            this.locationList = locationList;
        }

        if (phone == null) {
            this.phone = "no-phone";
        } else {
            this.phone = phone;
        }

        this.cui = cui;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
