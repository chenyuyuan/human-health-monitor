package com.humanhealthmonitor.pojoInflux;

public class BloodPressure01 {

    private String netmaskId;//网关id
    private String sensorId;//传感器id
    private String objectId;//监测对象id
    private String sendTime;//网关传送该数据的时刻,精确到秒

    private int highPressure;//血压高压
    private int lowPressure;//血压低压
    private int heartRate;//心率


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

    public int getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(int highPressure) {
        this.highPressure = highPressure;
    }

    public int getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(int lowPressure) {
        this.lowPressure = lowPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
