package com.humanhealthmonitor.pojo;

import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

public class ObjectLog {
    private long objectLogId;//监测对象日志ID
    private String objectId;//对象ID
    private String logType;//日志类型
    private Timestamp writeTime;//写入时间
    private String detail;//详情

    public long getObjectLogId() {
        return objectLogId;
    }

    public void setObjectLogId(long objectLogId) {
        this.objectLogId = objectLogId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
