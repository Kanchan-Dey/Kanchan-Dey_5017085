// package com.employeemanagementsystem.config;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
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
//     basePackages = "com.employeemanagementsystem.employeeRepo, com.employeemanagementsystem.departmentRepo",
//     entityManagerFactoryRef = "h2EntityManagerFactoryBean",
//     transactionManagerRef = "h2TransactionManager"
// )
// public class h2JpaConfiguration {
    
//     @Bean
//     LocalContainerEntityManagerFactoryBean h2EntityManagerFactoryBean(@Autowired EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("h2DataSource") DataSource dataSource){
//         return entityManagerFactoryBuilder
//         .dataSource(dataSource)
//         .packages("com.employeemanagementsystem.employeeEntity, com.employeemanagementsystem.departmentEntity")
//         .build();
//     }
//     @Bean
//     PlatformTransactionManager h2TransactionManager(@Qualifier("h2EntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean emfb){
//         return new JpaTransactionManager(emfb.getObject());
//     }
// }
