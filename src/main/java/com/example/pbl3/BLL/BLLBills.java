package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class BLLBills {
    //Cac ham lien quan den Bill
    public static List<Bill> getListBill() {
        return DatabaseHelper.GetAllBill();
    }

    public static boolean AddBill(Bill b) {
        return DatabaseHelper.InsertBill(b);
    }

    public static List<Bill> searchBill(String txt) {
        List<Bill> billList = DatabaseHelper.GetAllBill();
        List<Bill> list = new ArrayList<>();
        for (Bill bill : billList)
            if (bill.getCashierName().toLowerCase().contains(txt.toLowerCase()) || bill.getCashier_gmail().contains(txt) ||
                    bill.getCustomerName().toLowerCase().contains(txt.toLowerCase()) || bill.getPay().contains(txt) || bill.getDate().toString().contains(txt)) {
                if (bill.isStatus())
                    list.add(bill);
            }

        return list;
    }

    public static boolean DeleteBill(int idBill) {
        return DatabaseHelper.DeleteBill(idBill);
    }

    public static Bill getBillByIDBill(int IDBill) {
        List<Bill> billList = BLLBills.getListBill();
        for (Bill bill : billList)
            if (bill.getID_Bill() == IDBill)
                return bill;
        return null;
    }

    public static int getMaxIDBill() {
        int max = 0;
        List<Bill> billList = BLLBills.getListBill();
        for (Bill bill : billList) {
            if (bill.getID_Bill() > max) max = bill.getID_Bill();
        }
        return max;
    }

    //Cac ham lien quan den DetailBill
    public static List<DetailBillDB> getListDetailBill() {
        return DatabaseHelper.GetAllDetailBill();
    }

    public static boolean AddDetailBill(DetailBillDB db) {
        return DatabaseHelper.InsertDetailBill(db);
    }

    public static List<DetailBillDB> getDetailBillByIDBill(int IDBill) {
        List<DetailBillDB> detailBillDBS = BLLBills.getListDetailBill();
        List<DetailBillDB> list = new ArrayList<>();
        for (DetailBillDB detailBillDB : detailBillDBS)
            if (IDBill == detailBillDB.getIdBill())
                list.add(detailBillDB);
        return list;
    }

    public static int GetQuantityProductBought(String productName) {
        int quantity = 0;
        List<DetailBillDB> detailBillDBS = BLLBills.getListDetailBill();
        for (DetailBillDB detailBillDB : detailBillDBS)
            if (detailBillDB.getProductName().equals(productName))
                quantity += detailBillDB.getQuantity();
        return quantity;
    }

    public static ListTopProduct loadTopProduct() {
        List<DetailBillDB> detailBillDBS = BLLBills.getListDetailBill();
        ListTopProduct listTopProduct = new ListTopProduct();
        int no = 1;
        for (DetailBillDB detailBillDB : detailBillDBS) {
            listTopProduct.add(new TopProduct(no, detailBillDB.getProductName(), detailBillDB.getQuantity()));
            no++;
        }
        return listTopProduct.GetTop5Product();
    }

    public static ListTopSeller loadTopSeller() {
        List<Bill> billList = BLLBills.getListBill();
        ListTopSeller listTopSeller = new ListTopSeller();
        int no = 1;
        for (Bill bill : billList) {
            listTopSeller.add(new TopSeller(no, bill.getCashierName(), bill.getTotal_Money()));
            no++;
        }
        return listTopSeller.GetTop5Product();
    }

    public static List<Double> GetListSales(String txtStart, String txtEnd) {

        return DatabaseHelper.ListSales(txtStart, txtEnd);
    }

    public static List<String> GetListCategory(String txtStart, String txtEnd) {
        return DatabaseHelper.ListCategory(txtStart, txtEnd);
    }

    public static List<Double> GetListSalesBarchart(String txtStart, String txtEnd, boolean Type) {

        return DatabaseHelper.ListSalesBarchart(txtStart, txtEnd, Type);
    }

    public static List<String> GetListDate(String txtStart, String txtEnd, boolean Type) {
        return DatabaseHelper.ListDate(txtStart, txtEnd, Type);
    }

    public static int GetQuantity(String txtStart, String txtEnd, boolean Type) {
        return DatabaseHelper.QuantityBill(txtStart, txtEnd, Type);
    }

}
