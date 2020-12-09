package com.shanhaihen.mongo.base;

import com.shanhaihen.mongo.entity.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao extends MongoGenDao<PersonInfo> {
    private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    @Override
    protected Class getEntityClass() {
        return PersonInfo.class;
    }
}
