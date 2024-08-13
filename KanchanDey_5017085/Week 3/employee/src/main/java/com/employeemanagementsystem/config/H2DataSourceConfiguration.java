package com.employeemanagementsystem.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2DataSourceConfiguration {
    
    @ConfigurationProperties("spring.datasource.h2")
    @Bean
    public DataSourceProperties h2DataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean
    public DataSource h2DataSource(){
        return h2DataSourceProperties().initializeDataSourceBuilder().build();
    }
}
