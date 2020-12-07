package com.controller;

import com.model.Employee;
import com.service.EmployeeService;
import com.util.EmployeeValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests of {@link EmployeeController}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {EmployeeControllerTest.EmployeeControllerTestConfig.class})
public class EmployeeControllerTest {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Configuration
    public static class EmployeeControllerTestConfig {
        @Bean
        public EmployeeService employeeService() {
            return Mockito.mock(EmployeeService.class);
        }

        @Bean
        public Validator validator() {
            return new EmployeeValidator(employeeService());
        }

        @Bean
        public EmployeeController employeeController() {
            return new EmployeeController(employeeService(), validator());
        }

        @Bean
        public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
            viewResolver.setViewClass(JstlView.class);
            viewResolver.setPrefix("/WEB-INF/views/pages/");
            viewResolver.setSuffix(".jsp");
            return viewResolver;
        }
    }

    @Test
    public void showAllEmployeesTest() throws Exception {
        List<Employee> employees = new ArrayList<>();

        when(employeeService.findAllEmployees()).thenReturn(employees);

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("employees", employeeService.findAllEmployees());
        ResultMatcher view = MockMvcResultMatchers.view().name("employees");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }

    @Test
    public void showEmployeeByIdTest() throws Exception {
        Employee employee = new Employee();

        when(employeeService.findEmployeeById(1L)).thenReturn(employee);

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("employee", employeeService.findEmployeeById(1L));
        ResultMatcher view = MockMvcResultMatchers.view().name("employee");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }

    @Test
    public void showAddPageTest() throws Exception {
        Employee employee = new Employee();

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("newEmployee", employee);
        ResultMatcher view = MockMvcResultMatchers.view().name("addEmployee");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employees/add"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }

    @Test
    public void addEmployee_whenBindingResultHasErrors() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petya");
        employee.setLastName("Petrov");
        employee.setDepartmentId(3); //error
        employee.setJobTitle("QA");
        employee.setGender("male");
        employee.setDateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", employee.getFirstName());
        map.add("lastName", employee.getLastName());
        map.add("departmentId", String.valueOf(employee.getDepartmentId()));
        map.add("jobTitle", employee.getJobTitle());
        map.add("gender", employee.getGender());
        map.add("dateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(employee.getDateOfBirth()));

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("addEmployee");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees/add").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(employeeService, never()).addNewEmployee(employee);
    }

    @Test
    public void addEmployee_whenBindingResultHasNotErrors() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Petya");
        employee.setLastName("Petrov");
        employee.setDepartmentId(2);
        employee.setJobTitle("QA");
        employee.setGender("male");
        employee.setDateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", employee.getFirstName());
        map.add("lastName", employee.getLastName());
        map.add("departmentId", String.valueOf(employee.getDepartmentId()));
        map.add("jobTitle", employee.getJobTitle());
        map.add("gender", employee.getGender());
        map.add("dateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(employee.getDateOfBirth()));

        ResultMatcher status = MockMvcResultMatchers.status().isFound();
        ResultMatcher view = MockMvcResultMatchers.view().name("redirect:/employees");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees/add").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(employeeService, times(1)).addNewEmployee(employee);
    }

    @Test
    public void showEditPageTest() throws Exception {
        Employee employee = new Employee();

        when(employeeService.findEmployeeById(1L)).thenReturn(employee);

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher model = MockMvcResultMatchers.model().attribute("employee", employeeService.findEmployeeById(1L));
        ResultMatcher view = MockMvcResultMatchers.view().name("editEmployee");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/employees/1/edit"))
                .andExpect(status)
                .andExpect(model)
                .andExpect(view);
    }

    @Test
    public void updateEmployee_whenBindingResultHasErrors() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Petya");
        employee.setLastName("Petrov");
        employee.setDepartmentId(3); //error
        employee.setJobTitle("QA");
        employee.setGender("male");
        employee.setDateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", employee.getFirstName());
        map.add("lastName", employee.getLastName());
        map.add("departmentId", String.valueOf(employee.getDepartmentId()));
        map.add("jobTitle", employee.getJobTitle());
        map.add("gender", employee.getGender());
        map.add("dateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(employee.getDateOfBirth()));

        ResultMatcher status = MockMvcResultMatchers.status().isOk();
        ResultMatcher view = MockMvcResultMatchers.view().name("editEmployee");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees/1/edit").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(employeeService, never()).updateEmployeeById(1L, 3, "QA");
    }

    @Test
    public void editEmployee_whenBindingResultHasNotErrors() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Petya");
        employee.setLastName("Petrov");
        employee.setDepartmentId(2);
        employee.setJobTitle("QA");
        employee.setGender("male");
        employee.setDateOfBirth(new GregorianCalendar(1989, Calendar.AUGUST, 7).getTime());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", employee.getFirstName());
        map.add("lastName", employee.getLastName());
        map.add("departmentId", String.valueOf(employee.getDepartmentId()));
        map.add("jobTitle", employee.getJobTitle());
        map.add("gender", employee.getGender());
        map.add("dateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(employee.getDateOfBirth()));

        ResultMatcher status = MockMvcResultMatchers.status().isFound();
        ResultMatcher view = MockMvcResultMatchers.view().name("redirect:/employees/{id}");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees/1/edit").params(map))
                .andExpect(status)
                .andExpect(view);

        verify(employeeService, times(1)).updateEmployeeById(1L, 2, "QA");
    }

    @Test
    public void deleteEmployeeByIdTest() throws Exception {
        ResultMatcher status = MockMvcResultMatchers.status().isFound();
        ResultMatcher view = MockMvcResultMatchers.view().name("redirect:/employees");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/employees/1/delete"))
                .andExpect(status)
                .andExpect(view);

        verify(employeeService, times(1)).deleteEmployeeById(1L);
    }
}