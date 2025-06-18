package com.example.corso.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.corso.repository.authRepository",
        entityManagerFactoryRef = "authEntityManager",
        transactionManagerRef = "authTransactionManager"
)
public class AuthDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.auth.datasource")
    public DataSource authDatasSource () {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean authEntityManager(EntityManagerFactoryBuilder builder){
        return builder
                .dataSource(authDatasSource())
                .packages("com.example.corso.entitySecurity")
                .persistenceUnit("entityUsers")
                .properties(Map.of(
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.show_sql", "true",
                        "hibernate.format_sql", "true"
                ))
                .build();
    }

    @Bean
    public PlatformTransactionManager authTransactionManager(
            @Qualifier("authEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
