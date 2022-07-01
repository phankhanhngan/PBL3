package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.Supplier;

import java.util.ArrayList;
import java.util.List;

public class BLLSuppliers {
    public static List<Supplier> getListSupplier() {
        return DatabaseHelper.GetAllSupplier();
    }

    public static boolean AddSupplier(Supplier s) {
        return DatabaseHelper.InsertSupplier(s);
    }

    public static boolean UpdateSupplier(Supplier s) {
        return DatabaseHelper.UpdateSupplier(s);
    }

    public static boolean DeleteSupplier(int ID) {
        return DatabaseHelper.DeleteSupplier(ID);
    }

    public static Supplier getSupplierByNameSupplier(String nameSupplier) {
        List<Supplier> supplierList = BLLSuppliers.getListSupplier();
        for (Supplier supplier : supplierList)
            if (supplier.getSup_Name().equals(nameSupplier))
                return supplier;
        return null;
    }

    public static List<Supplier> searchSupplier(String txt) {
        List<Supplier> list = getListSupplier();
        List<Supplier> supplierList = new ArrayList<>();
        for (Supplier supplier : list) {
            if (supplier.getSup_Name().toLowerCase().contains(txt.toLowerCase()) || supplier.getSup_Phone().contains(txt))
                supplierList.add(supplier);
        }
        return supplierList;
    }

}
