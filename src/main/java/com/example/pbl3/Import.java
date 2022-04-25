package com.example.pbl3;

import java.time.LocalDate;
import java.util.Date;

public class Import {
    private String product_name;
    private String supplier_name;
    private int quantity;
    private int import_id;
    private Date import_date;

    public Date getImport_date() {
        return import_date;
    }

    public void setImport_date(Date import_date) {
        this.import_date = import_date;
    }

    public int getImport_id() {
        return import_id;
    }

    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    public Import( int import_id, String product_name,int quantity, String supplier_name,  Date date) {
        this.product_name = product_name;
        this.supplier_name = supplier_name;
        this.quantity = quantity;
        this.import_id = import_id;
        this.import_date = date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_ID) {
        this.supplier_name = supplier_ID;
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
