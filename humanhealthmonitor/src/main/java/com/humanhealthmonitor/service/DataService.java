package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.DataMapper;
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

    public ArrayList<String> queryData(String dataType, int dataCount) {
        if(dataType.equals("Mattress")) {

        }
        else if(dataType.equals("Temperature")) {

        }
        else if(dataType.equals("BloodOxygen")) {

        }
        else if(dataType.equals("BloodPressure")) {

        }


        return new ArrayList<>();
    }

}
