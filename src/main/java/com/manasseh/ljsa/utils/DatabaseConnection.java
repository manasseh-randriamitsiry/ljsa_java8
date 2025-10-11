package com.manasseh.ljsa.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connection;
    public Connection getConnection(){
        // SQLite database file path (will be created in the project directory)
        String url = "jdbc:sqlite:ljsa.db";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (Exception e){
            PopUp error = new PopUp();
            error.error("Database Error", "Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}