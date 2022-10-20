package com.manasseh.ljsa.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection connection;
    public Connection getConnection(){
        String databaseName = "ljsa";
        String databaseUser = "root";
        String databasePassword = "123456789";
        String url = "jdbc:mysql://localhost/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,databaseUser,databasePassword);
        } catch (Exception e){
            PopUp error = new PopUp();
            error.error("Erreur connection",e.getMessage());
        }
        return connection;
    }
}
