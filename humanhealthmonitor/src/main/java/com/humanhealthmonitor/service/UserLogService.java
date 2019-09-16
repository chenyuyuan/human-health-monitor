package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.UserLogMapper;
import com.humanhealthmonitor.pojo.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;

    //插入用户日志，userLogId自增，设为null
    public int insertUserLog(UserLog userLog) {
        return userLogMapper.insertUserLog(userLog);
    }

    //根据userId查询用户日志
    public List<UserLog> queryUserLogByUserId(String userId) {
        return userLogMapper.queryUserLogByUserId(userId);
    }
}
