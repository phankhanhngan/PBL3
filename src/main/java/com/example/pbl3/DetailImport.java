package com.example.pbl3;

public class DetailImport {
    static private int id = 0;
    private String product;
    private int quantity;
    private Double unit_price;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    private int detail_id;
    private Double amount;
    private int importID;

    public DetailImport(String productName, int quantity, Double unitPrice, Double amount) {
        this.detail_id = ++id;
        this.product = productName;
        this.quantity = quantity;
        this.unit_price = unitPrice;
        this.amount = amount;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getImportID() {
        return importID;
    }

    public void setImportID(int importID) {
        this.importID = importID;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
