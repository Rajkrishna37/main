package com.loginapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // MySQL connection details
    private static final String URL = "jdbc:mysql://localhost:3306/loginapp";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Method to get connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Test connection
    public static void main(String[] args) {
        if (getConnection() != null) {
            System.out.println("Database Connected!");
        } else {
            System.out.println("Connection Failed!");
        }
    }
}
