package com.shanhaihen.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public CustomConversions mongoCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new BsonUndefinedParseConverter());
        return new CustomConversions(converters);
    }
}