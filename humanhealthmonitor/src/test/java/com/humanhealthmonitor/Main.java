package com.humanhealthmonitor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) {
        List<Byte> byteList = new ArrayList<>();
        byteList.add(Byte.parseByte("0"));
        byteList.add(Byte.parseByte("0"));

        byteList.add(Byte.parseByte("-2"));
        byteList.add(Byte.parseByte("-2"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("3"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("0"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("2"));

        byteList.add(Byte.parseByte("-86"));
        byteList.add(Byte.parseByte("-69"));

        byteList.add(Byte.parseByte("99"));

        byteList.add(Byte.parseByte("-2"));
        byteList.add(Byte.parseByte("-2"));
        byteList.add(Byte.parseByte("-2"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("4"));

        byteList.add(Byte.parseByte("-86"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("0"));

        byteList.add(Byte.parseByte("1"));
        byteList.add(Byte.parseByte("2"));

        byteList.add(Byte.parseByte("-86"));
        byteList.add(Byte.parseByte("-69"));

        byteList.add(Byte.parseByte("-2"));


        for(Byte b: byteList) {
            System.out.print(Integer.toHexString(b & 0xFF) + " ");
        }
        System.out.println();

        socketInfoPreProcess(byteList);

    }
    private static void socketInfoPreProcess(List<Byte> byteArrayListPre) {

        System.out.println("<SocketTask:socketinfopreprocess>");

        if(byteArrayListPre.size() < 8) return;
        System.out.println("size(): " + byteArrayListPre.size());
        int low = 0;
        int high = 0;

        while (low < byteArrayListPre.size()) {
            //System.out.println("1 low/high: " + low + " " + high);
            if (byteArrayListPre.get(low) == (byte) 0xFE) {
                if(low + 1 < byteArrayListPre.size() && byteArrayListPre.get(low + 1) == (byte) 0xFE) {
                    high = low + 2;
                    while (high < byteArrayListPre.size()) {
                        //System.out.println("2 low/high: " + low + " " + high);
                        if (byteArrayListPre.get(high) == (byte) 0xAA) {
                            if(high + 1 < byteArrayListPre.size() && byteArrayListPre.get(high + 1) == (byte) 0xBB) {
                                if(low < high) {
                                    List<Byte> byteArrayList = new ArrayList<>();
                                    for(int i = low; i <= high+1; ++i) {
                                        byteArrayList.add(byteArrayListPre.get(i));
                                    }
                                    for(Byte b: byteArrayList) {
                                        System.out.print(Integer.toHexString(b & 0xFF) + " ");
                                    }
                                    System.out.println("<SocketTask:socketinfopreprocess:>" + byteArrayList.size());
                                    low = high + 2 - 1; //后面还有个low++，所以-1
                                    break;
                                }

                            }
                        }
                        high++;
                    }
                }
            }
            low++;
        }


    }
}