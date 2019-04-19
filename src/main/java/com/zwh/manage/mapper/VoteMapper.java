/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.mapper;

import com.zwh.manage.model.Vote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface VoteMapper {

    @Insert("insert into t_vote (name,mobile,vote_info) values " +
            "(#{obj.name},#{obj.mobile},#{obj.voteInfo})")
    void insert(@Param("obj") Vote vote);

    @Select("select name,mobile,date_format(create_time, '%Y-%m-%d') as create_date " +
            "from t_vote order by id")
    List<Vote> getList();

    @Select("select count(1) from t_vote where mobile=#{mobile}")
    int checkMobile(@Param("mobile") String mobile);

}
