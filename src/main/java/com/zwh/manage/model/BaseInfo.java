package com.zwh.manage.model;

import lombok.Data;

/**
 * 基本信息
 */
@Data
public class BaseInfo {

    private int id;
    private String location;
    private String mobile;
    private String msg;
    private String createTime;

}
