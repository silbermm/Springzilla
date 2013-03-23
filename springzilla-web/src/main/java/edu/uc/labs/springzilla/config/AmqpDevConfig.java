package edu.uc.labs.springzilla.config;

import com.typesafe.config.Config;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Profile("dev")
@PropertySource("classpath:rabbit.properties")
public class AmqpDevConfig implements AmqpConfig {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(config.getString("rabbit.dev.host"));
        connectionFactory.setVirtualHost(config.getString("rabbit.dev.vhost"));
        connectionFactory.setUsername(config.getString("rabbit.dev.username"));
        connectionFactory.setPassword(config.getString("rabbit.dev.password"));
        return connectionFactory;
    }

    @Override
    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin ra = new RabbitAdmin(connectionFactory());
        ra.declareQueue(multicastQueue());
        ra.declareQueue(multicastReplyQueue());
        return ra;
    }
    
    @Override
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rt = new RabbitTemplate(connectionFactory());
        //rt.setMessageConverter(new JsonMessageConverter());
        return rt;
    }
    
    @Override
    @Bean
    public Queue multicastQueue() {
        return new Queue(config.getString("rabbit.queue.multicast"));
    }
    
    public Queue multicastReplyQueue() {
        return new Queue(config.getString("rabbit.queue.multicastreply"));
    }
    
    @Autowired private Config config;
    @Autowired private Environment env;
}
