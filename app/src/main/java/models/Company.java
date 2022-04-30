package models;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
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

    public Company(String username, String password, CompanyType companyType, String lastName, String firstName, String phone, String e_mail, List<Location> locationList) {
        if (username == null ){
            this.username = "set-username";
        }
        else{
            this.username = username;
        }
        this.username = username;
        this.password = password;
        if (companyType == null) {
            this.companyType = CompanyType.NONE;
        } else {
            this.companyType = companyType;
        }
        this.lastName = lastName;
        this.firstName = firstName;
        if ( locationList == null ) {
            this.locationList = new ArrayList<>();
            this.locationList.add(new Location(new LatLng(0,0), "no-street", "no-city", "no-country", "no-number"));
        }else{
            this.locationList = locationList;
        }

        if (phone == null) {
            this.phone = "no-phone";
        } else {
            this.phone = phone;
        }
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
