package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.AlarmLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Timestamp;

@Repository
public interface AlarmLogMapper {

    //插入警报信息
    @Insert("insert into alarmLog(objectId,eqpId,alarmType,alarmValue,writeTime,detail) " +
            "values(#{objectId},#{eqpId},#{alarmType},#{alarmValue},#{writeTime},#{detail})")
    int insertAlarmLog(AlarmLog alarmLog);

    //根据objectId查询警报信息
    @Select("select * from alarmLog where objectId=#{objectId}")
    List<AlarmLog> queryAlarmLogByObjectId(String objectId);

    //根据类型查询警报信息
    @Select("select * from alarmLog where alarmType=#{alarmType}")
    List<AlarmLog> queryAlarmLogByAlarmType(String alarmType);

    //查询指定时间内的警报量
    @Select("select count(*) from alarmLog where writeTime > #{beginTime} and writeTime < #{endTime}")
    int queryAlarmLogCountDuringCertainTime(@Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);

//    //测试用方法，更新单个记录写入时间
//    @Update("update alarmLog set writeTime=#{writeTime} where alarmLogId=#{alarmLogId}")
//    int updateAlarmLogSetWriteTimeById(@Param("writeTime") Timestamp writeTime, @Param("alarmLogId") int alarmLogId);

//    //测试用方法，更新全部记录写入时间延后一天
//    @Update("update alarmLog set writeTime=(writeTime+86400)")
//    int updateAllAlarmLogWriteTime();

}
