package com.example.dardev.collegekart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Dev on 17-03-2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

    private String firstname;
    private  String lastname;
    private String branch;
    private String year;
    private String mobile;
    private String email;
    private String password;


            public User(String firstname, String lastname, String branch, String year, String mobile, String email, String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.branch = branch;
        this.year = year;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }
    public User() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
