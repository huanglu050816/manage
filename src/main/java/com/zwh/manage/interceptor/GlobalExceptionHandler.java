package com.zwh.manage.interceptor;

import com.zwh.manage.exception.InnerException;
import com.zwh.manage.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常拦截
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = InnerException.class)
    public ResponseResult modelExceptionHandler(HttpServletRequest req, InnerException e) {
        log.error("request " + req.getRequestURL() + " error:", e);
        return new ResponseResult(e.getStatus(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult modelExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("request " + req.getRequestURL() + " error:", e);
        return new ResponseResult(500, "系统错误");
    }

}
