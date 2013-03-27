package edu.uc.labs.springzilla.config;

import com.typesafe.config.Config;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("production")
public class DataProdConfig implements DataConfig{

    @Autowired private Config config;
       
    
    @Override
    @Bean(destroyMethod="close")
    public DataSource dataSource() {
        BasicDataSource bs = new BasicDataSource();
        bs.setDriverClassName(config.getString("jdbc.prod.driverClassName"));
        bs.setUrl(config.getString("jdbc.prod.url"));
        bs.setUsername(config.getString("jdbc.prod.username"));
        bs.setPassword(config.getString("jdbc.prod.password"));
        bs.setMaxWait(config.getLong("jdbc.prod.maxwait"));
        return bs;
    }

}
