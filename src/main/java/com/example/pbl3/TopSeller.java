package com.example.pbl3;

public class TopSeller {
    private int no;
    private String seller_Name;
    private double revenue;

    public TopSeller(int no, String seller_Name, double revenue) {
        this.no = no;
        this.seller_Name = seller_Name;
        this.revenue = revenue;
    }

    public int getNo() {
        return no;
    }

    public String getSeller_Name() {
        return seller_Name;
    }

    public double getRevenue() {
        return revenue;
    }
}
