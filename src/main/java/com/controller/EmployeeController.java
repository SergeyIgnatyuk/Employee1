package com.controller;

import com.model.Employee;
import com.service.EmployeeService;
import com.util.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeValidator validator;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeValidator validator) {
        this.employeeService = employeeService;
        this.validator = validator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findAllEmployees(ModelMap modelMap) {
        modelMap.addAttribute("employees", employeeService.findAllEmployees());
        return "employees";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findEmployeeById(@PathVariable Long id, ModelMap modelMap) {
        modelMap.addAttribute("employee", employeeService.findEmployeeById(id));
        return "employee";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addNewEmployee(ModelMap modelMap) {
        modelMap.addAttribute("employee", new Employee());
        return "addEmployee";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addNewEmployee(@ModelAttribute("employee") Employee employee, BindingResult result) {
        validator.validate(employee, result);
        if (result.hasErrors()) {
            return "addEmployee";
        }
        employeeService.addNewEmployee(employee);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/{id}/editEmployee", method = RequestMethod.GET)
    public String updateEmployeeById(@PathVariable Long id, ModelMap modelMap) {
        modelMap.addAttribute("employee", employeeService.findEmployeeById(id));
        return "editEmployee";
    }

    @RequestMapping(value = "/{id}/editEmployee", method = RequestMethod.POST)
    public String updateEmployeeById(@PathVariable Long id, @RequestParam int departmentId, @RequestParam String jobTitle, ModelMap modelMap) {
        employeeService.updateEmployeeById(id, departmentId, jobTitle);
        return "redirect:/employees/{id}";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}
