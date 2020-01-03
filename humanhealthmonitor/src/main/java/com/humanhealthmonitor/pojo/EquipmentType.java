package com.humanhealthmonitor.pojo;

public class EquipmentType {

    private String eqpType;//设备类型
    private String stringMatch;//设备类型对应的设备号匹配字符串
    private int indexTotal;//测量的指标总个数
    private int alarmSerialMax;//警报界限总数
    private String introduction;//该类型设备简介
    private String eqpTypeCHName;//设备中文名


    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public String getStringMatch() {
        return stringMatch;
    }

    public void setStringMatch(String stringMatch) {
        this.stringMatch = stringMatch;
    }

    public int getIndexTotal() {
        return indexTotal;
    }

    public void setIndexTotal(int indexTotal) {
        this.indexTotal = indexTotal;
    }

    public int getAlarmSerialMax() {
        return alarmSerialMax;
    }

    public void setAlarmSerialMax(int alarmSerialMax) {
        this.alarmSerialMax = alarmSerialMax;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getEqpTypeCHName() {
        return eqpTypeCHName;
    }

    public void setEqpTypeCHName(String eqpTypeCHName) {
        this.eqpTypeCHName = eqpTypeCHName;
    }
}
