package com.example.pbl3.DTO;

import java.sql.Date;

public class Import {
    private int import_id;
    private int supplier_id;
    private String supplier_name;
    private Date import_date;
    private Double total;
    private String cashier_name;
    private String cashier_gmail;

    public Import(int import_id, int supplier_id ,String supplier_name, Date import_date, Double total, String cashier_name,String cashier_gmail) {
        this.import_id = import_id;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.import_date = import_date;
        this.total = total;
        this.cashier_name = cashier_name;
        this.cashier_gmail = cashier_gmail;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public String getCashier_gmail() {
        return cashier_gmail;
    }

    public int getImport_id() {
        return import_id;
    }

    public Date getImport_date() {
        return import_date;
    }

    public Double getTotal() {
        return total;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getCashier() {
        return cashier_name;
    }
}
