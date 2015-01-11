package MyPlanner.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Hibernate configuration class.
 * Uses persistence-mysql.properties for some of the properties.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("/WEB-INF/persistence-mysql.properties")
@ComponentScan(basePackages = "MyPlanner")
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[]{"MyPlanner"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource restDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    Properties hibernateProperties(){
        return new Properties(){
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }

}
