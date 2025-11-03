package com.snc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class to manage JDBC connections.
 * Update the URL, USERNAME, and PASSWORD constants to match your database configuration.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // Example H2 in‑memory DB
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        // Prevent instantiation
    }

    /**
     * Returns a new {@link Connection} to the configured database.
     *
     * @return a live JDBC connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
