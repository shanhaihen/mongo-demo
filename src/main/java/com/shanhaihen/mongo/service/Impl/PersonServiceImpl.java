package com.shanhaihen.mongo.service.Impl;

import com.shanhaihen.mongo.entity.PersonInfo;
import com.shanhaihen.mongo.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void add(PersonInfo personInfo) {

        mongoTemplate.insert(personInfo);

    }
}
