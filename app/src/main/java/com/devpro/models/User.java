package com.devpro.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class User {
    private boolean blocked;
    private String username;
    private String com_reg_no;
    private String password;
    private String lastName;
    private String firstName;
    private String phone;
    private String e_mail;
    private String reg_date;
    private String sub_start;
    private String sub_exp;
    private boolean sub_active;
    private int accepted;
    private int role;
    private String companyName;
    private String[] services;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public User() {

    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "blocked=" + blocked +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", phone='" + phone + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", sub_start='" + sub_start + '\'' +
                ", sub_exp='" + sub_exp + '\'' +
                ", sub_active=" + sub_active +
                ", companyAdmin=" + role +
                '}';
    }

    public User(String username, String password, String lastName, String firstName, String phone, String e_mail,
                String start, String expiration, boolean activesub, int has_company) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.e_mail = e_mail;
        this.sub_start = start;
        this.sub_exp = expiration;
        this.sub_active = activesub;
        this.role = has_company;
    }
    public User(boolean blocked,String companyName, String e_mail, String password,
                String sub_start, String sub_exp, boolean sub_active, int role, String comp_reg_no, String[] services) {
        this.blocked = blocked;
        this.companyName = companyName;
        this.e_mail = e_mail;
        this.password = password;
        this.sub_start = sub_start;
        this.sub_exp = sub_exp;
        this.sub_active = sub_active;
        this.role = role;
        this.com_reg_no = comp_reg_no;
        this.services = services;
    }


    public User(String username, String password, String lastName, String firstName, String phone, String e_mail,
                 int has_company) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.e_mail = e_mail;
        this.role = has_company;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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

    public String getSub_start() {
        return sub_start;
    }

    public void setSub_start(String sub_start) {
        this.sub_start = sub_start;
    }

    public String getSub_exp() {
        return sub_exp;
    }

    public void setSub_exp(String sub_exp) {
        this.sub_exp = sub_exp;
    }

    public boolean isSub_active() {
        return sub_active;
    }

    public void setSub_active(boolean sub_active) {
        this.sub_active = sub_active;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCom_reg_no() {
        return com_reg_no;
    }

    public void setCom_reg_no(String com_reg_no) {
        this.com_reg_no = com_reg_no;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }
}
