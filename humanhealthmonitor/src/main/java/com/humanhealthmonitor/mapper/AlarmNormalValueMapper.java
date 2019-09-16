package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.AlarmNormalValue;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmNormalValueMapper {

    //插入默认警报值，valueId自增
    @Insert("insert into alarmNormalValue(valueName,eqpType,indexSeq,serial,valueType,value,measurementUnit) " +
            "values(#{valueName},#{eqpType},#{indexSeq},#{serial},#{valueType},#{value},#{measurementUnit})")
    int insertAlarmNormalValue(AlarmNormalValue alarmNormalValue);

    //更改默认警报值
    @Update("update alarmNormalValue set value=#{value} where eqpType=#{eqpType} and serial=#{serial}")
    int updateAlarmNormalValue(AlarmNormalValue alarmNormalValue);

    //根据设备类型和serial查询默认警报值
    @Select("select * from alarmNormalValue where eqpType=#{eqpType} and serial=#{serial}")
    AlarmNormalValue queryAlarmNormalValueBySerial(@Param("eqpType") String eqpType, @Param("serial") int serial);

    //根据设备类型查询所有默认警报值
    @Select("select * from alarmNormalValue where eqpType=#{eqpType} order by serial ASC")
    List<AlarmNormalValue> queryAlarmNormalValueByEqpType(String eqpType);

}
