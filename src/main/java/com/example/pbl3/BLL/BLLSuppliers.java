package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.Supplier;

import java.util.List;

public class BLLSuppliers {
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

    public static Supplier getSupplierByNameSupplier(String nameSupplier)
    {
        List<Supplier> supplierList = BLLSuppliers.getListSupplier();
        for (int i=0; i<supplierList.size(); i++)
            if(supplierList.get(i).getSup_Name().equals(nameSupplier))
                return supplierList.get(i);
        return null;
    }
}
