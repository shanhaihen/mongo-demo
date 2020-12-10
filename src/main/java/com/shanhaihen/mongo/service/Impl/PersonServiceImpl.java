package com.shanhaihen.mongo.service.Impl;

import com.shanhaihen.mongo.base.PersonDao;
import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.Person;
import com.shanhaihen.mongo.service.IPersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PersonDao personDao;


    @Override
    public void add(Person person) {

        mongoTemplate.insert(person);

    }

    @Override
    public List<Person> query(Person person) {
        Query query = createScPersonInfoQuery(person);
        return personDao.queryList(query);
    }

    @Override
    public Page<Person> queryPage(Person person) {
        Query query = createScPersonInfoQuery(person);
        Integer pageNo = person.getPageNo();
        Integer pageSize = person.getPageSize();
        int start = (pageNo - 1) * pageSize;
        Page<Person> page = new Page<>(pageNo, pageSize);
        List<Person> personList;
        Long totalSize = 0L;
        if (Objects.nonNull(query)) {
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            query.skip(start);
            query.limit(pageSize);
            query.with(sort);
            personList = personDao.queryList(query);
            totalSize = personDao.getPageCount(query);
        } else {
            personList = new ArrayList();
        }
        page.setRecords(personList);
        page.setTotal(totalSize.intValue());
        return page;
    }

    private Query createScPersonInfoQuery(Person person) {
        Query query = new Query();

        if (StringUtils.isNotBlank(person.getIdNumber()) && StringUtils.isNotBlank(person.getName())) {
            //mongo or搜索
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("idNumber").regex(".*?" + person.getIdNumber().replace("*", "\\*") + ".*"),
                    Criteria.where("name").regex(".*?" + person.getName().replace("*", "\\*") + ".*")));
        } else if (StringUtils.isNotBlank(person.getName())) {
            query.addCriteria(Criteria.where("name").regex(".*?" + person.getName().replace("*", "\\*") + ".*"));
        } else if (StringUtils.isNotBlank(person.getIdNumber())) {
            query.addCriteria(Criteria.where("idNumber").regex(".*?" + person.getIdNumber().replace("*", "\\*") + ".*"));
        }

        if (Objects.nonNull(person.getGender())) {
            query.addCriteria(Criteria.where("gender").is(person.getGender()));
        }

        //in 查询
        if (Objects.nonNull(person.getPhoneNums())) {
            query.addCriteria(Criteria.where("phoneNums").elemMatch(Criteria.where("$in").is(person.getPhoneNums())));
        }
        //子查询
        String province = person.getProvince();
        if (StringUtils.isNotBlank(province)) {
            query.addCriteria(Criteria.where("addresses.province").is(province));
        }
        String city = person.getCity();
        if (StringUtils.isNotBlank(city)) {
            query.addCriteria(Criteria.where("addresses.city").is(city));
        }
        return query;
    }

}
