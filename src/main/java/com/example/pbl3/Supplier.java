package com.example.pbl3;

public class Supplier {
    private int Sup_Id;
    private String Sup_Name;
    private String Sup_Address;

    public Supplier(int sup_Id, String sup_Name, String sup_Address) {
        Sup_Id = sup_Id;
        Sup_Name = sup_Name;
        Sup_Address = sup_Address;
    }

    public int getSup_Id() {
        return Sup_Id;
    }

    public String getSup_Name() {
        return Sup_Name;
    }

    public String getSup_Address() {
        return Sup_Address;
    }

    public void setSup_Id(int sup_Id) {
        Sup_Id = sup_Id;
    }

    public void setSup_Name(String sup_Name) {
        Sup_Name = sup_Name;
    }

    public void setSup_Address(String sup_Address) {
        Sup_Address = sup_Address;
    }
}
