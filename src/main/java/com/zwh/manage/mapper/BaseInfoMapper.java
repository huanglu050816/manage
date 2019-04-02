package com.zwh.manage.mapper;

import com.zwh.manage.model.BaseInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface BaseInfoMapper {

    @Insert("insert into t_base_info (location,online_amount,offline_amount,mobile,msg) values " +
            "(#{obj.location},#{obj.onlineAmount},#{obj.offlineAmount},#{obj.mobile},#{obj.msg})")
    void insert(@Param("obj") BaseInfo info);

    @Select("select count(1) from t_base_info where mobile=#{mobile}")
    int checkMobile(@Param("mobile") String mobile);

    @Select("select location,online_amount,offline_amount,mobile,msg," +
            "date_format(create_time, '%Y-%m-%d') as create_time from t_base_info order by id")
    List<BaseInfo> getList();

}
