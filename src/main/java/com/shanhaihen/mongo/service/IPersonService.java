package com.shanhaihen.mongo.service;

import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.PersonInfo;

import java.util.List;

public interface IPersonService {

    void add(PersonInfo personInfo);

    List<PersonInfo> query(PersonInfo personInfo);

    Page<PersonInfo> queryPage(PersonInfo personInfo);


}
