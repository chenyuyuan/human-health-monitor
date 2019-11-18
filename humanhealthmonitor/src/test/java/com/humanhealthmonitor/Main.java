package com.humanhealthmonitor;

import java.math.BigInteger;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;
import static com.humanhealthmonitor.util.ByteUtils.bytesToHexString;

public class Main{
    public static void main(String[] args) {
        String timestamp = "1573634731000";
        String time16 = "5dcbc2ab";
        Long timestampCurrent = System.currentTimeMillis() / 1000;
        byte[] timeByteArray = new byte[4];
        for(int i=0; i<4; ++i) {
            timeByteArray[3 - i] = (byte)(timestampCurrent % 256);
            timestampCurrent = timestampCurrent/256;
        }

        System.out.println(bytesToHexString(timeByteArray));
        System.out.println("现在的时间戳" + System.currentTimeMillis() / 1000);
        System.out.println(byteArrayToString(timeByteArray, 16));
    }
}