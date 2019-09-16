package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.AlarmSpecialValueMapper;
import com.humanhealthmonitor.pojo.AlarmSpecialValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmSpecialValueService {
    @Autowired
    private AlarmSpecialValueMapper alarmSpecialValueMapper;

    //插入特殊警报值，valueId自增
    public int insertAlarmSpecialValue(AlarmSpecialValue alarmSpecialValue) {
        return alarmSpecialValueMapper.insertAlarmSpecialValue(alarmSpecialValue);
    }

    //更改特殊警报值
    public int updateAlarmSpecialValue(AlarmSpecialValue alarmSpecialValue) {
        return alarmSpecialValueMapper.updateAlarmSpecialValue(alarmSpecialValue);
    }

    //根据设备Id和serial查询默认警报值
    public AlarmSpecialValue queryAlarmSpecialValueBySerial(String eqpId, int serial) {
        return alarmSpecialValueMapper.queryAlarmSpecialValueBySerial(eqpId, serial);
    }

    //根据设备Id查询所有特殊警报值
    public List<AlarmSpecialValue> queryAlarmSpecialValueByEqpId(String eqpId) {
        return alarmSpecialValueMapper.queryAlarmSpecialValueByEqpId(eqpId);
    }

    //删除指定设备所有的特殊警报值
    public int deleteAlarmSpecialValueByEqpId(String eqpId) {
        return alarmSpecialValueMapper.deleteAlarmSpecialValueByEqpId(eqpId);
    }
}
