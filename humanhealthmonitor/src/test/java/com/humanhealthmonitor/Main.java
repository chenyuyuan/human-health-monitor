package com.humanhealthmonitor;

import java.math.BigInteger;

import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] date = scan.nextLine().split(",");
        int index = 0;
        for(int i = 0; i < date.length; ++i) {
            for(int j = i; j < date.length; ++j) {
                if(less(date[index], date[j])) {
                    index = j;
                }

            }
            String temp = date[index];
            date[index] = date[i];
            date[i] = temp;
        }
        for(int i = 0; i < date.length; ++i) {
            if(i != date.length - 1) {
                System.out.println(date[i] + ",");
            }
            else {
                System.out.println(date[i]);
            }
        }
    }
    public static boolean less (String s1, String s2) {
        String[] cs1 = s1.split(".");
        String[] cs2 = s2.split(".");
        if(Integer.parseInt(cs1[0]) < Integer.parseInt(cs2[0])) {
            return true;
        }
        else if(Integer.parseInt(cs1[0]) == Integer.parseInt(cs2[0])){
            if(Integer.parseInt(cs1[1]) < Integer.parseInt(cs2[1])) {
                return true;
            }
            else if(Integer.parseInt(cs1[1]) == Integer.parseInt(cs2[1])){
                if(Integer.parseInt(cs1[2]) < Integer.parseInt(cs2[2])) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }else {
            return false;
        }
    }
}