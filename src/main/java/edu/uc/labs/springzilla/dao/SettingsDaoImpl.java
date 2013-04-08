package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.models.ClonezillaSettings;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDaoImpl extends AbstractDao<ClonezillaSettings> implements SettingsDao {

    @Override
    public boolean isSet(String settingName) {
        ClonezillaSettings cs = (ClonezillaSettings) getSession().createQuery("from ClonezillaSettings where name=?").setParameter(0, settingName).uniqueResult();
        return (cs != null);
    }

    @Override
    public ClonezillaSettings getSettingByName(String settingName) {
        return (ClonezillaSettings) getSession().createQuery("from ClonezillaSettings where name=?").setParameter(0, settingName).uniqueResult();        
    }
        
}
