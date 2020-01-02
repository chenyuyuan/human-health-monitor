package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.EquipmentMapper;
import com.humanhealthmonitor.pojo.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    //添加监测设备
    public int insertEquipment(Equipment equipment) {
        if (queryEquipmentByEqpId(equipment.getEqpId()) == null) {
            return equipmentMapper.insertEquipment(equipment);
        }
        else {
            return -1;
        }
    }

    public int deleteEquipmentByDeviceID(String deviceID) {
        return equipmentMapper.deleteEquipmentBydeviceID(deviceID);
    }



    //监测设备更换绑定人并修改设备名称
    public int updateEquipmentObject(Equipment equipment) {
        return equipmentMapper.updateEquipmentObject(equipment);
    }

    //监测设备更换绑定人
    public int updateEquipmentOnlyObject(Equipment equipment) {
        return equipmentMapper.updateEquipmentOnlyObject(equipment);
    }

    //监测设备名称修改
    public int updateEquipmentName(Equipment equipment) {
        return equipmentMapper.updateEquipmentName(equipment);
    }

    //监测设备特殊警报值启动或者关闭
    public int updateEquipmentSpecial(Equipment equipment) {
        return equipmentMapper.updateEquipmentSpecial(equipment);
    }

    //监测设备类型更新（主要为添加新设备所用）
    public int updateEquipmentType(Equipment equipment) {
        return equipmentMapper.updateEquipmentType(equipment);
    }

    //监测设备网关号更新
    public int updateEquipmentNetMaskId(Equipment equipment) {
        return equipmentMapper.updateEquipmentNetMaskId(equipment);
    }

    //监测设备（在网关的）顺序号更新
    public int updateEquipmentDeviceSerial(Equipment equipment) {
        return equipmentMapper.updateEquipmentDeviceSerial(equipment);
    }

    //查询用户关联的所有监测对象的所有设备信息
    public List<Equipment> queryAllEquipmentByUserId(String userId) {
        return equipmentMapper.queryAllEquipmentByUserId(userId);
    }

    //查询某监测对象所有绑定的监测设备信息
    public List<Equipment> queryAllEquipmentByObjectId(String objectId) {
        return equipmentMapper.queryAllEquipmentByObjectId(objectId);
    }

    //查询某个监测设备所有信息
    public Equipment queryEquipmentByEqpId(String eqpId) {
        return equipmentMapper.queryEquipmentByEqpId(eqpId);
    }

    //根据netmaskId和deviceSerial查询设备信息
    public Equipment queryEquipmentByNetSerial(int netmaskId, int deviceSerial) {
        return equipmentMapper.queryEquipmentByNetSerial(netmaskId,deviceSerial);
    }




    //查询绑定用户名
    public String queryBindUserNameByObjectId(String objectId) {
        return equipmentMapper.queryBindUserNameByObjectId(objectId);
    }






    public int querySpecialValueByEqpId(String eqpId) {
        Equipment equipment = equipmentMapper.querySpecialValueByEqpId(eqpId);
        if(equipment==null) {
            return -1;
        }
        else {
            if(equipment.getSpecial()) //special是boolean
                return 1;
            else {
                return 0;
            }
        }

    }

}
