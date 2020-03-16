package com.humanhealthmonitor;

import com.github.qcloudsms.httpclient.HTTPException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) throws Exception {

        long timestamp = System.currentTimeMillis() / 1000;
        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMdd"); //设置格式
        String timeinformat = format.format(Long.parseLong(timestamp + "000"));

        System.out.println(java.sql.Date.valueOf(timeinformat));
    }

}