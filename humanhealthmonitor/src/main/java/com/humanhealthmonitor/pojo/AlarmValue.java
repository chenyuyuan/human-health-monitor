package com.humanhealthmonitor.pojo;

public class AlarmValue {
    private String valueName;//这个值的名称，便于理解，例如“体温”
    private int serial;//序列号
    private boolean valueType;//false代表下界限，比下界限值小的时候警报；1代表上界线，比上界限值大的时候报警
    private float Value;//警报值
    private String measurementUnit;//计量单位

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
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

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
