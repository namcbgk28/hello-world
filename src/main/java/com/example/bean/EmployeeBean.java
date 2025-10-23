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
    private String currentForm; // Current form path
    
    public EmployeeBean() {
        employeeDAO = new EmployeeDAO();
        newEmployee = new Employee();
        actionMode = "view";
        currentForm = "";
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
    
    public void prepareAddEmployee() {
        System.out.println("prepareAddEmployee CALLED");
        newEmployee = new Employee();
        newEmployee.setDateOfBirth(new Date());
        actionMode = "add";
        currentForm = "/employee/create.xhtml";
        message = "Ready to add new employee";
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
    
    public void cancelAction() {
        actionMode = "view";
        currentForm = "";
        newEmployee = new Employee();
        message = "Action cancelled";
    }
    
    public void viewEmployee(Employee employee) {
        selectedEmployee = employee;
        actionMode = "view";
        message = "Viewing employee: " + employee.getEmployeeName();
    }
    
    public void saveEmployee() {
        try {
            if (employeeDAO.addEmployee(newEmployee)) {
                message = "Employee added successfully: " + newEmployee.getEmployeeName();
                actionMode = "view";
                newEmployee = new Employee();
                loadAllEmployees(); // Refresh the list
            } else {
                message = "Failed to add employee";
            }
        } catch (Exception e) {
            message = "Error adding employee: " + e.getMessage();
            e.printStackTrace();
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
    
    public String getCurrentForm() {
        return currentForm;
    }
    
    public void setCurrentForm(String currentForm) {
        this.currentForm = currentForm;
    }
}