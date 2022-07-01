package com.example.pbl3.DTO;

import java.io.InputStream;

public class Product {
    private String serial;
    private String ProductName;
    private double salePrice;
    private InputStream image;
    private String Category;
    private int quantity;

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Product(String serial, String productName, double salePrice, InputStream image, String category, int quantity) {
        this.serial = serial;
        this.ProductName = productName;
        this.salePrice = salePrice;
        this.image = image;
        this.Category = category;
        this.quantity = quantity;
    }


    public String getSerial() {
        return serial;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public double getSalePrice() {
        return this.salePrice;
    }

    public String getCategory() {
        return this.Category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public void setQuantity(int quant) {
        this.quantity = quant;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
