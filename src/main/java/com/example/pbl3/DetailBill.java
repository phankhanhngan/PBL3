package com.example.pbl3;

public class DetailBill {
    private String Product;
    private int Quantity;
    public DetailBill(String Product, int Quantity)
    {
        this.Product = Product;
        this.Quantity = Quantity;
    }

    public String getProduct() {
        return Product;
    }

    public int getQuantity() {
        return Quantity;
    }

}
