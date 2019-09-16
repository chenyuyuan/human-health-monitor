package com.humanhealthmonitor.pojo;

import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

public class AdminLog {

    private int adminLogId;//管理员日志ID
    private String adminId;//管理员ID
    private String logType;//日志类型
    private Timestamp writeTime;//写入时间
    private String detail;//详细信息

    public int getAdminLogId() {
        return adminLogId;
    }

    public void setAdminLogId(int adminLogId) {
        this.adminLogId = adminLogId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Timestamp getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime) {
        this.writeTime = writeTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
