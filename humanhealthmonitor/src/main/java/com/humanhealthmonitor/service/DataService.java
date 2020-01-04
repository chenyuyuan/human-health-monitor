package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.DataMapper;
import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public void insertData(String dataType, ArrayList<Double> data, int netmaskId,String objectId, String eqpId,String sendTime) {
        if(dataType.equals("Mattress")) {
            if(data.size()!=2) return;
            dataMapper.insertMattressData(eqpId,netmaskId,objectId,sendTime,data.get(0),data.get(1));
        }
        else if(dataType.equals("Temperature")) {
            if(data.size()!=2) return;
            dataMapper.insertTemperatureData(eqpId,netmaskId,objectId,sendTime,data.get(0),data.get(1));
        }
        else if(dataType.equals("BloodOxygen")) {
            if(data.size()!=1) return;
            dataMapper.insertBloodOxygenData(eqpId,netmaskId,objectId,sendTime,data.get(0));
        }
        else if(dataType.equals("BloodPressure")) {
            if(data.size()!=3) return;
            dataMapper.insertBloodPressureData(eqpId,netmaskId,objectId,sendTime,data.get(0),data.get(1),data.get(2));
        }
    }

    public ArrayList<Temperature> queryTemperature(String objectId, int dataCount, String limitTime) {
        return dataMapper.queryLastSeveralTemperature(objectId,limitTime,dataCount);
    }
    public ArrayList<Mattress> queryMattress(String objectId, int dataCount, String limitTime) {
        return dataMapper.queryLastSeveralMattress(objectId,limitTime,dataCount);
    }
    public ArrayList<BloodOxygen> queryBloodOxygen(String objectId, int dataCount, String limitTime) {
        return dataMapper.queryLastSeveralBloodOxygen(objectId,limitTime,dataCount);
    }
    public ArrayList<BloodPressure> queryBloodPressure(String objectId, int dataCount, String limitTime) {
        return dataMapper.queryLastSeveralBloodPressure(objectId,limitTime,dataCount);
    }



    public ArrayList<Temperature> queryTemperatureLimitTime(String objectId, String startTime, String endTime) {
        return dataMapper.queryTemperatureLimitTime(objectId,startTime,endTime);
    }
    public ArrayList<Mattress> queryMattressLimitTime(String objectId, String startTime, String endTime) {
        return dataMapper.queryMattressLimitTime(objectId,startTime,endTime);
    }
    public ArrayList<BloodOxygen> queryBloodOxygenLimitTime(String objectId, String startTime, String endTime) {
        return dataMapper.queryBloodOxygenLimitTime(objectId,startTime,endTime);
    }
    public ArrayList<BloodPressure> queryBloodPressureLimitTime(String objectId, String startTime, String endTime) {
        return dataMapper.queryBloodPressureLimitTime(objectId,startTime,endTime);
    }




}
