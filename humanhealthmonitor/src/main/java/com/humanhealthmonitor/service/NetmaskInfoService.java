package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.NetmaskInfoMapper;
import com.humanhealthmonitor.pojo.NetmaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetmaskInfoService {
    @Autowired
    private NetmaskInfoMapper netmaskInfoMapper;

    //插入网关信息,netmaskId自增
    public int insertNetmaskInfo(NetmaskInfo netmaskInfo) {
        return netmaskInfoMapper.insertNetmaskInfo(netmaskInfo);
    }

    //更新网关信息
    public int updateNetMaskInfo(NetmaskInfo netmaskInfo) {
        return netmaskInfoMapper.updateNetMaskInfo(netmaskInfo);
    }

    //根据Id查询网关信息
    public NetmaskInfo queryNetMaskInfoByNetMaskId(int netmaskId) {
        return netmaskInfoMapper.queryNetMaskInfoByNetMaskId(netmaskId);
    }

    //根据ipAddress查询网关信息
    public NetmaskInfo queryNetMaskInfoByIpAddress(String ipAddress) {
        return netmaskInfoMapper.queryNetMaskInfoByIpAddress(ipAddress);
    }

    //查询所有网关信息
    public List<NetmaskInfo> queryAllNetMaskInfo() {
        return netmaskInfoMapper.queryAllNetMaskInfo();
    }

    //删除网关
    public int deleteNetMaskInfoByNetMaskId(int netmaskId) {
        return netmaskInfoMapper.deleteNetMaskInfoByNetMaskId(netmaskId);
    }

}
