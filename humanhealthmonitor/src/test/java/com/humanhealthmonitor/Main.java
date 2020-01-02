package com.humanhealthmonitor;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) throws HTTPException, IOException {

        int a = 230;
        a=(a+256)%256;
        System.out.println(a);

    }
}