package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.ObjectLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectLogMapper {
    //插入监测对象日志,objectLogId自增
    @Insert("insert into objectLog (objectId,logType,writeTime,detail) values (#{objectId},#{logType},#{writeTime},#{detail})")
    int insertObjectLog(ObjectLog objectLog);

    //根据objectId查询日志
    @Select("select * from objectLog where objectId=#{objectId}")
    List<ObjectLog> queryObjectLogByObjectId(String objectId);

}
