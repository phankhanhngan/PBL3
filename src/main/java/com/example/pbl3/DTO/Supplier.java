package com.example.pbl3.DTO;

public class Supplier {
    private int Sup_Id;
    private String Sup_Name;
    private String Sup_Address;
    private String Sup_Phone;


    public Supplier(int sup_Id, String sup_Name, String sup_Address, String sup_Phone) {
        Sup_Id = sup_Id;
        Sup_Name = sup_Name;
        Sup_Address = sup_Address;
        Sup_Phone = sup_Phone;
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

    public String getSup_Phone() {
        return Sup_Phone;
    }

}
