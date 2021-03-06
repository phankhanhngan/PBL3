package com.example.pbl3.DTO;

public class DetailImport {
    static private int id = 0;
    private String product;
    private int quantity;
    private Double unit_price;
    private int detail_id;
    private Double amount;
    private int importID;
    private String product_id;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public DetailImport(int importID, String productName, String product_id, int quantity, Double unitPrice, Double amount) {
        this.importID = importID;
        this.detail_id = ++id;
        this.product = productName;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unitPrice;
        this.amount = amount;
    }


    public Double getAmount() {
        return amount;
    }

    public int getImportID() {
        return importID;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        DetailImport.id = id;
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setImportID(int importID) {
        this.importID = importID;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

}
