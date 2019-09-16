package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.NetmaskInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetmaskInfoMapper {

    //插入网关信息,netmaskId自增
    @Insert("insert into netmaskInfo(netmaskName,ipAddress,comProtocol,state) values(#{netmaskName},#{ipAddress},#{comProtocol},#{state})")
    int insertNetmaskInfo(NetmaskInfo netmaskInfo);

    //更新网关信息
    @Update("update netmaskInfo set netmaskName=#{netmaskName},ipAddress=#{ipAddress},comProtocol=#{comProtocol},state=#{state} where netmaskId=#{netmaskId}")
    int updateNetMaskInfo(NetmaskInfo netmaskInfo);

    //根据Id查询网关信息
    @Select("select * from netmaskInfo where netmaskId=#{netmaskId}")
    NetmaskInfo queryNetMaskInfoByNetMaskId(int netmaskId);

    //根据ipAddress查询网关信息
    @Select("select * from netmaskInfo where ipAddress=#{ipAddress}")
    NetmaskInfo queryNetMaskInfoByIpAddress(String ipAddress);

    //查询所有网关信息
    @Select("select * from netmaskInfo")
    List<NetmaskInfo> queryAllNetMaskInfo();

    //删除网关
    @Delete("delete from netmaskInfo where netmaskId=#{netymaskId}")
    int deleteNetMaskInfoByNetMaskId(int netmaskId);

}
