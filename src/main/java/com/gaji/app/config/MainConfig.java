package com.gaji.app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class MainConfig {

    @Bean
    @Primary
    public HikariDataSource hikariDataSource() {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("oracle.jdbc.OracleDriver")
                .url("jdbc:oracle:thin:@ec2-13-125-235-49.ap-northeast-2.compute.amazonaws.com:1521/xe")
                .username("gaji_dev")
                .password("gclass")
                .build();
        hikariDataSource.setConnectionTimeout(30000);
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMaxLifetime(1800000);
        return hikariDataSource;
    }
}

