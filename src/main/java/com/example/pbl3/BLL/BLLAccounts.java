package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;
import com.example.pbl3.OpenUI;

import java.util.ArrayList;
import java.util.List;

public class BLLAccounts {
    private static OpenUI openUI = new OpenUI();

    public static String DecodePassword(String pass) { return DatabaseHelper.DecodePassword(pass); }

    public static boolean CheckAccount(String username, String password)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        for(int i=0; i<listAccount.size(); i++)
        {
            if(username.equals(listAccount.get(i).getUsername()) && BLLAccounts.DecodePassword(password).equals(listAccount.get(i).getPassword()))
            {
                BLLProject.setGmail(listAccount.get(i).getGmail());
                BLLProject.setNameCashier(listAccount.get(i).getFirstName() + " " + listAccount.get(i).getLastName());
                BLLProject.setPhoneCashier(listAccount.get(i).getPhone());
                BLLProject.setAddress(listAccount.get(i).getAddress());
                BLLProject.setUsername(listAccount.get(i).getUsername());
                boolean type;
                if(listAccount.get(i).isTypeOfUser().equals("Manager")) type = true;
                else type = false;
                BLLProject.setTypecashier(type);
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
                BLLProject.setGmail(gmail);
                return true;
            }
        }
        return false;
    }

    public static boolean UpdatePasswordAccount(String password) {
        return DatabaseHelper.UpdatePasswordAccount(password);
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
}
