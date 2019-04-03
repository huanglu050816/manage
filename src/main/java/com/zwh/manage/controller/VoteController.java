/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.controller;

import com.zwh.manage.exception.InnerException;
import com.zwh.manage.form.BaseInfoForm;
import com.zwh.manage.form.MemberForm;
import com.zwh.manage.form.VoteForm;
import com.zwh.manage.model.ResponseResult;
import com.zwh.manage.service.VoteService;
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

/**
 *
 */
@RequestMapping("vote")
@RestController
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("member")
    public ResponseResult submit(@Validated MemberForm form, BindingResult bindingResult) {
        ValidatorUtil.parseValidateResult(bindingResult);
        voteService.insertMember(form);
        return new ResponseResult(200, "提交成功");
    }

    @PostMapping("submit")
    public ResponseResult submit(@Validated VoteForm form, BindingResult bindingResult) {
        ValidatorUtil.parseValidateResult(bindingResult);
        voteService.vote(form);
        return new ResponseResult(200, "提交成功");
    }

    @GetMapping("number")
    public ResponseResult getCount() {
        return new ResponseResult(200, "查询成功", voteService.getNumbers());
    }

    @GetMapping("members")
    public ResponseResult getList() {
        return new ResponseResult(200, "查询成功", voteService.getMemberList());
    }

    @GetMapping("export")
    public void export(String token, HttpServletResponse response) {
        try {
            if (!"zwh123456".equals(token)) {
                throw new InnerException(400, "token错误");
            }
            voteService.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
