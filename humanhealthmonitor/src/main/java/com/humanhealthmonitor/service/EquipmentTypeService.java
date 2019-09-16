package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.EquipmentTypeMapper;
import com.humanhealthmonitor.pojo.EquipmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentTypeService {
    @Autowired
    private EquipmentTypeMapper equipmentTypeMapper;

    //插入新的设备类型
    public int insertEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeMapper.insertEquipmentType(equipmentType);
    }

    //更新监测设备类型信息
    public int updateEquipmentType(EquipmentType equipmentType) {
        return equipmentTypeMapper.updateEquipmentType(equipmentType);
    }

    //更新监测设备简介
    public int updateEquipmentTypeIntroduction(EquipmentType equipmentType) {
        return equipmentTypeMapper.updateEquipmentTypeIntroduction(equipmentType);
    }

    //查询所有监测设备类型的信息
    public List<EquipmentType> queryAllEquipmentType() {
        return equipmentTypeMapper.queryAllEquipmentType();
    }

    //查询某监测设备类型所有信息
    public EquipmentType queryEquipmentTypeByEqpType(String eqpType) {
        return equipmentTypeMapper.queryEquipmentTypeByEqpType(eqpType);
    }

    //根据匹配字符串查询某监测设备类型所有信息
    public EquipmentType queryEquipmentTypeByStringMatch(String stringMatch) {
        return equipmentTypeMapper.queryEquipmentTypeByStringMatch(stringMatch);
    }

    //查询某类型监测设备的指标数量
    public int queryIndexTotalByEqpType(String eqpType) {
        return equipmentTypeMapper.queryIndexTotalByEqpType(eqpType);
    }

    //查询某类型监测设备的警报界值数量
    public int queryAlarmSerialMaxByEqpType(String eqpType) {
        return equipmentTypeMapper.queryAlarmSerialMaxByEqpType(eqpType);
    }
}
