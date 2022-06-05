package com.example.pbl3;

public class TopProduct {
    private int No;
    private String ProductName;
    private int quantity;

    public TopProduct(int no, String productName, int quantity) {
        No = no;
        ProductName = productName;
        this.quantity = quantity;
    }

    public int getNo() {
        return No;
    }

    public String getProductName() {
        return ProductName;
    }

    public int getQuantity() {
        return quantity;
    }
}
