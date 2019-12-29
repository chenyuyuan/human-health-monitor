package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service

public class DataService {

    @Autowired
    private DataMapper dataMapper;

    public void insertData(String dataType, ArrayList<Integer> data, String sendTime) {

    }

}
