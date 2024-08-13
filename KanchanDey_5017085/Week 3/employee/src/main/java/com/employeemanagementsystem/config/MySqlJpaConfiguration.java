// package com.employeemanagementsystem.config;

// import javax.sql.DataSource;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.transaction.PlatformTransactionManager;

// @Configuration
// @EnableJpaRepositories(
//     basePackages = "com.employeemanagementsystem.departmentRepo, com.employeemanagementsystem.employeeRepo",
//     entityManagerFactoryRef = "mysqlEntityManagerFactoryBean",
//     transactionManagerRef = "mysqlTransactionManager"
// )
// public class MySqlJpaConfiguration {
    
//     @Bean
//     LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("mysqlDataSource") DataSource dataSource){
//         return entityManagerFactoryBuilder
//         .dataSource(dataSource)
//         .packages("com.employeemanagementsystem.departmentEntity, com.employeemanagementsystem.employeeEntity")
//         .build();
//     }

//     @Bean
//     PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean emfb){
//         return new JpaTransactionManager(emfb.getObject());
//     }
// }
