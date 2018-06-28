package com.ort.risk.ws.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ComponentScan(value = {"com.ort.risk.ws.repository"})
public class ContextConfig {

    @Bean
    public MongoTemplate getMongoTemplate() {
        return  new MongoTemplate(new MongoClient("mongodb://localhost:27017"), "risk-db");
    }

}
