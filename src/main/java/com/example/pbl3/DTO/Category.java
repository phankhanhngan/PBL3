package com.example.pbl3.DTO;

public class Category {
    private int Cate_Id;
    private String Cate_Name;

    public Category(int cate_Id, String cate_Name) {
        Cate_Id = cate_Id;
        Cate_Name = cate_Name;
    }

    public int getCate_Id() {
        return Cate_Id;
    }

    public String getCate_Name() {
        return Cate_Name;
    }

    public void setCate_Id(int cate_Id) {
        Cate_Id = cate_Id;
    }

    public void setCate_Name(String cate_Name) {
        Cate_Name = cate_Name;
    }
}
