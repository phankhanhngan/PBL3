package com.example.pbl3;

import java.util.Date;

public class Customer {
    private int ID;
    private String firstname;
    private String lastname;
    private String gmail;
    private String phone;
    private String gender;
    private Date birthday;
    private String address;

    public Customer(int ID, String firstname, String lastname, String gmail, String phone, String gender, Date birthday,String address)
    {
        this.ID = ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gmail = gmail;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }
}
