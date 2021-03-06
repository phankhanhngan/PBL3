package com.example.pbl3.DTO;

import com.example.pbl3.BLL.BLLProducts;

public class DetailBill {
    static int stt = 0;
    private int STT;
    private String Product;

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setIntoMoney(double intoMoney) {
        this.intoMoney = intoMoney;
    }

    private int Quantity;
    private double unitPrice;
    private double intoMoney;

    public DetailBill(String Product, int Quantity) {
        stt++;
        STT = stt;
        this.Product = Product;
        this.Quantity = Quantity;
        this.unitPrice = BLLProducts.getProductByProductName(Product).getSalePrice();
        intoMoney = unitPrice * Quantity;
    }

    public DetailBill(String Product, int Quantity, double unitPrice) {
        stt++;
        STT = stt;
        this.Product = Product;
        this.Quantity = Quantity;
        this.unitPrice = unitPrice;
        this.intoMoney = unitPrice * Quantity;
    }

    public String getProduct() {
        return Product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public double getIntoMoney() {
        return intoMoney;
    }

    public static void setSTT() {
        stt = 0;
    }

}
