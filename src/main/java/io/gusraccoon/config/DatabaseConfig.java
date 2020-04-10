package io.gusraccoon.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatabaseConfig {

    @Autowired
    private DatabaseProperty databaseProperty;

    public DataSource createDataSource(String url) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        
        System.out.println("url : "+url);
        dataSource.setUrl(url);
        dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
        dataSource.setUsername(databaseProperty.getUsername());
        dataSource.setPassword(databaseProperty.getPassword());

        return dataSource;
    }
    
    @Bean
    public DataSource routingDataSource() {
        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

            DataSource master = createDataSource(databaseProperty.getUrl());
            
            System.out.println("master dataSoruce : "+master);

            Map<Object, Object> dataSourceMap = new LinkedHashMap<>();
            dataSourceMap.put("master", master);

            databaseProperty.getSlaveList().forEach(slave -> {
                dataSourceMap.put(slave.getSlavename(), createDataSource(slave.getSlaveurl()));
            });

            replicationRoutingDataSource.setTargetDataSources(dataSourceMap);
            replicationRoutingDataSource.setDefaultTargetDataSource(master);
            
            System.out.println("replicationRoutingDataSource : "+replicationRoutingDataSource);
            return replicationRoutingDataSource;
        }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("io.gusraccoon");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        return entityManagerFactoryBean;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}