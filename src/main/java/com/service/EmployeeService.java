package com.service;

import com.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmployees();
    Employee findEmployeeById(Long id);
    void addNewEmployee(Employee employee);
    Employee updateEmployeeById(Long id, int departmentId, String jobTitle);
    void deleteEmployeeById(Long id);
}
