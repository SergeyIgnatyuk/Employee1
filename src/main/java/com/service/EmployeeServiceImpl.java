package com.service;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link EmployeeService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAllEmployees().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeDao.findEmployeeById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void addNewEmployee(Employee employee) {
        employeeDao.addNewEmployee(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Employee updateEmployeeById(Long id, int departmentId, String jobTitle) {
        return employeeDao.updateEmployeeById(id, departmentId, jobTitle);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void deleteEmployeeById(Long id) {
        employeeDao.deleteEmployeeById(id);
    }
}
