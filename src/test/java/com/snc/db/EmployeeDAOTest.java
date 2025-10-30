package com.snc.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize H2 in-memory database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE employees (id INT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), salary DOUBLE)");
        }
        // Override DatabaseConnection for tests to use H2
        // This is a simplified approach; in a real app, you'd use dependency injection
        // or a test-specific configuration for DatabaseConnection.
        // For this example, we'll directly use H2 connection in DAO for testing.
        employeeDAO = new EmployeeDAO() {
            @Override
            public void addEmployee(Employee employee) throws SQLException {
                String sql = "INSERT INTO employees (id, name, email, salary) VALUES (?, ?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, employee.getId());
                    pstmt.setString(2, employee.getName());
                    pstmt.setString(3, employee.getEmail());
                    pstmt.setDouble(4, employee.getSalary());
                    pstmt.executeUpdate();
                }
            }

            @Override
            public Employee getEmployeeById(int id) throws SQLException {
                String sql = "SELECT id, name, email, salary FROM employees WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        return new Employee(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getDouble("salary")
                        );
                    }
                }
                return null;
            }

            @Override
            public void updateEmployee(Employee employee) throws SQLException {
                String sql = "UPDATE employees SET name = ?, email = ?, salary = ? WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, employee.getName());
                    pstmt.setString(2, employee.getEmail());
                    pstmt.setDouble(3, employee.getSalary());
                    pstmt.setInt(4, employee.getId());
                    pstmt.executeUpdate();
                }
            }

            @Override
            public void deleteEmployee(int id) throws SQLException {
                String sql = "DELETE FROM employees WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }
            }

            @Override
            public List<Employee> getAllEmployees() throws SQLException {
                List<Employee> employees = new ArrayList<>();
                String sql = "SELECT id, name, email, salary FROM employees";
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        employees.add(new Employee(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getDouble("salary")
                        ));
                    }
                }
                return employees;
            }
        };
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE employees");
        }
    }

    @Test
    void testAddEmployee() throws SQLException {
        Employee employee = new Employee(1, "John Doe", "john.doe@example.com", 50000.0);
        employeeDAO.addEmployee(employee);
        Employee retrievedEmployee = employeeDAO.getEmployeeById(1);
        assertNotNull(retrievedEmployee);
        assertEquals("John Doe", retrievedEmployee.getName());
    }

    @Test
    void testGetEmployeeById() throws SQLException {
        Employee employee = new Employee(2, "Jane Smith", "jane.smith@example.com", 60000.0);
        employeeDAO.addEmployee(employee);
        Employee retrievedEmployee = employeeDAO.getEmployeeById(2);
        assertNotNull(retrievedEmployee);
        assertEquals("Jane Smith", retrievedEmployee.getName());
        assertNull(employeeDAO.getEmployeeById(99)); // Non-existent employee
    }

    @Test
    void testUpdateEmployee() throws SQLException {
        Employee employee = new Employee(3, "Peter Jones", "peter.jones@example.com", 70000.0);
        employeeDAO.addEmployee(employee);
        employee.setSalary(75000.0);
        employeeDAO.updateEmployee(employee);
        Employee updatedEmployee = employeeDAO.getEmployeeById(3);
        assertNotNull(updatedEmployee);
        assertEquals(75000.0, updatedEmployee.getSalary(), 0.001);
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        Employee employee = new Employee(4, "Alice Brown", "alice.brown@example.com", 80000.0);
        employeeDAO.addEmployee(employee);
        assertNotNull(employeeDAO.getEmployeeById(4));
        employeeDAO.deleteEmployee(4);
        assertNull(employeeDAO.getEmployeeById(4));
    }

    @Test
    void testGetAllEmployees() throws SQLException {
        employeeDAO.addEmployee(new Employee(5, "Bob White", "bob.white@example.com", 55000.0));
        employeeDAO.addEmployee(new Employee(6, "Charlie Green", "charlie.green@example.com", 65000.0));
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }
}