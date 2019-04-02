/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.model;

import lombok.Data;

/**
 * 返回结果实体
 */
@Data
public class ResponseResult {

    private int status;

    private String msg;

    private Object data;

    public ResponseResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseResult(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

}
