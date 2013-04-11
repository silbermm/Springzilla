package edu.uc.labs.springzilla.config;

import com.typesafe.config.Config;
import edu.uc.labs.springzilla.dao.*;
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
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource(value = { "/WEB-INF/applicationContext-security.xml" } )
@Import(PropertyPlaceholderConfig.class)
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
        p.setProperty("hibernate.dialect", config.getString("hibernate.dialect"));
        p.setProperty("hibernate.show_sql", config.getString("hibernate.show_sql"));
        p.setProperty("hibernate.format_sql", config.getString("hibernate.format_sql"));
        p.setProperty("hibernate.hbm2ddl.auto", config.getString("hibernate.hbm2ddl.auto"));
        return p;
    }
    
    @Bean
    public ClonezillaService clonezillaService() {
        return new ClonezillaService();
    }
    
    @Bean
    public SettingsDao settingsDao(){
        SettingsDao sd = new SettingsDaoImpl();
        sd.setSessionFactory(sessionFactory());
        return sd;
    }
    
    @Bean
    public MulticastDao multicastDao(){
        return new MulticastDaoImpl(config);
    }
    
    @Bean
    public StatusDao statusDao(){
        return new StatusDaoImpl();
    }

    @Bean
    public ImageDao imageDao(){
      return new ImageDaoImpl();
    }

		@Bean
		public DrblDao drblDao(){
			return new DrblDao();
		}
    
    @Autowired private DataSource dataSource;
    @Autowired private Config config;
}
