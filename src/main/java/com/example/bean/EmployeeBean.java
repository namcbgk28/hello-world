package com.example.bean;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("employeeBean")
@ViewScoped
public class EmployeeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Employee> employees;
    private Employee selectedEmployee;
    private String searchCode;
    private EmployeeDAO employeeDAO;
    private String message;
    
    public EmployeeBean() {
        employeeDAO = new EmployeeDAO();
        loadAllEmployees();
    }
    
    public void loadAllEmployees() {
        try {
            employees = employeeDAO.getAllEmployees();
            message = "Loaded " + (employees != null ? employees.size() : 0) + " employees";
        } catch (Exception e) {
            message = "Error loading data: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public void searchEmployee() {
        if (searchCode != null && !searchCode.trim().isEmpty()) {
            selectedEmployee = employeeDAO.getEmployeeByCode(searchCode.trim());
            if (selectedEmployee != null) {
                message = "Found employee: " + selectedEmployee.getEmployeeName();
            } else {
                message = "Employee not found with code: " + searchCode;
            }
        } else {
            message = "Please enter employee code";
        }
    }
    
    public void testConnection() {
        if (employeeDAO.testConnection()) {
            message = "Database connection successful!";
        } else {
            message = "Database connection failed!";
        }
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }
    
    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public String getSearchCode() {
        return searchCode;
    }
    
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}