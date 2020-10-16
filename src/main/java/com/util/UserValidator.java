package com.util;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        String username = user.getUsername();
        if (username.isEmpty()) {
            errors.rejectValue("username", "Required");
        } else if (username.contains(" ")) {
            errors.rejectValue("username", "Whitespaces");
        } else if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        } else if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        String password = user.getPassword();
        if (password.isEmpty()) {
            errors.rejectValue("password", "Required");
        } else if (password.contains(" ")) {
            errors.rejectValue("password", "Whitespaces");
        } else if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        String confirmPassword = user.getConfirmPassword();
        if (!confirmPassword.equals(password)) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
