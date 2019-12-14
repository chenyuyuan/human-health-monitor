package com.humanhealthmonitor.util;

import java.math.BigInteger;

public class ByteUtils {


    public static byte[] toByteArray(String hexString) {
        if (hexString.equals(""))
            throw new IllegalArgumentException("this hexString must not be empty");
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

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
     * 对于b[0] = 1;b[1] = 2; b[2] = 21;十六进制的会转化为20115，所以使用时前面得加个0;
     */
    public static String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }

    public static String toUnsignedBinaryString(byte[] b) {
        String ans = "";
        for(int i = 0;i <b.length; ++i) {
            String str = Integer.toBinaryString((b[i]&0xFF) + 0x100).substring(1);
            ans = str + ans;
        }
        return new StringBuffer(ans).reverse().toString();
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
}
