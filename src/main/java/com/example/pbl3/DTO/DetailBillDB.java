package com.example.pbl3;

public class DetailBillDB {
    private int idDetailBill;
    private int idBill;
    private int quantity;
    private String productName;

    public DetailBillDB(int idDetailBill, int idBill, int quantity, String productName) {
        this.idDetailBill = idDetailBill;
        this.idBill = idBill;
        this.quantity = quantity;
        this.productName = productName;
    }

    public int getIdDetailBill() {
        return idDetailBill;
    }

    public int getIdBill() {
        return idBill;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
