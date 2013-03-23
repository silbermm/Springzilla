package edu.uc.labs.springzilla.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Profile(value = "dev")
@PropertySource({"classpath:devDataSource.properties"})
public class DataDevConfig implements DataConfig {

    @Autowired
    private Environment env;
    
    @Value( "${jdbc.dev.url}" )
    private String jdbcUrl;
    
    @Bean(destroyMethod="close")
    @Override
    public DataSource dataSource() {
        BasicDataSource bs = new BasicDataSource();
        bs.setDriverClassName(env.getProperty("jdbc.dev.driverClassName"));
        bs.setUrl(jdbcUrl);
        bs.setUsername(env.getProperty("jdbc.dev.username"));
        bs.setPassword(env.getProperty("jdbc.dev.password"));
        bs.setMaxWait(Long.parseLong(env.getProperty("jdbc.dev.maxwait")));
        return bs;
    }
    
    
}
