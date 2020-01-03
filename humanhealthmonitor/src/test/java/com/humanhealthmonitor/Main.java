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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

public class Main{
    public static void main(String[] args) throws Exception {

        cutImage("C:\\Users\\Cyuyuan\\Desktop\\totoro1.jpg",0,0,200,100);

    }
    private static void cutImage(String filePath, int x, int y, int w, int h)
            throws Exception {
        ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(filePath));
        @SuppressWarnings("rawtypes")
        Iterator it = ImageIO.getImageReaders(iis);
        ImageReader imagereader = (ImageReader) it.next();
        imagereader.setInput(iis);
        ImageReadParam par = imagereader.getDefaultReadParam();
        par.setSourceRegion(new Rectangle(x, y, w, h));
        BufferedImage bi = imagereader.read(0, par);
        ImageIO.write(bi, "jpg", new File(filePath));
    }
}