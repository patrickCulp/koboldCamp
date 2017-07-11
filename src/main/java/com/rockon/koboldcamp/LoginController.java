/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rockon.koboldcamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author pculp
 */
@Controller
public class LoginController {
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String showLoginForm() {
//        return "login";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
	@RequestParam(value = "error", required = false) String error,
	@RequestParam(value = "logout", required = false) String logout) {

	ModelAndView model = new ModelAndView();
	if (error != null) {
	    // this will return an error message back to the .jsp file so you can display that somewhere. 
	    // We will get to that later.
		model.addObject("error", "Invalid username and password!");
	}

	return "home"; // this will redirect to home page after being logged in.
    }
}
