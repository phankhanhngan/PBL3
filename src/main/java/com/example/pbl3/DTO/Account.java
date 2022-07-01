package com.example.pbl3.DTO;

public class Account {
    private String firstName;
    private String lastName;
    private String gmail;
    private String phone;
    private String username;
    private String password;
    private String address;
    private String typeOfUser;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

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

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String isTypeOfUser() {
        return typeOfUser;
    }
}

