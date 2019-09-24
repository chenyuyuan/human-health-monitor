package com.humanhealthmonitor.util;

import java.math.BigInteger;

public class ByteUtils {
    //字节转为16进制字符串，如“FE”
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //将1个字节的8个位解析成无符号0-255的值
    public static int byteToUnsignedValue(Byte b) {
        int bInt = (int) b;
        if (bInt >= 0) {
            return bInt;
        } else {
            return (bInt + 256);
        }
    }
    //int转为两位16进制字符串
    public static String byteToHexStringSocketTask(Byte b) {
        int bInt = byteToUnsignedValue(b);
        String str = Integer.toHexString(bInt);
        if(str.length()==1) {
            str = "0" + str;
        }
        return str;
    }
    public static int byteArrayToInt (byte[] byteArray, int start, int end) {
        if(byteArray == null || byteArray.length == 0 || start > end || start < 0 || end >= byteArray.length) return -1;
        int res = 0;
        byte[] a = new byte[4];
        int i = a.length - 1, j = byteArray.length - 1;
        for (; i >= 0; --i, --j) {
            if(j >= 0)
                a[i] = byteArray[j];
            else
                a[i] = 0;
        }
        int v0 = (a[0] & 0xff) << 24;
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff) << 0;

        return v0 + v1 + v2 + v3;
    }
    /**
     * 将byte[]转为各种进制的字符串
     * @param radix 基数可以转换进制的范围(2-36)，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }
}
