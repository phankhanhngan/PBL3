package com.example.pbl3;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public DatabaseConnection() {
    }

    public Connection getConnection() {
        String databaseName = "demo_db";
        String databaseUser = "root";
        String databasePassword = "thinhmnsd2002";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("connect seccessfully!");
        } catch (Exception var6) {
            var6.printStackTrace();
            var6.getCause();
        }

        return this.databaseLink;
    }
}
