package com.humanhealthmonitor.pojo;

public class EquipmentIndex {

    private int indexId;//主键，自增
    private String eqpType;//设备类型
    private int indexSeq;//指标序号
    private String indexName;//指标名称
    private String measurementUnit;//单位

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public int getIndexSeq() {
        return indexSeq;
    }

    public void setIndexSeq(int indexSeq) {
        this.indexSeq = indexSeq;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}
