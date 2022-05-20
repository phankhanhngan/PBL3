package com.example.pbl3;

import java.time.LocalDate;
import java.util.Date;

public class Import {
    private int import_id;
    private String supplier_name;
    private Date import_date;
    private Double total;
    private String cashier;

    public Import(int import_id, String supplier_name, Date import_date, Double total, String cashier) {
        this.import_id = import_id;
        this.supplier_name = supplier_name;
        this.import_date = import_date;
        this.total = total;
        this.cashier = cashier;
    }

    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    public Date getImport_date() {
        return import_date;
    }

    public void setImport_date(Date import_date) {
        this.import_date = import_date;
    }

    public Double getTotal() {
        return total;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }
}
