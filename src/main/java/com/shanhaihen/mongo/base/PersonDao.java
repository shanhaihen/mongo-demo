package com.shanhaihen.mongo.base;

import com.shanhaihen.mongo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao extends MongoGenDao<Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    @Override
    protected Class getEntityClass() {
        return Person.class;
    }
}
