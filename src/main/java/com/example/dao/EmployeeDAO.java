package com.example.dao;

import com.example.model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mt_employee_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345678";
    
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT employee_code, employee_name, employee_age, date_of_birth FROM Mt_employee";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeCode(rs.getString("employee_code"));
                employee.setEmployeeName(rs.getString("employee_name"));
                employee.setEmployeeAge(rs.getInt("employee_age"));
                employee.setDateOfBirth(rs.getDate("date_of_birth"));
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting employee list: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    public Employee getEmployeeByCode(String employeeCode) {
        String sql = "SELECT employee_code, employee_name, employee_age, date_of_birth FROM Mt_employee WHERE employee_code = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employeeCode);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeCode(rs.getString("employee_code"));
                    employee.setEmployeeName(rs.getString("employee_name"));
                    employee.setEmployeeAge(rs.getInt("employee_age"));
                    employee.setDateOfBirth(rs.getDate("date_of_birth"));
                    return employee;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting employee by code: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found in classpath");
            System.err.println("Please deploy postgresql-42.7.1.jar to WildFly deployments folder");
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }
    
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            return false;
        }
    }
    
    public static void testConnection2() throws SQLException {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("connected successfully");

			String sql = "SELECT * FROM Mt_employee LIMIT 1";
			try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {
					String code = rs.getString("employee_code");
					String name = rs.getString("employee_name");
					int age = rs.getInt("employee_age");
					java.sql.Date dob = rs.getDate("date_of_birth");

					System.out.println("First Record: ");
					System.out.println("Code: " + code);
					System.out.println("Name: " + name);
					System.out.println("Age: " + age);
					System.out.println("Dob: " + dob);
				} else {
					System.out.println("There is no record found.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) throws SQLException {
    	EmployeeDAO emp = new EmployeeDAO();
		System.out.print(emp.testConnection());
	}
}