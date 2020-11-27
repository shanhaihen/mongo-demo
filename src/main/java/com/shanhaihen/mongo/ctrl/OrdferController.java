package com.shanhaihen.mongo.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdferController {

    @RequestMapping("/query")
    @ResponseBody
    public String getOrder(){
        return "mongo";
    }

}
