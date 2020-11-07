package com.dao.hibernate;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link EmployeeDao} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Repository
public class HibernateEmployeeDaoImpl implements EmployeeDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateEmployeeDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Employee> findAllEmployees() {
        Query query = currentSession().createQuery("FROM Employee e");
        return query.getResultList();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return currentSession().get(Employee.class, id);
    }

    @Override
    public void addNewEmployee(Employee employee) {
        currentSession().save(employee);
    }

    @Override
    public Employee updateEmployeeById(Long id, int departmentId, String jobTitle) {
        Session session = currentSession();
        Employee employee = session.get(Employee.class, id);
        employee.setDepartmentId(departmentId);
        employee.setJobTitle(jobTitle);
        session.update(employee);
        return employee;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Session session = currentSession();
        Employee employee = session.get(Employee.class, id);
        session.delete(employee);
    }
}
