package com.example.pbl3;

import java.sql.*;

public class DatabaseHelper {
    public static Connection connection = new DatabaseConnection().getConnection();
    public static String getTotalCustomer() {
        String total ="0";
        try {
            String query = "select count(ID) as total from customer";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next())
            {
                total = result.getString("total");
            }
        }
        catch (SQLException e)
        {

        }
        return total;
    }
    public static String getTotalProduct() {
        String total ="0";
        try {
            String query = "select count(Barcode) as total from product;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next())
            {
                total = result.getString("total");
            }
        }
        catch (SQLException e)
        {

        }
        return total;
    }
    public static String getTotalQuote() {
        String total ="0";
        try {
            String query = "select count(ID_Bill) as total from bill";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next())
            {
                total = result.getString("total");
            }
        }
        catch (SQLException e)
        {

        }
        return total;
    }
    public static int GetQuantityByProductname(String prod_Name)
    {

        int imp = 0,bill =0;
        try {
            PreparedStatement quantityImport = null;
            PreparedStatement quantityBill = null;

            String queryQuantityImport = "select sum(quantity) as quantity from detailimport where product = ?  group by product";
            quantityImport = connection.prepareStatement(queryQuantityImport);
            String queryQuantityBill = "select sum(Quantity) as quantity from detailbill left join product " +
                    "on detailbill.Product = product.ProductName where product.ProductName = ? group by product.ProductName";
            quantityBill = connection.prepareStatement(queryQuantityBill);

            quantityImport.setString(1, prod_Name);
            quantityBill.setString(1, prod_Name);
            ResultSet rs1 = quantityImport.executeQuery();
            ResultSet rs2 = quantityBill.executeQuery();

            if (rs1.next()) {imp = rs1.getInt("quantity");}
            if (rs2.next()) {bill = rs2.getInt("quantity");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (imp - bill);
    }



}
