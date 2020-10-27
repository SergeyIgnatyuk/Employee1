package com.service;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAllEmployees();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeDao.findEmployeeById(id);
    }

    @Override
    @Transactional
    public void addNewEmployee(Employee employee) {
        employeeDao.addNewEmployee(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployeeById(Long id, String departmentId, String jobTitle) {
        return employeeDao.updateEmployeeById(id, departmentId, jobTitle);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        employeeDao.deleteEmployeeById(id);
    }
}
