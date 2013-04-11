package edu.uc.labs.springzilla.web.controllers;

import edu.uc.labs.springzilla.exceptions.*;
import edu.uc.labs.springzilla.services.ClonezillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import edu.uc.labs.springzilla.json.*;

@Controller
@RequestMapping("/clone")
public class CloningController
{

	@RequestMapping(value = "getImages", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String[] getImageNames(){
		return service.getImages();
	}

	@RequestMapping(value = "restore", method = RequestMethod.POST) 
	@ResponseStatus(HttpStatus.OK)
  public @ResponseBody String startCloning(@RequestBody CloneJson data){
		try {	
			service.startRestoreSession(data);
		} catch (SessionException e){
			return e.getMessage();
		}
		return "OK"; 		
  }

	//@ExceptionHandler(SessionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleSessionException(){
	}

	@ExceptionHandler(ImageListingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleImageListingException(){
	}

	@Autowired ClonezillaService service;
}
