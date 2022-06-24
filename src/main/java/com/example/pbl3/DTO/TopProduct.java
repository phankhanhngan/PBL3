package com.example.pbl3.DTO;

import java.util.Comparator;

public class TopProduct  implements Comparable<TopProduct> {
    private int No;
    private String ProductName;
    private int quantity;

    public TopProduct(int no, String productName, int quantity) {
        No = no;
        ProductName = productName;
        this.quantity = quantity;
    }

    public int getNo() {
        return No;
    }

    public String getProductName() {
        return ProductName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNo(int no) {
        No = no;
    }

    @Override
    public int compareTo(TopProduct o) {
        return o.getQuantity() - this.getQuantity();
    }
}
