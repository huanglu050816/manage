/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.model;

import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
public class Vote {

    private int id;
    private String name;
    private String mobile;
    private String voteInfo;
    private Date createTime;
    private String createDate;

}
