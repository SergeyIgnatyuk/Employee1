package com.controller;

import com.model.User;
import com.service.SecurityService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;

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
    public String registration(ModelMap modelMap, @ModelAttribute("userForm") User userForm) {
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/";
    }
}
