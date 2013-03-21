package edu.uc.labs.springzilla.web.controllers;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    private static final Logger log = Logger.getLogger(IndexController.class);

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String index(Model model) {
        model.addAttribute("title", "Springzilla");
        return "main";
    }

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = "error";
        if (throwable != null) {
            errorMessage = throwable.getLocalizedMessage();
        }
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
    
    @RequestMapping("/404")
    public String notFound(Model model) {
        return "404";
    }
    
    @Autowired
    private MessageSource messageSource;
}
