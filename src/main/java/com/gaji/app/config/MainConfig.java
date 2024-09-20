package com.gaji.app.config;

import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.repository.KeywordRepository;
import com.gaji.app.keyword.service.AlertObserver;
import com.gaji.app.keyword.service.AlertSubject;
import com.gaji.app.keyword.service.KeywordObserver;
import com.gaji.app.keyword.service.KeywordService;
import com.gaji.app.product.domain.Product;
import com.zaxxer.hikari.HikariDataSource;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

    @Bean
    public Map<String, AlertSubject<Product>> keywordObserver(KeywordRepository keywordRepository, KeywordRegisterRepository keywordRegisterRepository, KeywordAlertRepository keywordAlertRepository) {
        Map<String, AlertSubject<Product>> observers = new HashMap<>();

        List<KeywordRegister> keywords = keywordRegisterRepository.findAll();

        for(KeywordRegister keyword : keywords) {
            if(!observers.containsKey(keyword.getWord())) {
                observers.put(keyword.getWord(), new KeywordService(new HashSet<>()));
            }

            observers.get(keyword.getWord()).attach(new KeywordObserver(keyword, keywordAlertRepository));
        }

        return observers;
    }

}

