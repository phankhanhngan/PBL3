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
        for(int i=0; i<importList.size(); i++)
            if(importList.get(i).getSupplier_name().contains(txt) || importList.get(i).getCashier().contains(txt) || importList.get(i).getImport_date().toString().contains(txt))
                list.add(importList.get(i));
        return list;
    }

    public static int getMaxImportID()
    {
        int max = 0;
        List<Import> importList = getListImport();
        for(int i=0; i<importList.size(); i++)
            if(importList.get(i).getImport_id() > max) max = importList.get(i).getImport_id();
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
        for(int i=0; i<detailImports.size(); i++)
            if(detailImports.get(i).getImportID() == id)
                list.add(detailImports.get(i));
        return list;
    }

    public static int GetQuantityProductImport(String productname)
    {
        int quantity = 0;
        List<DetailImport> detailImports = getListDetailImport();
        for(int i=0; i<detailImports.size(); i++)
            if(detailImports.get(i).getProduct().equals(productname))
                quantity += detailImports.get(i).getQuantity();
        return quantity;
    }
}
