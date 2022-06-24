package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class BLLProducts {
    public static List<Product> getListProduct()
    {
        return DatabaseHelper.GetAllProduct();
    }

    public static boolean AddProduct(Product p)
    {
        return DatabaseHelper.InsertProduct(p);
    }

    public static boolean UpdateProduct(Product p)
    {
        return DatabaseHelper.UpdateProduct(p);
    }

    public static boolean DeleteProduct(String barcode)
    {
        return DatabaseHelper.DeleteProduct(barcode);
    }

    public static List<Product> searchProduct(String txt, double min, double max)
    {
        List<Product> productList = DatabaseHelper.GetAllProduct();
        List<Product> list = new ArrayList<>();
        for(int i=0; i<productList.size(); i++)
            if((productList.get(i).getSerial().contains(txt) || productList.get(i).getProductName().contains(txt) ||
                    productList.get(i).getCategory().contains(txt)) && productList.get(i).getSalePrice() >= min)
            {
                if(max == 0) list.add(productList.get(i));
                else if(productList.get(i).getSalePrice() <= max) list.add(productList.get(i));
            }
        return list;
    }

    public static Product getProductByProductName(String productName)
    {
        List<Product> productList = BLLProducts.getListProduct();
        for(int i=0; i<productList.size(); i++)
            if(productList.get(i).getProductName().equals(productName))
                return productList.get(i);
        return null;
    }

    public static Product getProductBySerial(String serial)
    {
        List<Product> productList = BLLProducts.getListProduct();
        for(int i=0; i<productList.size(); i++)
            if(productList.get(i).getSerial().equals(serial))
                return productList.get(i);
        return null;
    }
}
