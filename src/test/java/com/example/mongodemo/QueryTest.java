package com.example.mongodemo;

import com.alibaba.fastjson.JSON;
import com.shanhaihen.mongo.MongoDemoApplication;
import com.shanhaihen.mongo.entity.Person;
import com.shanhaihen.mongo.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class QueryTest {

    @Autowired
    private IPersonService iPersonService;

    @Test
    public void queryPersonInfo() {
        Person person = new Person();
//        person.setName("张");
//        person.setIdNumber("318");
//        person.setPhoneNums(Arrays.asList("13210981898"));
        /**
         * 这里会进行单个匹配
         * [{"addresses":[{"area":"1001","city":"101","province":"0"},
         * {"area":"1002","city":"102","province":"12"}],
         * "age":38,"gender":2,"idNumber":"133109198109182320",
         * "idType":"1","name":"张10",
         * "phoneNums":["13210981811","13312098911"]}]
         */
        person.setProvince("12");
        person.setCity("101");
        List<Person> query = iPersonService.query(person);
        System.out.println(JSON.toJSONString(query));
    }


}
