package com.stb.mybooking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost");
    }
    
    @Bean
    public MongoRepositoryFactory mongoRepositoryFactory() throws Exception {
        return new MongoRepositoryFactory(mongoTemplate());
    }
}
