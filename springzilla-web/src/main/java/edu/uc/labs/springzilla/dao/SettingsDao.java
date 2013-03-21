package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.models.ClonezillaSettings;


public interface SettingsDao extends Dao<ClonezillaSettings> {
    
    boolean isSet(String settingName);
    
    
}
