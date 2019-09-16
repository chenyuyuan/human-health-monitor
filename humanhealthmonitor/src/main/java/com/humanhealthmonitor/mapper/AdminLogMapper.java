package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.AdminLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminLogMapper {
    //插入管理员日志，adminLogId自增
    @Insert("insert into adminLog(adminId,logType,writeTime,detail) values (#{adminId},#{logType},#{writeTime},#{detail})")
    int insertAdminLog(AdminLog adminLog);

    //根据管理员id和日志类型查询管理员日志
    @Select("select * from adminLog where adminId=#{adminId} and logType=#{logType}")
    List<AdminLog> selectAdminLogByAdminId(String adminId);

    //删除某一时间之前的管理员日志
    @Delete("delete from adminLog where adminId=#{adminId} and writeTime < #{writeTime}")
    int deleteAdminLogByAdminIdAndWriteTime(AdminLog adminLog);

}
