package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.EquipmentIndex;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentIndexMapper {

    //添加监测设备指标
    @Insert("insert into equipmentIndex(eqpType,indexSeq,indexName,measurementUnit) values(#{eqpType},#{indexSeq},#{indexName},#{measurementUnit})")
    int insertEquipmentIndex(EquipmentIndex equipmentIndex);

    //查询某一类型设备全部的指标信息
    @Select("select * from equipmentIndex where eqpType=#{eqpType} order by indexSeq asc")
    List<EquipmentIndex> queryAllEquipmentIndexByEqpType(String eqpType);

    //根据设备类型和指标序号查询指标所有信息
    @Select("select * from equipmentIndex where eqpType=#{eqpType} and indexSeq=#{indexSeq}")
    EquipmentIndex queryEquipmentIndexByEqpTypeAndIndexSeq(@Param("eqpType") String eqpType, @Param("indexSeq") int indexSeq);
}
