package com.humanhealthmonitor;

import java.math.BigInteger;

import java.util.*;

import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) {
        byte[] b = new byte[2];
        b[0] = 3;
        b[1] = 127;
        String s = byteArrayToString(b,2);
        System.out.println(s);
    }
}