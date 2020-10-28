package com.service;

import com.dao.EmployeeDao;
import com.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        employeeService.findAllEmployees();

        Mockito.verify(employeeDao, Mockito.times(1)).findAllEmployees();
    }

    @Test
    public void findEmployeeByIdTest() {
        employeeService.findEmployeeById(2L);

        Mockito.verify(employeeDao, Mockito.times(1)).findEmployeeById(2L);
    }

    @Test
    public void addNewEmployeeTest() {
        Employee employee = new Employee();

        employeeService.addNewEmployee(employee);

        Mockito.verify(employeeDao, Mockito.times(1)).addNewEmployee(employee);
    }

    @Test
    public void updateEmployeeByIdTest() {
        employeeService.updateEmployeeById(2L, 1,"Java Developer");

        Mockito.verify(employeeDao, Mockito.times(1)).updateEmployeeById(2L, 1,"Java Developer");
    }

    @Test
    public void deleteEmployeeByIdTest() {
        employeeService.deleteEmployeeById(2L);

        Mockito.verify(employeeDao, Mockito.times(1)).deleteEmployeeById(2L);
    }
}
