package com.humanhealthmonitor;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);
        for (int i = 1; i < 3; i++) {
            b = a.add(b);
            a = b.subtract(a);
        }
        System.out.println(b);
    }

}
