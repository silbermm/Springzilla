package edu.uc.labs.springzilla.test;

import edu.uc.labs.springzilla.config.AmqpDevConfig;
import edu.uc.labs.springzilla.config.AppConfig;
import edu.uc.labs.springzilla.config.DataDevConfig;
import edu.uc.labs.springzilla.config.PropertyPlaceholderConfig;
import edu.uc.labs.springzilla.dao.MulticastDao;
import edu.uc.labs.springzilla.models.MulticastSettings;
import java.io.IOException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PropertyPlaceholderConfig.class, AppConfig.class, DataDevConfig.class, AmqpDevConfig.class})
@ActiveProfiles("dev")
public class TestDaos {

    @Autowired
    private MulticastDao multicastDao;
    
    private static final Logger log = Logger.getLogger(TestDaos.class);

    @Test
    public void testMulticastDaoSet() {
        final String PORT   = "1111";
        final String RDV    = "244.0.0.1";
        final String SENDER = "";
        final String TTL    = "1";
        
        
        try {
            MulticastSettings ms = new MulticastSettings();
            ms.setMulticastPort(PORT);
            ms.setMulticastTTL(TTL);
            ms.setRdvAddress(RDV);
            ms.setSenderAddress(SENDER);
            
            multicastDao.updateSettings(ms);
            MulticastSettings s = multicastDao.getSettings();
                        
            Assert.assertEquals(PORT, s.getMulticastPort());
            //Assert.assertEquals(RDV, s.getRdvAddress());
            //Assert.assertNull(s.getSenderAddress());
            //Assert.assertEquals(TTL, s.getMulticastTTL());
            
        } catch (IOException ex) {
            log.error(ex.getMessage());
            Assert.fail();
        }
        
        
    }
}
