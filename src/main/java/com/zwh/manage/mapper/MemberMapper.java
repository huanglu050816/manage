/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.mapper;

import com.zwh.manage.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface MemberMapper {

    @Insert("insert into t_member (name,description,amount,benefit) values " +
            "(#{obj.name},#{obj.description},#{obj.amount},#{obj.benefit})")
    void insert(@Param("obj") Member member);

    @Select("select * from t_member order by id")
    List<Member> getList();

}
