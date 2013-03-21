package edu.uc.labs.springzilla.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET, params="fail")
    public String loginError(Model model){
        model.addAttribute("error", true);
        model.addAttribute("errorMsgKey", "application.error.login");
        return "login";
    }
    
}
