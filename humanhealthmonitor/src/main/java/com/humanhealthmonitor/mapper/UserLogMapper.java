package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.UserLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLogMapper {
    //插入用户日志，userLogId自增
    @Insert("insert into userLog (userId,logType,writeTime,detail) values (#{userId},#{logType},#{writeTime},#{detail})")
    int insertUserLog(UserLog userLog);

    //根据userId查询用户日志
    @Select("select * from userLog where userId=#{userId}")
    List<UserLog> queryUserLogByUserId(String userId);
}
