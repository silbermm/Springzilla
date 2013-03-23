package edu.uc.labs.springzilla.services;

import edu.uc.labs.springzilla.dao.MulticastDao;
import edu.uc.labs.springzilla.dao.SettingsDao;
import edu.uc.labs.springzilla.models.ClonezillaSettings;
import edu.uc.labs.springzilla.models.MulticastSettings;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClonezillaService {
    
    public void saveSettings(ClonezillaSettings settings) {
        settingsDao.update(settings);    
    }
    
    public void saveMulticastSettings(MulticastSettings settings) {
       try{
        multicastDao.updateSettings(settings);
       }catch (IOException e){
       }
    }
    
    public MulticastSettings getMulticastSettings(){
        try {
            return multicastDao.getSettings();
        }catch (IOException e){
            return null;
        }
    }
    
    @Autowired private SettingsDao settingsDao;
    @Autowired private MulticastDao multicastDao;
    
    
}
