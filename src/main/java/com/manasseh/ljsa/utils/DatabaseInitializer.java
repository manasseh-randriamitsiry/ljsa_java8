package com.manasseh.ljsa.utils;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    /**
     * Initialize the SQLite database with the schema from ljsa_sqlite.sql
     */
    public static void initializeDatabase() {
        Connection connection = null;
        try {
            // Get database connection
            DatabaseConnection dbConnection = new DatabaseConnection();
            connection = dbConnection.getConnection();
            
            if (connection != null) {
                // Read and execute the SQL file
                executeSqlFile(connection);
                System.out.println("Database initialized successfully.");
            } else {
                System.err.println("Failed to establish database connection.");
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Execute SQL statements from the embedded resource file
     * @param connection Database connection
     * @throws IOException If there's an error reading the file
     * @throws SQLException If there's an error executing SQL statements
     */
    private static void executeSqlFile(Connection connection) throws IOException, SQLException {
        StringBuilder sql = new StringBuilder();
        
        // Try to read from embedded resource first, then from file system
        InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("ljsa_sqlite.sql");
        if (inputStream == null) {
            // Fallback to file system
            inputStream = new FileInputStream("ljsa_sqlite.sql");
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines and comments
                if (!line.trim().isEmpty() && !line.trim().startsWith("--")) {
                    sql.append(line).append("\n");
                }
            }
        }
        
        // Split statements by semicolon
        String[] statements = sql.toString().split(";");
        
        try (Statement stmt = connection.createStatement()) {
            for (String statement : statements) {
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty()) {
                    stmt.execute(trimmedStatement);
                }
            }
        }
    }
}