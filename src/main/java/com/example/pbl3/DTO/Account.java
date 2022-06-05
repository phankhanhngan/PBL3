package com.example.pbl3;

public class Account {
    public String firstName;
    public String lastName;
    public String gmail;
    public String phone;
    public String username;
    public String password;
    public String address;
    public String typeOfUser;

    public Account(String fName, String lName, String gmail, String phone, String username, String password, String address, String typeOfUser) {
        this.firstName = fName;
        this.lastName = lName;
        this.gmail = gmail;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.address = address;
        this.typeOfUser = typeOfUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGmail() {
        return gmail;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public void setfName(String fName) {
        this.firstName = fName;
    }

    public void setlName(String lName) {
        this.lastName = lName;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String isTypeOfUser() {
        return typeOfUser;
    }
}
