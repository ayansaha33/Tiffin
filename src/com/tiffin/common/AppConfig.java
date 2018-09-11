package com.tiffin.common;



import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tiffin.service.PropertyService;

 
@Configuration
@EnableTransactionManagement
@PropertySources({
    @PropertySource("/resources/properties/jdbc.properties"),
    @PropertySource("/resources/properties/tiffinApp.properties"),
    @PropertySource("/resources/properties/hibernate.properties")
})

public class AppConfig {
	
	private static final Logger logger = Logger.getLogger(AppConfig.class);

	@Autowired
	PropertyService propertyService;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		logger.info("Entering DataSource Config...");
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(propertyService.readValues("eai.driverClassName"));
		driverManagerDataSource.setUrl(propertyService.readValues("eai.url"));
		driverManagerDataSource.setUsername(propertyService.readValues("eai.username"));
		driverManagerDataSource.setPassword(propertyService.readValues("eai.password"));
		return driverManagerDataSource;
	}
	
	@Bean(name="sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
		logger.info("Entering LocalSessionFactoryBean for Spring-Hibernare Configuration...");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.tiffin" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
	
	
	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", propertyService.readValues("hibernate.dialect"));
        properties.put("hibernate.show_sql", propertyService.readValues("hibernate.show_sql"));
        properties.put("hibernate.format_sql", propertyService.readValues("hibernate.format_sql"));
        return properties;        
    }

	
	@Bean(name="transactionManager")
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
}