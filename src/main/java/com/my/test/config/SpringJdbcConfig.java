package com.my.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.my.test")
public class SpringJdbcConfig {

    @Value("${spring.datasource.url}")
    private String dsUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String dsDriver;

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dsDriver);
        dataSource.setUrl(dsUrl);

        return dataSource;
    }
}
