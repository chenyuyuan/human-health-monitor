package com.humanhealthmonitor.pojo;

import java.sql.Date;//e.g.2000-01-01

public class ObjectResourceUse {

    private String objectId;//监测对象ID
    private String yearMonth;//年月，与objectId作为联合主键
    private Date beginDate;//开始日期
    private Date endDate;//结束日期
    private int msgCount;//月短信使用量
    private int onlineTimeLength;//在线时长
    private int dataCount;//时间序列数据使用条数

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getOnlineTimeLength() {
        return onlineTimeLength;
    }

    public void setOnlineTimeLength(int onlineTimeLength) {
        this.onlineTimeLength = onlineTimeLength;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}
