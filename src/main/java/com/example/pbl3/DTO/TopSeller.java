package com.example.pbl3.DTO;

public class TopSeller implements Comparable<TopSeller> {
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

    public void setSeller_Name(String seller_Name) {
        this.seller_Name = seller_Name;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public int compareTo(TopSeller o) {
        return  (int)(o.getRevenue() - this.getRevenue());
    }
}
