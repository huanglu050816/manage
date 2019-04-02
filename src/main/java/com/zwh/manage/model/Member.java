package com.zwh.manage.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Data
public class Member {

    private int id;
    private String name;
    private String description;
    private int benefit;
    private int amount;
    private int voteNumber;
    private Date createTime;

}
