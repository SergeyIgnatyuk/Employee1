package com.service;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests of {@link EmployeeServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmployeeServiceImplTest.EmployeeServiceImplTestConfig.class})
public class EmployeeServiceImplTest {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeeService employeeService;

    @Configuration
    public static class EmployeeServiceImplTestConfig {
        @Bean
        public EmployeeDao employeeDao() {
            return Mockito.mock(EmployeeDao.class);
        }

        @Bean
        EmployeeService employeeService() {
            return new EmployeeServiceImpl(employeeDao());
        }
    }

    @Test
    public void findAllEmployeesTest() {
        Employee employee1 = new Employee();
        employee1.setId(1L);

        Employee employee2 = new Employee();
        employee2.setId(2L);

        Employee employee3 = new Employee();
        employee3.setId(3L);

        List<Employee> employees = Stream.of(employee2, employee3, employee1).collect(Collectors.toList());

        when(employeeDao.findAllEmployees()).thenReturn(employees);

        employees = employeeService.findAllEmployees();

        Assert.assertEquals(1, employees.get(0).getId().intValue());
        Assert.assertEquals(2, employees.get(1).getId().intValue());
        Assert.assertEquals(3, employees.get(2).getId().intValue());

        verify(employeeDao, Mockito.times(1)).findAllEmployees();
    }

    @Test
    public void findEmployeeByIdTest() {
        employeeService.findEmployeeById(2L);

        verify(employeeDao, Mockito.times(1)).findEmployeeById(2L);
    }

    @Test
    public void addNewEmployeeTest() {
        Employee employee = new Employee();

        employeeService.addNewEmployee(employee);

        verify(employeeDao, Mockito.times(1)).addNewEmployee(employee);
    }

    @Test
    public void updateEmployeeByIdTest() {
        employeeService.updateEmployeeById(2L, 1,"Java Developer");

        verify(employeeDao, Mockito.times(1)).updateEmployeeById(2L, 1,"Java Developer");
    }

    @Test
    public void deleteEmployeeByIdTest() {
        employeeService.deleteEmployeeById(2L);

        verify(employeeDao, Mockito.times(1)).deleteEmployeeById(2L);
    }
}
