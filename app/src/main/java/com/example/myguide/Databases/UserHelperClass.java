package com.example.myguide.Databases;

public class UserHelperClass {

    String fullName, username, phoneNo, password,date, gender;

        public UserHelperClass(){}

    public UserHelperClass(String fullName, String username, String phoneNo, String password, String date, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.date = date;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
