package edu.uc.labs.springzilla.config;

import edu.uc.labs.springzilla.dao.SettingsDao;
import edu.uc.labs.springzilla.dao.SettingsDaoImpl;
import edu.uc.labs.springzilla.services.ClonezillaService;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource(value = { "/WEB-INF/applicationContext-security.xml" } )
@Import(PropertyPlaceholderConfig.class)
@PropertySource("classpath:hibernate.properties")
public class AppConfig {

    private static final Logger log = Logger.getLogger(AppConfig.class);
  
    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sf = new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("edu.uc.labs.springzilla.models");
        sf.addProperties(getHibernateProperties());
        return sf.buildSessionFactory();
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager tm = new HibernateTransactionManager();
        tm.setSessionFactory(sessionFactory());
        return tm;
    }

    @Bean
    public Properties getHibernateProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        p.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        p.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        p.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return p;
    }
    
    @Bean
    public ClonezillaService clonezillaService() {
        return new ClonezillaService();
    }
    
    @Bean
    public SettingsDao settingsDao(){
        return new SettingsDaoImpl();
    }
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private Environment env;
}
