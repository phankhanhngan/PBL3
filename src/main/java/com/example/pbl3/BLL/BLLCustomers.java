package com.example.pbl3.BLL;

import com.example.pbl3.OpenUI;
import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;

import java.util.ArrayList;
import java.util.List;
public class BLLCustomers {
    private static OpenUI openUI = new OpenUI();
    public static List<Customer> getListCustomer()
    {
        return DatabaseHelper.getAllCustomer();
    }

    public static List<Customer> searchCustomer(String txt)
    {
        List<Customer> listCustomer = new ArrayList<>();
        List<Customer> listFullCustomer = getListCustomer();
        for (int i=0; i<listFullCustomer.size(); i++)
        {
            if(listFullCustomer.get(i).getFirstname().contains(txt) || listFullCustomer.get(i).getLastname().contains(txt) ||
                    listFullCustomer.get(i).getGmail().contains(txt) || listFullCustomer.get(i).getPhone().contains(txt) ||
                    listFullCustomer.get(i).getAddress().contains(txt) || listFullCustomer.get(i).getGender().contains(txt))
            {
                listCustomer.add(listFullCustomer.get(i));
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
        for(int i=0; i<customers.size(); i++)
            if(customers.get(i).getID() == ID)
                return customers.get(i);
        return null;
    }

    public static Customer getCustomerByCustomerPhone(String phone)
    {
        List<Customer> customers = BLLCustomers.getListCustomer();
        for(int i=0; i<customers.size(); i++)
            if(customers.get(i).getPhone().equals(phone))
                return customers.get(i);
        return null;
    }
}
