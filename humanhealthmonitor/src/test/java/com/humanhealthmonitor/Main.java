package com.humanhealthmonitor;

import java.math.BigInteger;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) {
        String timestamp = "1573634731000";
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
        String timeinformat = format.format(Long.parseLong(timestamp));
        System.out.println(timeinformat);
    }
}