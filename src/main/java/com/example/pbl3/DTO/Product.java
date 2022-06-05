//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.pbl3;

import java.util.Date;

public class Product {
    private String Barcode;
    private String ProductName;
    private float salePrice;
    private String Category;
    private int quantity;

    public Product(String barcode, String productName, float salePrice, String category, int quantity) {
        this.Barcode = barcode;
        this.ProductName = productName;
        this.salePrice = salePrice;
        this.Category = category;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return this.Barcode;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public float getSalePrice() {
        return this.salePrice;
    }

    public String getCategory() {
        return this.Category;
    }

    public int getQuantity() {return this.quantity;}

    public void setBarcode(String barcode) {
        this.Barcode = barcode;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setQuantity(int quant) { this.quantity = quant; }
}
