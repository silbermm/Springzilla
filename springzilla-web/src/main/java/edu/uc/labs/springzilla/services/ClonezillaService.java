package edu.uc.labs.springzilla.services;

import edu.uc.labs.springzilla.dao.SettingsDao;
import edu.uc.labs.springzilla.models.ClonezillaSettings;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClonezillaService {
    
    public void saveSettings(ClonezillaSettings settings) {
        settingsDao.update(settings);
        // also need to send a message to rabbitqueue to update 
        // files on the filesystem and wait to hear back
        Message m = new Message("message".getBytes(), new MessageProperties());
        rabbitTemplate.sendAndReceive(env.getProperty("rabbit.multicastQ"), "", m);
    }
    
    
    
    @Autowired
    private SettingsDao settingsDao;
    
    @Autowired
    private AmqpAdmin rabbitAdmin;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private Environment env;
    
}
