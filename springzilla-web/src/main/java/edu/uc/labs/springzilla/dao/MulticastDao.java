package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.models.MulticastSettings;
import java.io.IOException;

public interface MulticastDao {
    
    void updateSettings(MulticastSettings settings) throws IOException;
    MulticastSettings getSettings() throws IOException;
    
}
