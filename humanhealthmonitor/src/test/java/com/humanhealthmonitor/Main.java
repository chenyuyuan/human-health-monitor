package com.humanhealthmonitor;

import java.io.UnsupportedEncodingException;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) {
        String deviceID = "A000304";
        byte[] deviceIDByteArray = stringToByteArray(deviceID);

        for(byte b: deviceIDByteArray) {
            System.out.println(b);
        }

    }
    public static byte[] stringToByteArray(String str) {
        char[] ca = str.toCharArray();
        byte[] res = new byte[(ca.length+1)/2];
        int ires = res.length - 1;
        for (int i = ca.length - 1; i >= 0; i = i - 2) {
            if(i == 0) {
                res[ires] = (byte)charToHex(ca[i]);
            }
            else {
                res[ires] = (byte)((byte)charToHex(ca[i]) + (byte)charToHex(ca[i - 1])*16);
            }
            ires--;
        }

        return res;
    }
    public static int charToHex(char c) {
        if(c>='0' && c<='9') {
            return c - '0';
        }
        else if(c>='a' && c<='z') {
            return c - 'a' + 10;
        }
        else if(c>='A' && c<='Z') {
            return c - 'A' + 10;
        }
        return -1;
    }
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String deviceName = "温度设备";
//        String bindObject = "哈工大威海1号";
//        System.out.print(deviceName + " " + bindObject + "\n");
//
//        byte[] deviceNameByteArray = deviceName.getBytes("utf-8");
//        byte[] bindObjectByteArray = bindObject.getBytes("utf-8");
//        String deviceNameHex = byteArrayToString(deviceNameByteArray,16);
//        deviceNameHex = deviceNameHex.length() % 2 == 1? "0" + deviceNameHex : deviceNameHex;
//        String deviceNameHexLength = deviceNameHex.length()/2 < 16?"0"+Integer.toHexString(deviceNameHex.length()/2): Integer.toHexString(deviceNameHex.length()/2);
//        String bindObjectHex = byteArrayToString(bindObjectByteArray,16);
//        bindObjectHex = bindObjectHex.length() % 2 == 1? "0" + bindObjectHex : bindObjectHex;
//        String bindObjectHexLength = bindObjectHex.length()/2 < 10?"0"+Integer.toHexString(bindObjectHex.length()/2): Integer.toHexString(bindObjectHex.length()/2);
//
//        System.out.println("deviceNameHexLength:" + deviceNameHexLength + "; bindObjectHexLength: " + bindObjectHexLength);
//        System.out.println("deviceNameHex:" + deviceNameHex + "; bindObject: " + bindObjectHex);
//
//        String orderLength = Integer.toHexString(6+1+deviceNameHex.length()/2+1+bindObjectHex.length()/2 +5+1);
//
//        int check = 4 + 10 + 4 + 3 + deviceNameHex.length()/2 + bindObjectHex.length()/2;
//        check = check + 4 + 0x5D + 0x62 + 0x44 + 0x6A;
//
//        for (byte b: deviceNameByteArray) {
//            check = check + b;
//        }
//        for (byte b: bindObjectByteArray) {
//            check = check + b;
//        }
//        check = (check% 256 + 256)%256;
//
//        String order = "FEFE"+orderLength+"07"+"040a000403"+deviceNameHexLength+deviceNameHex+bindObjectHexLength+bindObjectHex+"045D62446A"+Integer.toHexString(check)+"AABB";
//
//        order = order.toUpperCase();
//        System.out.println("发送的指令：" + order);
//    }
}