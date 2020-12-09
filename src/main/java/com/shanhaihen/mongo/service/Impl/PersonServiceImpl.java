package com.shanhaihen.mongo.service.Impl;

import com.shanhaihen.mongo.base.PersonDao;
import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.PersonInfo;
import com.shanhaihen.mongo.service.IPersonService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PersonDao personDao;


    @Override
    public void add(PersonInfo personInfo) {

        mongoTemplate.insert(personInfo);

    }

    @Override
    public List<PersonInfo> query(PersonInfo personInfo) {
        Query query = createScPersonInfoQuery(personInfo);
        return personDao.queryList(query);
    }

    @Override
    public Page<PersonInfo> queryPage(PersonInfo personInfo) {
        Query query = createScPersonInfoQuery(personInfo);
        Integer pageNo = personInfo.getPageNo();
        Integer pageSize = personInfo.getPageSize();
        int start = (pageNo - 1) * pageSize;
        Page<PersonInfo> page = new Page<>(pageNo, pageSize);
        List<PersonInfo> scPersonInfos;
        Long totalSize = 0L;
        if (Objects.nonNull(query)) {
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            query.skip(start);
            query.limit(pageSize);
            query.with(sort);
            scPersonInfos = personDao.queryList(query);
            totalSize = personDao.getPageCount(query);
        } else {
            scPersonInfos = Lists.newArrayList();
        }
        page.setRecords(scPersonInfos);
        page.setTotal(totalSize.intValue());
        return page;
    }

    private Query createScPersonInfoQuery(PersonInfo personInfo) {
        Query query = new Query();

        if (StringUtils.isNotBlank(personInfo.getIdNumber()) && StringUtils.isNotBlank(personInfo.getName())) {
            //mongo or搜索
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("idNumber").regex(".*?" + personInfo.getIdNumber().replace("*", "\\*") + ".*"),
                    Criteria.where("name").regex(".*?" + personInfo.getName().replace("*", "\\*") + ".*")));
        } else if (StringUtils.isNotBlank(personInfo.getName())) {
            query.addCriteria(Criteria.where("name").regex(".*?" + personInfo.getName().replace("*", "\\*") + ".*"));
        } else if (StringUtils.isNotBlank(personInfo.getIdNumber())) {
            query.addCriteria(Criteria.where("idNumber").regex(".*?" + personInfo.getIdNumber().replace("*", "\\*") + ".*"));
        }

        if (Objects.nonNull(personInfo.getGender())) {
            query.addCriteria(Criteria.where("gender").is(personInfo.getGender()));
        }

        //in 查询
        if (Objects.nonNull(personInfo.getPhoneNums())) {
            query.addCriteria(Criteria.where("phoneNums").elemMatch(Criteria.where("$in").is(personInfo.getPhoneNums())));
        }
        //子查询
        String province = personInfo.getProvince();
        if (StringUtils.isNotBlank(province)) {
            query.addCriteria(Criteria.where("addresses.province").is(province));
        }
        String city = personInfo.getCity();
        if (StringUtils.isNotBlank(city)) {
            query.addCriteria(Criteria.where("addresses.city").is(city));
        }
        return query;
    }

}
