package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.Equipment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentMapper {

    //添加监测设备；返回值应该是插入的条数，所以这里插入成功的话应该返回1
    @Insert("insert into equipment(eqpId,objectId,eqpName,eqpType,netmaskId,deviceSerial,special,registerDate) " +
            "values(#{eqpId},#{objectId},#{eqpName},#{eqpType},#{netmaskId},#{deviceSerial},#{special},#{registerDate})")
    int insertEquipment(Equipment equipment);


    //Error: alarmLog`, CONSTRAINT `alarmLog_equipment__fk` 要同时修改或者删除AlarmLog表
    //但由于没有用AlarmLog，所以不会出现这个错误，先不管
    @Delete("delete from equipment where eqpId = #{eqpId}")
    int deleteEquipmentBydeviceID(@Param("eqpId") String eqpId);


    //监测设备更换绑定人并修改设备名称
    @Update("update equipment set objectId=#{objectId},eqpName=#{eqpName} where eqpId=#{eqpId}")
    int updateEquipmentObject(Equipment equipment);

    //监测设备更换绑定人
    @Update("update equipment set objectId=#{objectId} where eqpId=#{eqpId}")
    int updateEquipmentOnlyObject(Equipment equipment);


    // 监测设备更换绑定人(yuan)
    @Update("delete from equipment where eqpId=#{eqpId}")
    int deleteEquipmentOnlyObject(@Param("netmaskId") int eqpId);


    //监测设备名称修改
    @Update("update equipment set eqpName=#{eqpName} where eqpId=#{eqpId}")
    int updateEquipmentName(Equipment equipment);

    //监测设备特殊警报值启动或者关闭
    @Update("update equipment set special=#{special} where eqpId=#{eqpId}")
    int updateEquipmentSpecial(Equipment equipment);

    //监测设备类型更新（主要为添加新设备所用）
    @Update("update equipment set eqpType=#{eqpType} where eqpId=#{eqpId}")
    int updateEquipmentType(Equipment equipment);

    //监测设备网关号更新
    @Update("update equipment set netmaskId=#{netmaskId} where eqpId=#{eqpId}")
    int updateEquipmentNetMaskId(Equipment equipment);

    //监测设备（在网关的）顺序号更新
    @Update("update equipment set deviceSerial=#{deviceSerial} where eqpId=#{eqpId}")
    int updateEquipmentDeviceSerial(Equipment equipment);

    //查询用户关联的所有监测对象的所有设备信息
    @Select("select * from equipment where objectId in (select objectId from object where userId = #{userId})")
    List<Equipment> queryAllEquipmentByUserId(String userId);

    //查询某监测对象所有绑定的监测设备
    @Select("select * from equipment where objectId=#{objectId}")
    List<Equipment> queryAllEquipmentByObjectId(String objectId);

    //查询某个监测设备所有信息
    @Select("select * from equipment where eqpId=#{eqpId}")
    Equipment queryEquipmentByEqpId(String eqpId);

    //根据netmaskId和deviceSerial查询设备信息
    @Select("select * from equipment where netmaskId=#{netmaskId} and deviceSerial=#{deviceSerial}")
    Equipment queryEquipmentByNetSerial(@Param("netmaskId") int netmaskId, @Param("deviceSerial") int deviceSerial);

    @Select("SELECT objectName FROM object where objectId = #{objectId}")
    String queryBindUserNameByObjectId(@Param("objectId") String objectId);


    @Select("SELECT special FROM equipment where eqpId = #{eqpId}")
    Equipment querySpecialValueByEqpId(@Param("eqpId") String eqpId);

}
