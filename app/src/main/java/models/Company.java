package models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Company {
    private String username;
    private String password;
    private CompanyType companyType;
    private String lastName;
    private String firstName;
    private String phone;
    private String e_mail;
    private List<Location> locationList;

    public Company(String username, String password, String name, CompanyType companyType, String lastName, String firstName, String phone, String e_mail, List<Location> locationList) {
        this.username = username;
        this.password = password;
        this.companyType = companyType;
        this.lastName = lastName;
        this.firstName = firstName;
        this.locationList = locationList;
        this.phone = phone;
        this.e_mail = e_mail;
    }

    public Company(String e_mail, String password, String lastName, String firstName) {
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.e_mail = e_mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
