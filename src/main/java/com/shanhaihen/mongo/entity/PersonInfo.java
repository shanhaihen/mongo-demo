package com.shanhaihen.mongo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据json
 {"name":"张1","gender":1,"idType":1,"idNumber":"133109198109182311","phoneNums":["13210981891","13312098981"],
 "addresses":[{"province":"1","city":"100","area":"1000"},{"province":"11","city":"111","area":"1111"}]}
 {"name":"张2","gender":2,"idType":1,"idNumber":"133109198109182312","phoneNums":["13210981892","13312098982"],
 "addresses":[{"province":"2","city":"200","area":"2000"},{"province":"22","city":"222","area":"2222"}]}
 {"name":"张3","gender":1,"idType":1,"idNumber":"133109198109182313","phoneNums":["13210981893","13312098983"],
 "addresses":[{"province":"3","city":"300","area":"3000"},{"province":"33","city":"333","area":"3333"}]}
 {"name":"张4","gender":1,"idType":1,"idNumber":"133109198109182314","phoneNums":["13210981894","13312098984"],
 "addresses":[{"province":"4","city":"400","area":"4000"},{"province":"44","city":"444","area":"4444"}]}
 {"name":"张5","gender":1,"idType":1,"idNumber":"133109198109182315","phoneNums":["13210981895","13312098985"],
 "addresses":[{"province":"5","city":"500","area":"5000"},{"province":"55","city":"555","area":"5555"}]}
 {"name":"张6","gender":2,"idType":1,"idNumber":"133109198109182316","phoneNums":["13210981896","13312098986"],
 "addresses":[{"province":"6","city":"600","area":"6000"},{"province":"66","city":"666","area":"6666"}]}
 {"name":"张7","gender":2,"idType":1,"idNumber":"133109198109182317","phoneNums":["13210981897","13312098987"],
 "addresses":[{"province":"7","city":"700","area":"7000"},{"province":"77","city":"777","area":"7777"}]}
 {"name":"张8","gender":1,"idType":1,"idNumber":"133109198109182318","phoneNums":["13210981898","13312098988"],
 "addresses":[{"province":"8","city":"800","area":"8000"},{"province":"88","city":"888","area":"8888"}]}
 {"name":"张9","gender":2,"idType":1,"idNumber":"133109198109182319","phoneNums":["13210981899","13312098989"],
 "addresses":[{"province":"9","city":"900","area":"9000"},{"province":"99","city":"99","area":"9999"}]}
 {"name":"张10","gender":2,"idType":1,"idNumber":"133109198109182320","phoneNums":["13210981811","13312098911"],
 "addresses":[{"province":"0","city":"101","area":"1001"},{"province":"12","city":"102","area":"1002"}]}
 *
 */
@Data
public class PersonInfo implements Serializable {
    private static final long serialVersionUID = 446583973455582001L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 1：男 2：女
     */
    private Integer gender;

    /**
     * 证件类型 1身份证
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNumber;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 电话号码
     */
    private List<String> phoneNums;
    /**
     * 地址
     */
    private List<Address> addresses;

    private Integer pageNo;

    private Integer pageSize;

    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;

}
