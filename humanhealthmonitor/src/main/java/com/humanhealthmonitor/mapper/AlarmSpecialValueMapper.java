package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.AlarmSpecialValue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmSpecialValueMapper {

    //插入特殊警报值，valueId自增
    @Insert("insert into alarmSpecialValue(valueName,eqpId,indexSeq,serial,valueType,value,measurementUnit) " +
            "values(#{valueName},#{eqpId},#{indexSeq},#{serial},#{valueType},#{value},#{measurementUnit})")
    int insertAlarmSpecialValue(AlarmSpecialValue alarmSpecialValue);

    //更改特殊警报值
    @Update("update alarmSpecialValue set value=#{value} where eqpId=#{eqpId} and serial=#{serial}")
    int updateAlarmSpecialValue(AlarmSpecialValue alarmSpecialValue);

    //根据设备Id和serial查询默认警报值
    @Select("select * from alarmSpecialValue where eqpId=#{eqpId} and serial=#{serial}")
    AlarmSpecialValue queryAlarmSpecialValueBySerial(String eqpId, int serial);

    //根据设备Id查询所有特殊警报值
    @Select("select * from alarmSpecialValue where eqpId=#{eqpId} order by serial ASC")
    List<AlarmSpecialValue> queryAlarmSpecialValueByEqpId(String eqpId);

    //删除指定设备所有的特殊警报值
    @Delete("delete from alarmSpecialValue where eqpId=#{eqpId}")
    int deleteAlarmSpecialValueByEqpId(String eqpId);
}
