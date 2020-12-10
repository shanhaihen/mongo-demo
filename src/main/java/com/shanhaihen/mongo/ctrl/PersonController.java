package com.shanhaihen.mongo.ctrl;

import com.shanhaihen.mongo.entity.Person;
import com.shanhaihen.mongo.entity.ResponseUtil;
import com.shanhaihen.mongo.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    IPersonService iPersonService;

    @RequestMapping("/query")
    @ResponseBody
    public String queryPerson(){
        return "mongo";
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResponseUtil getOrder(@RequestBody Person person){
        iPersonService.add(person);
        return ResponseUtil.success();
    }


}
