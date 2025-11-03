package com.snc.db;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link EmployeeDAO} using an H2 in‑memory database.
 */
public class EmployeeDAOTest {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;
    private EmployeeDAO dao;

    @Before
    public void setUp() throws Exception {
        // Initialize H2 in‑memory DB and create the employees table
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE employees (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255), " +
                    "salary DOUBLE" +
                    ")");
        }
        // Override DatabaseConnection to return our H2 connection
        DatabaseConnectionOverride.setConnection(connection);
        dao = new EmployeeDAO();
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP ALL OBJECTS");
            }
            connection.close();
        }
    }

    @Test
    public void testCRUDOperations() throws SQLException {
        Employee emp = new Employee(1, "John Doe", "john.doe@example.com", 75000.0);
        // Create
        dao.addEmployee(emp);
        // Read
        Employee fetched = dao.getEmployeeById(1);
        assertNotNull(fetched);
        assertEquals("John Doe", fetched.getName());
        // Update
        emp.setName("Jane Doe");
        dao.updateEmployee(emp);
        Employee updated = dao.getEmployeeById(1);
        assertEquals("Jane Doe", updated.getName());
        // Delete
        dao.deleteEmployee(1);
        assertNull(dao.getEmployeeById(1));
    }

    @Test
    public void testGetAllEmployees() throws SQLException {
        dao.addEmployee(new Employee(1, "Alice", "alice@example.com", 60000.0));
        dao.addEmployee(new Employee(2, "Bob", "bob@example.com", 65000.0));
        List<Employee> all = dao.getAllEmployees();
        assertEquals(2, all.size());
    }
}

/**
 * Helper class to override the static connection provider used by {@link DatabaseConnection}.
 * This is only for testing purposes.
 */
class DatabaseConnectionOverride extends DatabaseConnection {
    private static Connection testConnection;

    static void setConnection(Connection conn) {
        testConnection = conn;
    }

    public static Connection getConnection() throws SQLException {
        return testConnection != null ? testConnection : DatabaseConnection.getConnection();
    }
}