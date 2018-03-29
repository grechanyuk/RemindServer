package com.developer.eg.remind.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.developer.eg.remind.server.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("com.developer.eg.remind.server")
public class DatabaseConfig {

    @Resource
    private Environment env; //Возможно класс Environment уже содержит данные из properties, объявлять его не нужно

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); //То, что отображает таблицы БД в виде классов. Предоставляет возможность автоматически создавать бины
        em.setDataSource(dataSource());
        em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() { //Этот метод должен носить именно такое название
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory().getObject());

        return tm;
    }

    @Bean
    public DataSource dataSource () {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(env.getRequiredProperty("db.url"));
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));

        ds.setInitialSize(Integer.parseInt(env.getRequiredProperty("db.initialSize")));
        ds.setMinIdle(Integer.parseInt(env.getRequiredProperty("db.minIdle")));
        ds.setMaxIdle(Integer.parseInt(env.getRequiredProperty("db.maxIdle")));
        ds.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        ds.setMinEvictableIdleTimeMillis(Long.parseLong(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        ds.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("db.testOnBorrow")));
        ds.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return ds;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties"); //Подгрузка всего файла целиком, чтобы не как в dataSource
        try {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find hibernate.properties");
        }
    }
}
