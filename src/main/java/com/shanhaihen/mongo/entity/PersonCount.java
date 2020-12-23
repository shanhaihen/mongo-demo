package com.shanhaihen.mongo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonCount implements Serializable {
    private String time;
    private String timestamp;
    private Long count;
}

