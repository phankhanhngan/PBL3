package com.example.pbl3.DTO;

public class DetailBillDB {
    private int idDetailBill;
    private int idBill;
    private int quantity;
    private String productName;
    private String product_serial;
    private double unit_price;

    public int getIdDetailBill() {
        return idDetailBill;
    }

    public void setIdDetailBill(int idDetailBill) {
        this.idDetailBill = idDetailBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public void setProduct_serial(String product_serial) {
        this.product_serial = product_serial;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public DetailBillDB(int idDetailBill, int idBill, int quantity, String productName, String product_serial, double unit_price) {
        this.idDetailBill = idDetailBill;
        this.idBill = idBill;
        this.quantity = quantity;
        this.productName = productName;
        this.product_serial = product_serial;
        this.unit_price = unit_price;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct_serial() {
        return product_serial;
    }

    public double getUnit_price() {
        return unit_price;
    }
}
