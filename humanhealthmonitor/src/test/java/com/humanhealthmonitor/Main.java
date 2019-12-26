package com.humanhealthmonitor;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) throws HTTPException, IOException {
        int time = (int)(System.currentTimeMillis()/1000) - 8*3600;

        CloudMsgUtil cloudMsgUtil = new CloudMsgUtil();
        cloudMsgUtil.sendSingleCloudMsg("17852227655", "yuan", "Hello, yuan!");

        System.out.println(time);


    }
}