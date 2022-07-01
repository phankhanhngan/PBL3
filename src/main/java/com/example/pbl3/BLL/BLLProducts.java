package com.example.pbl3.BLL;

import com.example.pbl3.DAL.DatabaseHelper;
import com.example.pbl3.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class BLLProducts {
    public static List<Product> getListProduct() {
        return DatabaseHelper.GetAllProduct();
    }

    public static boolean AddProduct(Product p) {
        return DatabaseHelper.InsertProduct(p);
    }

    public static boolean UpdateProduct(Product p) {
        return DatabaseHelper.UpdateProduct(p);
    }

    public static boolean DeleteProduct(String barcode) {
        return DatabaseHelper.DeleteProduct(barcode);
    }

    public static List<Product> searchProduct(String txt, double min, double max) {
        List<Product> productList = DatabaseHelper.GetAllProduct();
        List<Product> list = new ArrayList<>();
        for (Product product : productList)
            if ((product.getSerial().contains(txt) || product.getProductName().toLowerCase().contains(txt.toLowerCase()) ||
                    product.getCategory().contains(txt)) && product.getSalePrice() >= min) {
                if (max == 0) list.add(product);
                else if (product.getSalePrice() <= max) list.add(product);
            }
        return list;
    }

    public static Product getProductByProductName(String productName) {
        List<Product> productList = BLLProducts.getListProduct();
        for (Product product : productList)
            if (product.getProductName().equals(productName))
                return product;
        return null;
    }
}