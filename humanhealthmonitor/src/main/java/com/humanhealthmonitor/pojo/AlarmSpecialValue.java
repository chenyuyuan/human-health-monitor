package com.humanhealthmonitor.pojo;

public class AlarmSpecialValue {

    private int valueId;//id
    private String valueName;//这个值的名称，便于理解，例如“体温”
    private String eqpId;//设备id，采自监测设备表
    private int indexSeq;//设备类型指标内部序号
    private int serial;//序列号
    private boolean valueType;//false代表下界限，比下界限值小的时候警报；1代表上界线，比上界限值大的时候报警
    //    private float specialValue;//特殊警报值
    private float value;//特殊警报值
    private String measurementUnit;//计量单位

    public int getValueId() {
        return valueId;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    public int getIndexSeq() {
        return indexSeq;
    }

    public void setIndexSeq(int indexSeq) {
        this.indexSeq = indexSeq;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public boolean isValueType() {
        return valueType;
    }

    public void setValueType(boolean valueType) {
        this.valueType = valueType;
    }

//    public float getSpecialValue() {
//        return specialValue;
//    }
//
//    public void setSpecialValue(float specialValue) {
//        this.specialValue = specialValue;
//    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
