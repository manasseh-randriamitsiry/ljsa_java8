package com.manasseh.ljsa.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                executeSqlFile(connection, "ljsa_sqlite.sql");
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
     * Execute SQL statements from a file
     * @param connection Database connection
     * @param filePath Path to the SQL file
     * @throws IOException If there's an error reading the file
     * @throws SQLException If there's an error executing SQL statements
     */
    private static void executeSqlFile(Connection connection, String filePath) throws IOException, SQLException {
        StringBuilder sql = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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