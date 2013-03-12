package edu.uc.labs.springzilla.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(value = "/")
public class IndexController {
    
    private static final Logger log = Logger.getLogger(IndexController.class);
    
    @RequestMapping(method = RequestMethod.GET, value="")
    public String index(){
        return "index";
    }
    
    
    @Autowired
    private MessageSource messageSource;
}
