package com.employeemanagementsystem.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MySqlDataSourceConfiguration {
    
    @ConfigurationProperties("spring.datasource.mysql")
    @Bean
    DataSourceProperties mysqlDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Primary
    @Bean
    DataSource mysqlDataSource(){
        return mysqlDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
