package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.EquipmentType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentTypeMapper {

    //插入新的设备类型
    @Insert("insert into equipmentType(eqpType,stringMatch,indexTotal,alarmSerialMax,introduction) " +
            "values(#{eqpType},#{stringMatch},#{indexTotal},#{alarmSerialMax},#{introduction})")
    int insertEquipmentType(EquipmentType equipmentType);

    //更新监测设备类型信息
    @Update("update equipmentType set stringMatch=#{stringMatch}," +
            "indexTotal=#{indexTotal},alarmSerialMax=#{alarmSerialMax},introduction=#{introduction} where eqpType=#{eqpType}")
    int updateEquipmentType(EquipmentType equipmentType);

    //更新监测设备简介
    @Update("update equipmentType set introduction=#{introduction} where eqpType=#{eqpType}")
    int updateEquipmentTypeIntroduction(EquipmentType equipmentType);

    //查询所有监测设备类型的信息
    @Select("select * from equipmentType")
    List<EquipmentType> queryAllEquipmentType();

    //根据eqpType查询某监测设备类型所有信息
    @Select("select * from equipmentType where eqpType=#{eqpType}")
    EquipmentType queryEquipmentTypeByEqpType(String eqpType);

    //根据匹配字符串查询某监测设备类型所有信息
    @Select("select * from equipmentType where stringMatch=#{stringMatch}")
    EquipmentType queryEquipmentTypeByStringMatch(String stringMatch);

    //查询某类型监测设备的指标数量
    @Select("select indexTotal from equipmentType where eqpType=#{eqpType}")
    int queryIndexTotalByEqpType(String eqpType);

    //查询某类型监测设备的警报界值数量
    @Select("select alarmSerialMax from equipmentType where eqpType=#{eqpType}")
    int queryAlarmSerialMaxByEqpType(String eqpType);

}
