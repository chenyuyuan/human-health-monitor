package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.AlarmNormalValueMapper;
import com.humanhealthmonitor.pojo.AlarmNormalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmNormalValueService {
    @Autowired
    private AlarmNormalValueMapper alarmNormalValueMapper;

    //插入默认警报值
    public int insertAlarmNormalValue(AlarmNormalValue alarmNormalValue) {
        return alarmNormalValueMapper.insertAlarmNormalValue(alarmNormalValue);
    }

    //更改默认警报值
    public int updateAlarmNormalValueDefault(AlarmNormalValue alarmNormalValue) {
        return alarmNormalValueMapper.updateAlarmNormalValue(alarmNormalValue);
    }

    //根据设备类型和serial查询默认警报值
    public AlarmNormalValue queryAlarmNormalValueBySerial(String eqpType, int serial) {
        return alarmNormalValueMapper.queryAlarmNormalValueBySerial(eqpType, serial);
    }

    //根据设备类型查询所有默认警报值
    public List<AlarmNormalValue> queryAlarmNormalValueByEqpType(String eqpType) {
        return alarmNormalValueMapper.queryAlarmNormalValueByEqpType(eqpType);
    }
}
