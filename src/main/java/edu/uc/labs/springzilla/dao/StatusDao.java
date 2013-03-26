package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.json.OcsManager;
import java.io.IOException;

public interface StatusDao {
    
    OcsManager getOcsStatus() throws IOException;
    boolean startOcs();
    boolean stopOcs();
    
}
