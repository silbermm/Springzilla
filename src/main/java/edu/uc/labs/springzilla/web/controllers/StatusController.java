package edu.uc.labs.springzilla.web.controllers;

import edu.uc.labs.springzilla.json.OcsManager;
import edu.uc.labs.springzilla.services.ClonezillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/status")
public class StatusController {
    
    @RequestMapping(value = "/ocs", method=RequestMethod.GET)
    public @ResponseBody OcsManager getOcsStatus(){
        return null; 
    }
    
    @Autowired ClonezillaService service;

}
