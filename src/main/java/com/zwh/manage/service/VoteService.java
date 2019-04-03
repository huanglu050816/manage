/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zwh.manage.exception.InnerException;
import com.zwh.manage.form.MemberForm;
import com.zwh.manage.form.VoteForm;
import com.zwh.manage.mapper.MemberMapper;
import com.zwh.manage.mapper.VoteMapper;
import com.zwh.manage.model.Member;
import com.zwh.manage.model.Vote;
import com.zwh.manage.util.FileUtil;
import com.zwh.manage.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class VoteService {

    private static final String VOTE_PREFIX = "vote:%d:";
    private static final String NUM_PREFIX = "num:";

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private VoteMapper voteMapper;

    public void insertMember(MemberForm form) {
        Member member = new Member();
        BeanUtils.copyProperties(form, member);
        memberMapper.insert(member);
    }

    public List<Member> getMemberList() {
        List<Member> result = new ArrayList<>();
        List<Member> memberList = memberMapper.getList();
        for (Member member : memberList) {
            int id = member.getId();
            String key = String.format(VOTE_PREFIX, id);
            String value = RedisUtil.get(key);
            if (StringUtils.isEmpty(value)) {
                member.setVoteNumber(0);
            } else {
                member.setVoteNumber(Integer.parseInt(value));
            }

            result.add(member);
        }
        return result;
    }

    public int getNumbers() {
        String number = RedisUtil.get(NUM_PREFIX);
        if (StringUtils.isEmpty(number)) {
            RedisUtil.set(NUM_PREFIX, "0");
            return 0;
        } else {
            return Integer.parseInt(number);
        }
    }

    public void vote(VoteForm form) {
        // 检查手机号是否重复
        if (voteMapper.checkMobile(form.getMobile()) > 0) {
            throw new InnerException(400, "手机号码已存在，请勿重复投票");
        }
        Vote vote = new Vote();
        BeanUtils.copyProperties(form, vote);
        JSONArray voteArray = JSON.parseArray(form.getVoteInfo());
        // 投票
        for (int i=0;i<voteArray.size();i++) {
            int id = (Integer) voteArray.get(i);
            String key = String.format(VOTE_PREFIX, id);
            RedisUtil.incr(key);
        }
        RedisUtil.incr(NUM_PREFIX);
        // 记录
        voteMapper.insert(vote);
    }

    public void export(HttpServletResponse response) throws IOException {
        List<Vote> voteList = voteMapper.getList();
        List<List<String>> dataList = revertBaseInfoList(voteList);
        FileUtil.exportExcel(dataList, "数据.xls", response);
    }

    /**
     * 按照地区、手机号、线上投资金额，线下投资金额，日期，留言格式整理
     * @param voteList
     * @return
     */
    private List<List<String>> revertBaseInfoList(List<Vote> voteList) {
        List<List<String>> result = new ArrayList<>();
        List<String> firstLine = new ArrayList<>();
        firstLine.add("姓名");
        firstLine.add("手机号");
        firstLine.add("代收本金");
        firstLine.add("代收利息");
        firstLine.add("日期");
        result.add(firstLine);
        voteList.forEach(vote -> {
            List<String> list = new ArrayList<>();
            list.add(vote.getName());
            list.add(vote.getMobile());
            list.add(String.valueOf(vote.getAmount()));
            list.add(String.valueOf(vote.getBenefit()));
            list.add(String.valueOf(vote.getCreateDate()));
            result.add(list);
        });
        return result;
    }

}
