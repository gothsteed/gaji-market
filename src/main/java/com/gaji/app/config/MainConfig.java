package com.gaji.app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

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
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMinimumIdle(5);
        hikariDataSource.setIdleTimeout(300000); // 5 minutes
        hikariDataSource.setMaxLifetime(1800000); // 30 minutes
        hikariDataSource.setConnectionTimeout(30000); // 30 seconds

        hikariDataSource.setConnectionTestQuery("SELECT 1 FROM DUAL");

        // Add connection init SQL to set session parameters
        hikariDataSource.setConnectionInitSql("ALTER SESSION SET CURRENT_SCHEMA = gaji_dev");

        return hikariDataSource;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

