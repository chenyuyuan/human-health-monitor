package com.humanhealthmonitor;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) throws HTTPException, IOException {

        Double d= 2.45;

        System.out.println(Float.parseFloat(d.toString()));

    }
}