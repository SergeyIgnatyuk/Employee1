package com.dao.jdbc;

import com.controller.EmployeeController;
import com.dao.EmployeeDao;
import com.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Integrations tests of {@link JdbcEmployeeDaoImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcEmployeeDaoImplTest.JdbcEmployeeDaoImplTestConfig.class})
@Transactional
public class JdbcEmployeeDaoImplTest {
    @Autowired
    private EmployeeDao employeeDao;

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackages = "com.dao.jdbc")
    public static class JdbcEmployeeDaoImplTestConfig {
        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.H2).addScript("classpath:h2.sql").build();
        }

        @Bean
        public TransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }
    }

    @Test
    @Rollback(value = true)
    public void findAllEmployeesTest() {
        List<Employee> employees = employeeDao.findAllEmployees();

        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("Sergey", employees.get(0).getFirstName());
        Assert.assertEquals("Natasha", employees.get(1).getFirstName());
    }

    @Test
    @Rollback(value = true)
    public void findEmployeeByIdTest() {
        Employee employee = employeeDao.findEmployeeById(1L);

        Assert.assertEquals(1, employee.getId().intValue());
        Assert.assertEquals("Sergey", employee.getFirstName());
        Assert.assertEquals("Sergeev", employee.getLastName());
        Assert.assertEquals(1, employee.getDepartmentId());
        Assert.assertEquals("Java Developer", employee.getJobTitle());
        Assert.assertEquals("male", employee.getGender());
        Assert.assertEquals(new GregorianCalendar(1989, Calendar.AUGUST, 07).getTime(), employee.getDateOfBirth());
    }

    @Test
    @Rollback(value = true)
    public void addNewEmployeeTest() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Petya");
        testEmployee.setLastName("Petrov");
        testEmployee.setDepartmentId(1);
        testEmployee.setJobTitle("JS Developer");
        testEmployee.setGender("male");
        testEmployee.setDateOfBirth(new GregorianCalendar(1990, Calendar.OCTOBER, 28).getTime());

        employeeDao.addNewEmployee(testEmployee);
        List<Employee> employees = employeeDao.findAllEmployees();

        Assert.assertEquals(3, employees.size());

        Assert.assertEquals(3, employees.get(2).getId().intValue());
        Assert.assertEquals("Petya", employees.get(2).getFirstName());
        Assert.assertEquals("Petrov", employees.get(2).getLastName());
        Assert.assertEquals(1, employees.get(2).getDepartmentId());
        Assert.assertEquals("JS Developer", employees.get(2).getJobTitle());
        Assert.assertEquals("male", employees.get(2).getGender());
        Assert.assertEquals(new GregorianCalendar(1990, Calendar.OCTOBER, 28).getTime(), employees.get(2).getDateOfBirth());
    }

    @Test
    @Rollback(value = true)
    public void updateEmployeeByIdTest() {
        Employee employee = employeeDao.findEmployeeById(2L);
        employeeDao.updateEmployeeById(2L, 1, "Java Developer");
        employee = employeeDao.findEmployeeById(2L);

        Assert.assertEquals(1, employee.getDepartmentId());
        Assert.assertEquals("Java Developer", employee.getJobTitle());
    }

    @Test
    @Rollback(value = true)
    public void deleteEmployeeByIdTest() {
        employeeDao.deleteEmployeeById(2L);
        List<Employee> employees = employeeDao.findAllEmployees();

        Assert.assertEquals(1, employees.size());
        Assert.assertNotEquals(2, employees.get(0).getId().intValue());
    }
}
