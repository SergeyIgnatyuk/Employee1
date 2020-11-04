package com.dao;

import com.model.Employee;

import java.util.List;

/**
 * DAO interface for {@link com.model.Employee}
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface EmployeeDao {
    List<Employee> findAllEmployees();
    Employee findEmployeeById(Long id);
    void addNewEmployee(Employee employee);
    Employee updateEmployeeById(Long id, int departmentId, String jobTitle);
    void deleteEmployeeById(Long id);
}
