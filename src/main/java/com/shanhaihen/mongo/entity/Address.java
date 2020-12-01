package com.shanhaihen.mongo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address  implements Serializable {
    private static final long serialVersionUID = 7195659940771084987L;
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
