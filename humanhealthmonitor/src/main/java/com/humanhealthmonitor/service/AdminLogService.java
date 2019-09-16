package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.AdminLogMapper;
import com.humanhealthmonitor.pojo.AdminLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogService {
    @Autowired
    private AdminLogMapper adminLogMapper;

    //插入管理员日志
    public int insertAdminLog(AdminLog adminLog) {
        return adminLogMapper.insertAdminLog(adminLog);
    }

    //根据管理员id和日志类型查询管理员日志
    public List<AdminLog> selectAdminLogByAdminId(String adminId) {
        return adminLogMapper.selectAdminLogByAdminId(adminId);
    }

    //删除某一时间之前的管理员日志
    public int deleteAdminLogByAdminIdAndWriteTime(AdminLog adminLog) {
        return adminLogMapper.deleteAdminLogByAdminIdAndWriteTime(adminLog);
    }
}
