package com.humanhealthmonitor.pojo;

import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

public class AlarmLog {

    private long alarmLogId;//警报ID
    private String objectId;//对象ID
    private String eqpId;//设备ID
    private String alarmType;//警报类型
    private float alarmValue;//警报值
    private Timestamp writeTime;//写入时间
    private String detail;//详情

    public long getAlarmLogId() {
        return alarmLogId;
    }

    public void setAlarmLogId(long alarmLogId) {
        this.alarmLogId = alarmLogId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public float getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(float alarmValue) {
        this.alarmValue = alarmValue;
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
