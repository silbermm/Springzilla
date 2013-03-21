package edu.uc.labs.springzilla.test;

import edu.uc.labs.springzilla.config.AmqpDevConfig;
import edu.uc.labs.springzilla.config.AppConfig;
import edu.uc.labs.springzilla.config.DataDevConfig;
import edu.uc.labs.springzilla.config.PropertyPlaceholderConfig;
import edu.uc.labs.springzilla.services.ClonezillaService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {PropertyPlaceholderConfig.class, AppConfig.class, DataDevConfig.class,AmqpDevConfig.class})
@ActiveProfiles("dev")
public class TestMessaging {
 
    @Autowired
    ClonezillaService clonezillaService;

    @Autowired
    AmqpAdmin amqpAdmin;
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Autowired
    Environment env;
    
    @Test
    public void testClonezillaService(){
       MessageProperties mp = new MessageProperties();
       mp.setCorrelationId(java.util.UUID.randomUUID().toString().getBytes());      
       mp.setReplyTo(env.getProperty("rabbit.multicastQ.reply"));
       
       Message m = new Message("Test".getBytes(), mp);
       
       Message rec = rabbitTemplate.sendAndReceive(env.getProperty("rabbit.multicastQ"), m);
       
       Assert.assertTrue(rec.getBody().equals("True"));     
    }
    
    
}
