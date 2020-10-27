package com.dao;

import com.model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAllEmployees();
    Employee findEmployeeById(Long id);
    void addNewEmployee(Employee employee);
    Employee updateEmployeeById(Long id, String departmentId, String jobTitle);
    void deleteEmployeeById(Long id);
}
