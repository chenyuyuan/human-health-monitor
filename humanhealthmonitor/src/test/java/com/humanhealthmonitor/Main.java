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

        byte[] b2 = {-2,-126};
        System.out.println(toUnsignedBinaryString(b2));
    }
    public static String toUnsignedBinaryString(byte[] b) {
        String ans = "";
        for(int i = 0;i <b.length; ++i) {
            String str = Integer.toBinaryString((b[i]&0xFF) + 0x100).substring(1);
            ans = str + ans;
        }
        return new StringBuffer(ans).reverse().toString();
    }
}