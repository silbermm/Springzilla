package edu.uc.labs.springzilla.config;

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
        connectionFactory.setHost(env.getProperty("rabbit.dev.host"));
        connectionFactory.setVirtualHost(env.getProperty("rabbit.dev.vhost"));
        connectionFactory.setUsername(env.getProperty("rabbit.dev.username"));
        connectionFactory.setPassword(env.getProperty("rabbit.dev.password"));
        return connectionFactory;
    }

    @Override
    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin ra = new RabbitAdmin(connectionFactory());
        ra.declareQueue(multicastQueue());
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
        return new Queue(env.getProperty("rabbit.multicastQ"));
    }
    
    
    @Autowired
    private Environment env;
}
