package com.spring.data.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

/**
 * Created by Ghazi Naceur on 28/06/2017.
 */
@Configuration // tells spring that this class contains beans that should be instantiated by spring container
public class Config {

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/sandbox");
        return new SimpleMongoDbFactory(new MongoClient(uri),"sandbox");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }


}
