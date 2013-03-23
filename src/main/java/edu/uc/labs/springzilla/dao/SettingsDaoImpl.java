package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.models.ClonezillaSettings;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDaoImpl extends AbstractDao<ClonezillaSettings> implements SettingsDao {

    @Override
    public boolean isSet(String settingName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    


}
