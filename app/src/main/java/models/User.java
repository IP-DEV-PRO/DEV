package models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@IgnoreExtraProperties
public class User {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String phone;
    private String e_mail;
    private String start;
    private String expiration;
    private boolean activesub;
    private boolean has_company;

    public User() {

    }

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName;
    }

    public User(String username, String password, String lastName, String firstName, String phone, String e_mail,
                String start, String expiration, boolean activesub, boolean has_company) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.e_mail = e_mail;
        this.start = start;
        this.expiration = expiration;
        this.activesub = activesub;
        this.has_company = has_company;
    }

    public User(String username, String password, String lastName, String firstName, String phone, String e_mail,
                 boolean has_company) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.e_mail = e_mail;
        this.has_company = has_company;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }


    public boolean isHas_company() {
        return has_company;
    }

    public void setHas_company(boolean has_company) {
        this.has_company = has_company;
    }

    public boolean isActivesub() {
        return activesub;
    }

    public void setActivesub(boolean activesub) {
        this.activesub = activesub;
    }
}
