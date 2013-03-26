package edu.uc.labs.springzilla.web.controllers;

import edu.uc.labs.springzilla.exceptions.MulticastConfigException;
import edu.uc.labs.springzilla.models.ClonezillaSettings;
import edu.uc.labs.springzilla.json.GeneralSettingsJson;
import edu.uc.labs.springzilla.models.MulticastSettings;
import edu.uc.labs.springzilla.services.ClonezillaService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    private static final Logger log = Logger.getLogger(IndexController.class);

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String index(Model model) {
        model.addAttribute("title", "Springzilla");
        return "main";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/settings/multicast")
    public @ResponseBody MulticastSettings getMulticastSettings(){
        return service.getMulticastSettings();
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/settings/multicast",headers={"content-type=application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void setMulticastSettings(@RequestBody MulticastSettings settings){
        service.saveMulticastSettings(settings);
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/settings/general")
    public @ResponseBody List<ClonezillaSettings> getGeneralSettings(){
        return service.getGeneralSettings();
    }
    
    @RequestMapping(method=RequestMethod.PUT, value="/settings/general",headers="Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    public void setGeneralSettings(@RequestBody GeneralSettingsJson generalSettings){
        ClonezillaSettings cs = new ClonezillaSettings();
        cs.setSettingName(GeneralSettingsJson.IMAGE_LOCATION);
        cs.setSettingValue(generalSettings.getImageLocation());        
        service.saveGeneralSettings(cs);        
    }
    
    @ExceptionHandler(MulticastConfigException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleMulticastException(){}
    
    
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
    
    @Autowired private MessageSource messageSource;
    @Autowired private ClonezillaService service;
}
