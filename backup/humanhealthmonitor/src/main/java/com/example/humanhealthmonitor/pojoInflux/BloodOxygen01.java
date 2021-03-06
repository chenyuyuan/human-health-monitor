package com.example.humanhealthmonitor.pojoInflux;

public class BloodOxygen01 {

    private String netmaskId;//网关id
    private String sensorId;//传感器id
    private String objectId;//监测对象id
    private String sendTime;//网关传送该数据的时刻,精确到秒

    private int spo2;//血氧饱和度

    public String getSendTime()
    {
        return this.sendTime;
    }

    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getNetmaskId() {
        return netmaskId;
    }

    public void setNetmaskId(String netmaskId) {
        this.netmaskId = netmaskId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }
}
