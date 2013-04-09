package edu.uc.labs.springzilla.web.controllers;

import edu.uc.labs.springzilla.exceptions.ImageListingException;
import edu.uc.labs.springzilla.services.ClonezillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/clone")
public class CloningController {
    
    @RequestMapping(value="getImages", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String[] getImageNames(){
        return service.getImages();
    }
    
    @ExceptionHandler(ImageListingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleImageListingException(){
        
    }
    
    @Autowired ClonezillaService service;
}
