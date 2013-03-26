package edu.uc.labs.springzilla.test;

import edu.uc.labs.springzilla.config.AppConfig;
import edu.uc.labs.springzilla.config.DataDevConfig;
import edu.uc.labs.springzilla.config.PropertyPlaceholderConfig;
import edu.uc.labs.springzilla.dao.MulticastDao;
import edu.uc.labs.springzilla.dao.StatusDao;
import edu.uc.labs.springzilla.json.OcsManager;
import edu.uc.labs.springzilla.models.MulticastSettings;
import java.io.IOException;
import java.util.logging.Level;
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
@ContextConfiguration(classes = {PropertyPlaceholderConfig.class, AppConfig.class, DataDevConfig.class})
@ActiveProfiles("dev")
public class TestDaos {

    @Autowired private MulticastDao multicastDao;
    @Autowired private StatusDao statusDao;
    
    
    private static final Logger log = Logger.getLogger(TestDaos.class);

    @Test
    public void testMulticastDaoSet() {
        final String PORT = "2232";
        final String RDV = "239.0.0.1";
        final String SENDER = "239.23.23.247";
        final String TTL = "16";


        try {
            MulticastSettings ms = new MulticastSettings();
            ms.setMulticastPort(PORT);
            ms.setMulticastTTL(TTL);
            ms.setRdvAddress(RDV);
            ms.setSenderAddress(SENDER);

            multicastDao.updateSettings(ms);
            MulticastSettings s = multicastDao.getSettings();

            Assert.assertEquals(PORT, s.getMulticastPort());
            Assert.assertEquals(RDV, s.getRdvAddress());
            if (SENDER.equals("")) {
                Assert.assertNull(s.getSenderAddress());
            } else {
                Assert.assertEquals(SENDER, s.getSenderAddress());
            }
            Assert.assertEquals(TTL, s.getMulticastTTL());

        } catch (IOException ex) {
            log.error(ex.getMessage());
            Assert.fail();
        }
    }
    
    @Test
    public void testStatusDao() {
        try {
            OcsManager m = statusDao.getOcsStatus();
            Assert.assertEquals("started", m.getStatus());
            Assert.assertNotNull(m.getElapsedTime());
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
    }
}
