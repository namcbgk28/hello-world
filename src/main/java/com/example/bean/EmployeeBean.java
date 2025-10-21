package com.example.bean;

import com.example.dao.EmployeeDAO;
import com.example.model.Employee;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("employeeBean")
@ViewScoped
public class EmployeeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Employee> employees;
    private Employee selectedEmployee;
    private Employee newEmployee;
    private String searchCode;
    private EmployeeDAO employeeDAO;
    private String message;
    private boolean isEditMode;
    private String actionMode; // "add", "edit", "view"
    
    public EmployeeBean() {
        employeeDAO = new EmployeeDAO();
        newEmployee = new Employee();
        actionMode = "view";
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
    
    public String prepareAddEmployee() {
        newEmployee = new Employee();
        newEmployee.setDateOfBirth(new Date());
        actionMode = "add";
        message = "Ready to add new employee";
        return "add-employee?faces-redirect=true";
    }
    
    public String prepareEditEmployee(Employee employee) {
        selectedEmployee = employee;
        newEmployee = new Employee();
        newEmployee.setEmployeeCode(employee.getEmployeeCode());
        newEmployee.setEmployeeName(employee.getEmployeeName());
        newEmployee.setEmployeeAge(employee.getEmployeeAge());
        newEmployee.setDateOfBirth(employee.getDateOfBirth());
        actionMode = "edit";
        message = "Ready to edit employee: " + employee.getEmployeeName();
        return "edit-employee?faces-redirect=true";
    }
    
    public String saveEmployee() {
        try {
            boolean success = false;
            
            if ("add".equals(actionMode)) {
                // Validate employee code uniqueness
                if (employeeDAO.isEmployeeCodeExists(newEmployee.getEmployeeCode())) {
                    message = "Employee code already exists: " + newEmployee.getEmployeeCode();
                    return null; // Stay on current page
                }
                success = employeeDAO.createEmployee(newEmployee);
                if (success) {
                    message = "Employee created successfully: " + newEmployee.getEmployeeName();
                } else {
                    message = "Failed to create employee";
                    return null; // Stay on current page
                }
            } else if ("edit".equals(actionMode)) {
                success = employeeDAO.updateEmployee(newEmployee);
                if (success) {
                    message = "Employee updated successfully: " + newEmployee.getEmployeeName();
                } else {
                    message = "Failed to update employee";
                    return null; // Stay on current page
                }
            }
            
            if (success) {
                loadAllEmployees();
                actionMode = "view";
                newEmployee = new Employee();
                return "index?faces-redirect=true"; // Navigate back to list
            }
            
        } catch (Exception e) {
            message = "Error saving employee: " + e.getMessage();
            e.printStackTrace();
        }
        
        return null; // Stay on current page if there's an error
    }
    
    public void deleteEmployee(Employee employee) {
        try {
            boolean success = employeeDAO.deleteEmployee(employee.getEmployeeCode());
            if (success) {
                message = "Employee deleted successfully: " + employee.getEmployeeName();
                loadAllEmployees();
                if (selectedEmployee != null && selectedEmployee.getEmployeeCode().equals(employee.getEmployeeCode())) {
                    selectedEmployee = null;
                }
            } else {
                message = "Failed to delete employee";
            }
        } catch (Exception e) {
            message = "Error deleting employee: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    public String cancelAction() {
        actionMode = "view";
        newEmployee = new Employee();
        message = "Action cancelled";
        return "index?faces-redirect=true";
    }
    
    public void viewEmployee(Employee employee) {
        selectedEmployee = employee;
        actionMode = "view";
        message = "Viewing employee: " + employee.getEmployeeName();
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
    
    public Employee getNewEmployee() {
        return newEmployee;
    }
    
    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }
    
    public boolean isEditMode() {
        return isEditMode;
    }
    
    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }
    
    public String getActionMode() {
        return actionMode;
    }
    
    public void setActionMode(String actionMode) {
        this.actionMode = actionMode;
    }
    
    public boolean isAddMode() {
        return "add".equals(actionMode);
    }
    
    public boolean isEditModeActive() {
        return "edit".equals(actionMode);
    }
    
    public boolean isViewMode() {
        return "view".equals(actionMode);
    }
}