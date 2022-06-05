package com.example.pbl3;

import com.example.pbl3.View.ProductController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetailBill {
    static int stt = 0;
    private int STT;
    private String Product;
    private int Quantity;
    private double unitPrice;
    private double intoMoney;
    public DetailBill(String Product, int Quantity)
    {
        stt++;
        STT = stt;
        this.Product = Product;
        this.Quantity = Quantity;
        DatabaseConnection ConnectNow = new DatabaseConnection();
        Connection connectDB = ConnectNow.getConnection();
        String query = "select SalePrice from product where ProductName = '" + Product + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while(queryResult.next()) {
                unitPrice = queryResult.getDouble("SalePrice");
            }
        } catch (SQLException var14) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, (String)null, var14);
            var14.printStackTrace();
        }
        intoMoney = unitPrice * Quantity;
    }

    public String getProduct() {
        return Product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getSTT() {
        return STT;
    }

    public double getUnitPrice() {
        return unitPrice;
    }


    public double getIntoMoney() {
        return intoMoney;
    }

    public static void setSTT()
    {
        stt = 0;
    }

}
