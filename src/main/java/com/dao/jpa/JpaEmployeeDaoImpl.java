package com.dao.jpa;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of {@link EmployeeDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class JpaEmployeeDaoImpl implements EmployeeDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findAllEmployees() {
        Query query = entityManager.createQuery("FROM Employee");
        return query.getResultList();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void addNewEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public Employee updateEmployeeById(Long id, int departmentId, String jobTitle) {
        Employee employee = findEmployeeById(id);
        employee.setDepartmentId(departmentId);
        employee.setJobTitle(jobTitle);
        return entityManager.merge(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = findEmployeeById(id);
        entityManager.remove(employee);
    }
}
