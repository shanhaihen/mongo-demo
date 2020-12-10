package com.example.mongodemo;

import com.alibaba.fastjson.JSON;
import com.shanhaihen.mongo.MongoDemoApplication;
import com.shanhaihen.mongo.base.PersonDao;
import com.shanhaihen.mongo.entity.Page;
import com.shanhaihen.mongo.entity.Person;
import com.shanhaihen.mongo.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class PersonTest {

    @Autowired
    private IPersonService iPersonService;

    @Autowired
    private PersonDao personDao;

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
     *   [{"addresses":[{"area":"1001","city":"101","province":"0"},
     *   {"area":"1002","city":"102","province":"12"}],
     *   "age":38,"gender":2,"idNumber":"133109198109182320",
     *   "idType":"1","name":"张10",
     *   "phoneNums":["13210981811","13312098911"]}]
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
     */

    @Test
    public void queryAllRegexListPersonInfo() {
        Query query = new Query();
        //query.addCriteria(Criteria.where("addresses").elemMatch(Criteria.where("province").is("12").andOperator(Criteria.where("city").is("101"))));
        query.addCriteria(Criteria.where("addresses").elemMatch(Criteria.where("province").is("12").andOperator(Criteria.where("city").is("102"))));
        List<Person> personList = personDao.queryList(query);
        System.out.println(JSON.toJSONString(personList));
    }


}
