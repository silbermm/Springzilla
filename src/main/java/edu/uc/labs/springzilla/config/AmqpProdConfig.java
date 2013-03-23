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
@Profile("production")
@PropertySource("classpath:rabbit.properties")
public class AmqpProdConfig implements AmqpConfig {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(env.getProperty("rabbit.prod.host"));
        connectionFactory.setVirtualHost(env.getProperty("rabbit.prod.vhost"));
        connectionFactory.setUsername(env.getProperty("rabbit.prod.username"));
        connectionFactory.setPassword(env.getProperty("rabbit.prod.password"));
        return connectionFactory;
    }

    @Override
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Override
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
 
    @Override
    public Queue multicastQueue() {
        return new Queue(env.getProperty("rabbit.multicastQ"));
    }
    
     @Autowired
    private Environment env;
}
