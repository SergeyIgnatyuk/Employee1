package com.controller;

import com.model.User;
import com.service.SecurityService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for {@link com.model.User}'s pages.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Controller
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final Validator validator;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, @Qualifier("userValidator") Validator validator) {
        this.userService = userService;
        this.securityService = securityService;
        this.validator = validator;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap modelMap) {
        modelMap.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult result) {
        validator.validate(userForm, result);
        if (result.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String findAll(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAll());
        return "users";
    }
}
