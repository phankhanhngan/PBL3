package com.example.pbl3;

public class TopProduct {
    private int No;
    private String ProductName;
    private Double Price;
    private Double Sale;
    private String ProductStatus;

    public TopProduct(int no, String productName, Double price, Double sale, String productStatus) {
        No = no;
        ProductName = productName;
        Price = price;
        Sale = sale;
        ProductStatus = productStatus;
    }

    public int getNo() {
        return No;
    }

    public String getProductName() {
        return ProductName;
    }

    public Double getPrice() {
        return Price;
    }

    public Double getSale() {
        return Sale;
    }

    public String getProductStatus() {
        return ProductStatus;
    }

    public void setNo(int no) {
        No = no;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public void setSale(Double sale) {
        Sale = sale;
    }

    public void setProductStatus(String productStatus) {
        ProductStatus = productStatus;
    }
}
