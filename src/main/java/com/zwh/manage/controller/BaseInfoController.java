package com.zwh.manage.controller;

import com.zwh.manage.exception.InnerException;
import com.zwh.manage.form.BaseInfoForm;
import com.zwh.manage.model.ResponseResult;
import com.zwh.manage.service.BaseInfoService;
import com.zwh.manage.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 *
 */
@RequestMapping("")
@RestController
public class BaseInfoController {

    @Autowired
    private BaseInfoService baseInfoService;

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        return "hello";
    }

    @PostMapping("submit")
    public ResponseResult submit(@Validated BaseInfoForm form, BindingResult bindingResult) {
        ValidatorUtil.parseValidateResult(bindingResult);
        baseInfoService.submit(form);
        return new ResponseResult(200, "提交成功");
    }

    @GetMapping("export")
    public void export(String token, HttpServletResponse response) {
        try {
            if (!"zwh123456".equals(token)) {
                throw new InnerException(400, "token错误");
            }
            baseInfoService.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
