package com.util;

import com.model.Employee;
import com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * Validator for {@link com.model.Employee} class,
 * implements {@link Validator} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Component
public class EmployeeValidator implements Validator {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeValidator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;

        String firstName = employee.getFirstName();
        if (firstName.isEmpty()) {
            errors.rejectValue("firstName", "empty");
        } else {
            if (firstName.contains(" ")) {
                errors.rejectValue("firstName", "whitespaces");
            } else {
                if (firstName.length() < 3 || firstName.length() > 15) {
                    errors.rejectValue("firstName", "size");
                }
            }
        }

        String lastName = employee.getLastName();
        if (lastName.isEmpty()) {
            errors.rejectValue("lastName", "empty");
        } else {
            if (lastName.contains(" ")) {
                errors.rejectValue("lastName", "whitespaces");
            } else {
                if (lastName.length() < 3 || lastName.length() > 15) {
                    errors.rejectValue("lastName", "size");
                }
            }
        }

        int departmentId = employee.getDepartmentId();
        if (departmentId < 1 || departmentId > 2) {
            errors.rejectValue("departmentId", "departmentId");
        }

        String jobTitle = employee.getJobTitle();
        if (jobTitle.isEmpty()) {
            errors.rejectValue("jobTitle", "empty");
        } else {
            if (jobTitle.length() < 2 || jobTitle.length() > 15) {
                errors.rejectValue("jobTitle", "size");
            }
        }

        String gender = employee.getGender();
        if (gender.isEmpty()) {
            errors.rejectValue("gender", "empty");
        } else {
            if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
                errors.rejectValue("gender", "gender");
            }
        }

        Date dateOfBirth = employee.getDateOfBirth();
        if (dateOfBirth == null) {
            errors.rejectValue("dateOfBirth", "empty");
        }
    }
}
