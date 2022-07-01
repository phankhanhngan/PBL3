package com.example.pbl3.DTO;

import java.sql.Date;

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

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public Date getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }
}
