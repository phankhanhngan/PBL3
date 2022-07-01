package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.DetailImport;
import com.example.pbl3.DTO.Import;

import java.util.ArrayList;
import java.util.List;

public class BLLImports {
    public static List<Import> getListImport()
    {
        return DatabaseHelper.GetAllImport();
    }

    public static boolean AddImport(Import i)
    {
        return DatabaseHelper.InsertImport(i);
    }

    public static boolean DeleteImport(int id)
    {
        return DatabaseHelper.DeleteImport(id);
    }

    public static List<Import> searchImport(String txt)
    {
        List<Import> importList = getListImport();
        List<Import> list = new ArrayList<>();
        for (Import anImport : importList)
            if (anImport.getSupplier_name().toLowerCase().contains(txt.toLowerCase()) || anImport.getCashier().toLowerCase().contains(txt.toLowerCase()) || anImport.getImport_date().toString().contains(txt))
                list.add(anImport);
        return list;
    }

    public static int getMaxImportID()
    {
        int max = 0;
        List<Import> importList = getListImport();
        for (Import anImport : importList) if (anImport.getImport_id() > max) max = anImport.getImport_id();
        System.out.println(max);
        return max;
    }


    //Cac ham lien quan den DetailImport
    public static List<DetailImport> getListDetailImport()
    {
        return DatabaseHelper.GetAllDetailImport();
    }

    public static boolean AddDetailImport(DetailImport di)
    {
        return DatabaseHelper.InsertDetailImport(di);
    }

    public static List<DetailImport> getListDetailImportByIDImport(int id)
    {
        List<DetailImport> detailImports = getListDetailImport();
        List<DetailImport> list = new ArrayList<>();
        for (DetailImport detailImport : detailImports)
            if (detailImport.getImportID() == id)
                list.add(detailImport);
        return list;
    }

    public static int GetQuantityProductImport(String productName)
    {
        int quantity = 0;
        List<DetailImport> detailImports = getListDetailImport();
        for (DetailImport detailImport : detailImports)
            if (detailImport.getProduct().equals(productName))
                quantity += detailImport.getQuantity();
        return quantity;
    }
}