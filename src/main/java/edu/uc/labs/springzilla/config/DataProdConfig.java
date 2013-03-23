package edu.uc.labs.springzilla.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
@Profile("production")
@PropertySource({"classpath:devDataSource.properties"})
public class DataProdConfig implements DataConfig{

    @Autowired
    private Environment env;
    
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    
    @Override
    @Bean(destroyMethod="close")
    public DataSource dataSource() {
        BasicDataSource bs = new BasicDataSource();
        bs.setDriverClassName(env.getProperty("jdbc.prod.driverClassName"));
        bs.setUrl(env.getProperty("jdbc.prod.url"));
        bs.setUsername(env.getProperty("jdbc.prod.username"));
        bs.setPassword(env.getProperty("jdbc.prod.password"));
        bs.setMaxWait(Long.parseLong(env.getProperty("jdbc.prod.maxwait")));
        return bs;
    }

}
