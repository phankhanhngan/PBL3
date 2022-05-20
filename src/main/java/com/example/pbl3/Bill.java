package com.example.pbl3;

import java.util.Date;

public class Bill {
    private int ID_Bill;
    private int ID_Customer;
    private String customerName;
    private String Cashier_Phone;
    private String cashierName;
    private Date date;
    private double Total_Money;

    public Bill(int ID_Bill, int ID_Customer, String customerName, String cashier_Phone, String cashierName, Date date, double total_Money)
    {
        this.ID_Bill = ID_Bill;
        this.ID_Customer = ID_Customer;
        this.customerName = customerName;
        this.Cashier_Phone = cashier_Phone;
        this.cashierName = cashierName;
        this.date = date;
        this.Total_Money = total_Money;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public int getID_Bill() {
        return ID_Bill;
    }

    public void setID_Bill(int ID_Bill) {
        this.ID_Bill = ID_Bill;
    }

    public int getID_Customer() {
        return ID_Customer;
    }

    public void setID_Customer(int ID_Customer) {
        this.ID_Customer = ID_Customer;
    }

    public String getCashier_Phone() {
        return Cashier_Phone;
    }

    public void setCashier_Phone(String cashier_Phone) {
        Cashier_Phone = cashier_Phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal_Money() {
        return Total_Money;
    }

    public void setTotal_Money(double total_Money) {
        Total_Money = total_Money;
    }
}