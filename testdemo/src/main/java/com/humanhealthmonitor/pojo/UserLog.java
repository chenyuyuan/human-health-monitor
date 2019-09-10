package com.humanhealthmonitor.pojo;

import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

public class UserLog {
    private long userLogId;//用户日志ID
    private String userId;//用户ID
    private String logType;//日志类型，包含登录、登出等
    private Timestamp writeTime;//写入时间
    private String detail;//详情

    public long getUserLogId() {
        return userLogId;
    }

    public void setUserLogId(long userLogId) {
        this.userLogId = userLogId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
