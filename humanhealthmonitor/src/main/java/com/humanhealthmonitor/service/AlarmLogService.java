package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.AlarmLogMapper;
import com.humanhealthmonitor.pojo.AlarmLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AlarmLogService {
    @Autowired
    private AlarmLogMapper alarmLogMapper;

    //插入警报信息
    public int insertAlarmLog(AlarmLog alarmLog) {
        return alarmLogMapper.insertAlarmLog(alarmLog);
    }

    //根据objectId查询警报信息
    public List<AlarmLog> queryAlarmLogByObjectId(String objectId) {
        return alarmLogMapper.queryAlarmLogByObjectId(objectId);
    }

    //根据类型查询警报信息
    public List<AlarmLog> queryAlarmLogByAlarmType(String alarmType) {
        return alarmLogMapper.queryAlarmLogByAlarmType(alarmType);
    }

    //查询指定时间内的警报量
    public int queryAlarmLogCountDuringCertainTime( Timestamp beginTime,  Timestamp endTime)
    {
        return alarmLogMapper.queryAlarmLogCountDuringCertainTime(beginTime,endTime);
    }

//    //测试用方法，更新单个记录写入时间
//    public int updateAlarmLogSetWriteTimeById( Timestamp writeTime, int alarmLogId)
//    {
//        return alarmLogMapper.updateAlarmLogSetWriteTimeById(writeTime,alarmLogId);
//    }

//    //测试用方法，更新全部记录写入时间延后一天
//    public int updateAllAlarmLogWriteTime()
//    {
//        return alarmLogMapper.updateAllAlarmLogWriteTime();
//    }
}
