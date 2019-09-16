package com.humanhealthmonitor.pojo;

import java.sql.Date;//e.g.2000-01-01

public class Equipment {

    private String eqpId;//设备ID
    private String objectId;//监测对象ID
    private String eqpName;//设备名称
    private String eqpType;//设备类型
    private boolean special;//false表示使用默认警报值，true表示使用特殊警报值
    private Date registerDate;//注册日期

//    private boolean bindState;//绑定状态，已绑定：1 未绑定：0
//    private float alarmMax1;//警戒值上限1
//    private float alarmMin1;//警戒值下限1
//    private float alarmMax2;//警戒值上限2
//    private float alarmMin2;//警戒值下限2
//    private float alarmMax3;//警戒值上限3
//    private float alarmMin3;//警戒值下限3

    private int netmaskId;//网关id//added 20190416
    private int deviceSerial;//设备在netmaskId网关中的顺序号//added 20190416

    public int getNetmaskId() {
        return netmaskId;
    }

    public void setNetmaskId(int netmaskId) {
        this.netmaskId = netmaskId;
    }

    public int getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(int deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public boolean getSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

//    public boolean getBindState() {
//        return bindState;
//    }
//
//    public void setBindState(boolean bindState) {
//        this.bindState = bindState;
//    }
//
//    public float getAlarmMax1() {
//        return alarmMax1;
//    }
//
//    public void setAlarmMax1(float alarmMax1) {
//        this.alarmMax1 = alarmMax1;
//    }
//
//    public float getAlarmMin1() {
//        return alarmMin1;
//    }
//
//    public void setAlarmMin1(float alarmMin1) {
//        this.alarmMin1 = alarmMin1;
//    }
//
//    public float getAlarmMax2() {
//        return alarmMax2;
//    }
//
//    public void setAlarmMax2(float alarmMax2) {
//        this.alarmMax2 = alarmMax2;
//    }
//
//    public float getAlarmMin2() {
//        return alarmMin2;
//    }
//
//    public void setAlarmMin2(float alarmMin2) {
//        this.alarmMin2 = alarmMin2;
//    }
//
//    public float getAlarmMax3() {
//        return alarmMax3;
//    }
//
//    public void setAlarmMax3(float alarmMax3) {
//        this.alarmMax3 = alarmMax3;
//    }
//
//    public float getAlarmMin3() {
//        return alarmMin3;
//    }
//
//    public void setAlarmMin3(float alarmMin3) {
//        this.alarmMin3 = alarmMin3;
//    }
}
