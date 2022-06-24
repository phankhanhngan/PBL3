package com.example.pbl3.DTO;

import com.example.pbl3.BLL.BLLProducts;
import com.example.pbl3.BLL.BLLProject;


public class DetailBill {
    static int stt = 0;
    private int STT;
    private String Product;
    private int Quantity;
    private double unitPrice;
    private double intoMoney;
    public DetailBill(String Product, int Quantity)
    {
        stt++;
        STT = stt;
        this.Product = Product;
        this.Quantity = Quantity;
        this.unitPrice = BLLProducts.getProductByProductName(Product).getSalePrice();
        intoMoney = unitPrice * Quantity;
    }

    public DetailBill(String Product, int Quantity, double unitPrice)
    {
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

    public int getSTT() {
        return STT;
    }

    public double getUnitPrice() {
        return unitPrice;
    }


    public double getIntoMoney() {
        return intoMoney;
    }

    public static void setSTT()
    {
        stt = 0;
    }

}
