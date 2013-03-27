package edu.uc.labs.springzilla.config;

import com.typesafe.config.Config;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "dev")
public class DataDevConfig implements DataConfig {

    @Autowired private Config config;
    
    @Bean(destroyMethod="close")
    @Override
    public DataSource dataSource() {
        BasicDataSource bs = new BasicDataSource();
        bs.setDriverClassName(config.getString("jdbc.dev.driverClassName"));
        bs.setUrl(config.getString("jdbc.dev.url"));
        bs.setUsername(config.getString("jdbc.dev.username"));
        bs.setPassword(config.getString("jdbc.dev.password"));
        bs.setMaxWait(config.getLong("jdbc.dev.maxwait"));
        return bs;
    }
    
    
}
