package com.shanhaihen.mongo.service;

import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.Person;

import java.util.List;

public interface IPersonService {

    void add(Person person);

    List<Person> query(Person person);

    Page<Person> queryPage(Person person);


}
