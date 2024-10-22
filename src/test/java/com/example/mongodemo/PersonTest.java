package com.example.mongodemo;

import com.alibaba.fastjson.JSON;
import com.shanhaihen.mongo.MongoDemoApplication;
import com.shanhaihen.mongo.base.PersonDao;
import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.Person;
import com.shanhaihen.mongo.entity.PersonCount;
import com.shanhaihen.mongo.entity.PhoneCount;
import com.shanhaihen.mongo.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PersonTest {

    @Autowired
    private IPersonService iPersonService;

    @Autowired
    private PersonDao personDao;

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 分页查询
     */
    @Test
    public void queryPersonPage() {
        Person person = new Person();
        person.setPageNo(1);
        person.setPageSize(3);
        Page<Person> personPage = iPersonService.queryPage(person);
        System.out.println(JSON.toJSONString(personPage));
    }

    /**
     * 查询列表
     */
    @Test
    public void queryPersonInfo() {
        Person person = new Person();
//        person.setName("张");
//        person.setIdNumber("318");
//        person.setPhoneNums(Arrays.asList("13210981898"));
        /**
         * 这里会进行单个匹配

         */
        person.setProvince("12");
        person.setCity("101");
        List<Person> list = iPersonService.query(person);
        System.out.println(JSON.toJSONString(list));
    }

    /**
     * 子数组，字段单个匹配,分别在两个数组中匹配了对应的字段
     * [{"addresses":[{"area":"1001","city":"101","province":"0"},
     * {"area":"1002","city":"102","province":"12"}],
     * "age":38,"gender":2,"idNumber":"133109198109182320",
     * "idType":"1","name":"张10",
     * "phoneNums":["13210981811","13312098911"]}]
     */
    @Test
    public void queryRegexListPersonInfo() {
        Query query = new Query();
        query.addCriteria(Criteria.where("addresses.province").is("12"));
        query.addCriteria(Criteria.where("addresses.city").is("101"));
        List<Person> personList = personDao.queryList(query);
        System.out.println(JSON.toJSONString(personList));
    }

    /**
     * 子数组，字段全个匹配
     * 注:只支持一个 andOperator
     * 多个使用
     * criteria.andOperator(
     * Criteria.where("city").is("102"),
     * Criteria.where("area").is("11")
     * );
     */
    @Test
    public void queryAllRegexListPersonInfo() {
        Query query = new Query();
        //query.addCriteria(Criteria.where("addresses").elemMatch(Criteria.where("province").is("12").andOperator(Criteria.where("city").is("101"))));
        Criteria criteria = Criteria.where("province").is("12");
        criteria.andOperator(Criteria.where("city").is("102"));
        query.addCriteria(Criteria.where("addresses").elemMatch(criteria));
        List<Person> personList = personDao.queryList(query);
        System.out.println(JSON.toJSONString(personList));

        Query query2 = new Query();
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(Criteria.where("province").is("12"));
        criterias.add(Criteria.where("city").is("102"));
        query2.addCriteria(Criteria.where("addresses").elemMatch(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))));
        List<Person> personList1 = personDao.queryList(query);
        System.out.println(JSON.toJSONString(personList1));
    }

    /**
     * 此方法适用于3.4.5的mongo 数据库
     */
    @Test
    public void phoneCount() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("phoneNums"),
                Aggregation.unwind("phoneNums"),
                Aggregation.group("phoneNums").count().as("count")
        );
        AggregationResults<PhoneCount> aggregate = mongoTemplate.aggregate(agg, "person", PhoneCount.class);
        System.out.println(aggregate.getMappedResults().size());
    }

    @Test
    public void personCount() {
        Criteria criteria = new Criteria();
        /**
         * 时间戳格式化成日期分组
         */
        Aggregation aggregation = newAggregation(
                match(criteria),
                project("timestamp").andExpression("{$dateToString: {date: { $add: {'$timestamp', [0]} }, format: '%Y-%m-%d %H'}}", new Date(28800000)).as("time"),
                group("time").count().as("count"),
                project(bind("time", "_id").and("count")),
                sort(ASC, "time")
        );
//        Aggregation aggregation = newAggregation(
//                match(criteria),
//                project("timestamp"),
//                group("timestamp").count().as("count"),
//                project(bind("timestamp", "_id").and("count"))
//        );
        AggregationResults<PersonCount> result = mongoTemplate.aggregate(aggregation, "person", PersonCount.class);
        List<PersonCount> results = result.getMappedResults();
        System.out.println(results);

    }


}
