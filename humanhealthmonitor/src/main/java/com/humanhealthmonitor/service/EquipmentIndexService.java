package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.EquipmentIndexMapper;
import com.humanhealthmonitor.pojo.EquipmentIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentIndexService {
    @Autowired
    private EquipmentIndexMapper equipmentIndexMapper;

    //添加监测设备指标
    public int insertEquipmentIndex(EquipmentIndex equipmentIndex) {
        return equipmentIndexMapper.insertEquipmentIndex(equipmentIndex);
    }

    //查询某一类型设备全部的指标信息
    public List<EquipmentIndex> queryAllEquipmentIndexByEqpType(String eqpType) {
        return equipmentIndexMapper.queryAllEquipmentIndexByEqpType(eqpType);
    }

    //根据设备类型和指标序号查询指标所有信息
    public EquipmentIndex queryEquipmentIndexByEqpTypeAndIndexSeq(String eqpType, int indexSeq) {
        return equipmentIndexMapper.queryEquipmentIndexByEqpTypeAndIndexSeq(eqpType, indexSeq);
    }

}
