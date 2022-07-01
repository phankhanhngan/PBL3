package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class BLLAccounts {
    public static String DecodePassword(String pass) { return DatabaseHelper.DecodePassword(pass); }

    public static boolean CheckAccount(String username, String password)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        for (Account account : listAccount) {
            if (username.equals(account.getUsername()) && BLLAccounts.DecodePassword(password).equals(account.getPassword())) {
                BLLProject.setGmail(account.getGmail());
                BLLProject.setNameCashier(account.getFirstName() + " " + account.getLastName());
                BLLProject.setPhoneCashier(account.getPhone());
                BLLProject.setAddress(account.getAddress());
                BLLProject.setUsername(account.getUsername());
                boolean type;
                type = account.isTypeOfUser().equals("Manager");
                BLLProject.setTypecashier(type);
                return true;
            }
        }
        return false;
    }

    public static boolean CheckMail(String gmail)
    {
        List<Account> listAccount = DatabaseHelper.GetAllAccount();
        for (Account account : listAccount) {
            if (account.getGmail().equals(gmail)) {
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
        for (Account account : listAccount)
            if (account.getFirstName().toLowerCase().contains(txt.toLowerCase()) || account.getLastName().toLowerCase().contains(txt.toLowerCase()) ||
                    account.getGmail().contains(txt) || account.getPhone().contains(txt) ||
                    account.getAddress().contains(txt) || account.isTypeOfUser().contains(txt))
                list.add(account);
        return list;
    }
}
