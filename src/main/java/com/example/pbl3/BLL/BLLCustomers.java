package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class BLLCustomers {
    public static List<Customer> getListCustomer()
    {
        return DatabaseHelper.getAllCustomer();
    }

    public static List<Customer> searchCustomer(String txt)
    {
        List<Customer> listCustomer = new ArrayList<>();
        List<Customer> listFullCustomer = getListCustomer();
        for (Customer customer : listFullCustomer) {
            if (customer.getFirstname().toLowerCase().contains(txt.toLowerCase()) || customer.getLastname().toLowerCase().contains(txt.toLowerCase()) ||
                    customer.getGmail().contains(txt) || customer.getPhone().contains(txt) ||
                    customer.getAddress().contains(txt) || customer.getGender().contains(txt)) {
                listCustomer.add(customer);
            }
        }
        return listCustomer;
    }

    public static boolean AddCustomer(Customer c)
    {
        return DatabaseHelper.InsertCustomer(c);
    }

    public static boolean UpdateCustomer(Customer c)
    {
        return DatabaseHelper.UpdateCustomer(c);
    }

    public static boolean DeleteCustomer(int ID)
    {
        return DatabaseHelper.DeleteCustomer(ID);
    }

    public static Customer getCustomerByID(int ID)
    {
        List<Customer> customers = BLLCustomers.getListCustomer();
        for (Customer customer : customers)
            if (customer.getID() == ID)
                return customer;
        return null;
    }

    public static Customer getCustomerByCustomerPhone(String phone)
    {
        List<Customer> customers = BLLCustomers.getListCustomer();
        for (Customer customer : customers)
            if (customer.getPhone().equals(phone))
                return customer;
        return null;
    }
}
