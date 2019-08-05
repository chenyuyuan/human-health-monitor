package com.example.humanhealthmonitor.service;

import com.example.humanhealthmonitor.mapper.ObjectLogMapper;
import com.example.humanhealthmonitor.pojo.ObjectLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectLogService {
    @Autowired
    private ObjectLogMapper objectLogMapper;

    //插入监测对象日志
    public int insertObjectLog(ObjectLog objectLog) {
        return objectLogMapper.insertObjectLog(objectLog);
    }

    //根据objectId查询日志
    public List<ObjectLog> queryObjectLogByObjectId(String objectId) {
        return objectLogMapper.queryObjectLogByObjectId(objectId);
    }
}
