/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.exception;

/**
 * 平台自定义异常
 */
public class InnerException extends RuntimeException {

    private int status;

    private String message;

    public InnerException(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
