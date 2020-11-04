package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for welcome & contacts pages.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap modelMap) {
        return "welcome";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts(ModelMap modelMap) {
        return "contacts";
    }
}
