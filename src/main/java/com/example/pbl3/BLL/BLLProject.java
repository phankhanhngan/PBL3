package com.example.pbl3.BLL;


import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;
import com.example.pbl3.OpenUI;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class BLLProject {
    private static OpenUI openUI = new OpenUI();

    //Cac ham lien quan den Account
    public static boolean CheckAccount(String username, String password)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        for(int i=0; i<listAccount.size(); i++)
        {
            if(username.equals(listAccount.get(i).getUsername()) && password.equals(listAccount.get(i).getPassword()))
            {
                openUI.setGmail(listAccount.get(i).getGmail());
                openUI.setNameCashier(listAccount.get(i).getFirstName() + " " + listAccount.get(i).getLastName());
                openUI.setPhoneCashier(listAccount.get(i).getPhone());
                openUI.setAddress(listAccount.get(i).getAddress());
                openUI.setUsername(listAccount.get(i).getUsername());
                boolean type;
                if(listAccount.get(i).isTypeOfUser().equals("Manager")) type = true;
                else type = false;
                openUI.setTypecashier(type);
                return true;
            }
        }
        return false;
    }

    public static boolean CheckMail(String gmail)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        for(int i=0; i<listAccount.size(); i++)
        {
            if(listAccount.get(i).getGmail().equals(gmail))
            {
                openUI.setGmail(gmail);
                return true;
            }
        }
        return false;
    }

    public static void UpdatePasswordAccount(String password) {
        DatabaseHelper.UpdatePasswordAccount(password);
    }

    public static boolean AddAccount(Account a)
    {
        return DatabaseHelper.InsertAccount(a);
    }

    public static boolean UpdateAccount(Account a)
    {
        return DatabaseHelper.UpdateAccount(a);
    }

    public static boolean DeleteAccount(String username)
    {
        return DatabaseHelper.DeleteAccount(username);
    }

    public static List<Account> SearchAccount(String txt)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        List<Account> list = new ArrayList<>();
        for(int i=0; i<listAccount.size(); i++)
            if(listAccount.get(i).getFirstName().contains(txt) || listAccount.get(i).getLastName().contains(txt) ||
            listAccount.get(i).getGmail().contains(txt) || listAccount.get(i).getPhone().contains(txt) ||
            listAccount.get(i).getAddress().contains(txt) || listAccount.get(i).isTypeOfUser().contains(txt))
                list.add(listAccount.get(i));
        return list;
    }

    // Cac ham lien quan den Category
    public static List<Category> getListCategory()
    {
        return DatabaseHelper.GetAllCategory();
    }

    public static boolean AddCategory(Category c)
    {
        return DatabaseHelper.InsertCategory(c);
    }

    public static boolean UpdateCategory(Category c)
    {
        return DatabaseHelper.UpdateCategory(c);
    }

    public static boolean DeleteCategory(int id)
    {
        return DatabaseHelper.DeleteCategory(id);
    }

    public static List<Category> SearchCategory(String txt)
    {
        List<Category> list = new ArrayList<>();
        List<Category> categoryList = DatabaseHelper.GetAllCategory();
        for(int i=0; i<categoryList.size(); i++)
            if(categoryList.get(i).getCate_Name().contains(txt))
                list.add(categoryList.get(i));
        return list;
    }


    //Cac ham lien quan den Customer
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

   //Cac ham lien quan den Supplier
    public static List<Supplier> getListSupplier()
    {
        return DatabaseHelper.GetAllSupplier();
    }

    public static boolean AddSupplier(Supplier s)
    {
        return DatabaseHelper.InsertSupplier(s);
    }

    public static boolean UpdateSupplier(Supplier s)
    {
        return DatabaseHelper.UpdateSupplier(s);
    }

    public static boolean DeleteSupplier(int ID)
    {
        return DatabaseHelper.DeleteSupplier(ID);
    }

}

