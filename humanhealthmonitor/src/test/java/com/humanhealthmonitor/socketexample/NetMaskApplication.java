package com.humanhealthmonitor.socketexample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.humanhealthmonitor.util.ByteUtils.*;
public class NetMaskApplication implements Runnable {
    private NetMaskApplication() {}

    private static int count = 1;
    private Socket socket;
    private InputStream din;
    private OutputStream dout;
    private int a = 1000;

    private static String getResponse(String s) {
        s = s.toLowerCase();
        // order 1
        if(s.equals("fefe"+"02"+"01"+"01aabb")) { //✔
            System.out.println("order type 1 return");
            return "fefe"+"01"+"03"+  "01"+"0001"  +"02"+"aabb"; //✔ // 通讯类型，网关号，校验和
            //**************************************************//
        }
        // order 2 FEFE07020A0006010921AABB
        if(s.equals("fefe"+"07"+"02"+"0a00060109"+"21"+"aabb")) {
            System.out.println("order type 2 return");
            return "fefe"+"02"+"06"+"0a00060109"+"01"+"20"+"aabb"; //添加成功
            //return "fefe"+"02"+"07"+"0a00060309"+"00"+"21"+"aabb"; //添加失败
        }
        // order 2 改
        //fefe07020a00040313aabb
        if(s.equals("fefe"+"08"+"02"   +"04"+"0a000403"+ "00" +  "17"+"aabb")) { //✔
            System.out.println("returned the order type 2 ADD DEVICES");
            return "fefe"+"02"+"06" +  "04"+"0a000403"+"01"  +"16"+"aabb"; //✔ //添加成功
            //return "fefe"+"02"+"07"+"0a00060309"+"00"+"21"+"aabb"; //添加失败
        }
        //fefe07020a00040313aabb //删除设备
        if(s.equals("fefe"+"08"+"02"   +"04"+"0a000403"+ "10" +  "17"+"aabb")) { //✔
            System.out.println("returned the order type 2 DELETE DEVICES!");
            return "fefe"+"02"+"06" +  "04"+"0a000403"+"11"  +"16"+"aabb"; //✔ //添加成功
            //return "fefe"+"02"+"07"+"0a00060309"+"00"+"21"+"aabb"; //添加失败
        }
        // order 3
        if(s.equals("fefe"+"07"+"03"  + "04" + "0a000304" + "18" +"aabb")){ //✔
            System.out.println("return the order type 4");
            return "fefe"+"04"+"11"+"040a000801"+"045dcbc2ab"+"0103"+"0400140027"+"f3"+"aabb"; //✔
        }
        // order 4
        if(s.equals("fefe"+"02" + "04" + "04" +"aabb")){ //✔
            System.out.println("return the order type 4");
            int checkSum = 0;
            Long timestampCurrent = System.currentTimeMillis() / 1000;
            byte[] timeByteArray = new byte[4];
            for(int i=0; i<4; ++i) {
                timeByteArray[3 - i] = (byte)(timestampCurrent % 256);
                checkSum=checkSum + timeByteArray[3-i];
                timestampCurrent = timestampCurrent/256;
            }
            checkSum = (checkSum + 4+10+8+1 + 4 + 1+3 + 4+16+4+32+7)%256;
            String checkSumStr = Integer.toHexString(checkSum).toUpperCase();
            if(checkSumStr.length() == 1) {
                checkSumStr = "0" + checkSumStr;
            }
            String timestamp = byteArrayToString(timeByteArray, 16);
            String returnOrder = "fefe"+"04"+"11"+"040a000801"+ "04"+timestamp+"0103"+"0400140027" +checkSumStr+"aabb"; // ✔
            returnOrder = "fefe0411040a000304045dd3b833010304091f08f763aabb";
            System.out.println("指令3&4返回："+returnOrder);
            return returnOrder;
        }
        //fefe 04 11 040a000801 045dcbc2ab 0103 04 00140027 f3 aa bb
        //add if below


        return "fefe1122334455aabb";
    }

    public void run() {

        try {
            //socket = new Socket("140.143.232.52", 14900);
            //socket = new Socket("101.132.105.38", 14900);
            socket = new Socket("127.0.0.1", 14900);
            System.out.println("Connected!");
            din = socket.getInputStream();
            dout = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (a > 0) {
            try {
                a--;

                byte[] bytes = new byte[1];
                String info = "";
                List<Byte> byteArrayList = new ArrayList<>();

                //receive data
                while (din.read(bytes) != -1) {
                    byteArrayList.add(bytes[0]);
                    System.out.print(bytesToHexString(bytes));
                    if (din.available() == 0) {
                        dout.flush();
                        System.out.print("\n");
                        break;
                    }
                }
                System.out.println("counter: " + count);
                //return data
                byte[] receiveByteArray = new byte[byteArrayList.size()];
                for (int i = 0; i < byteArrayList.size(); ++i)
                    receiveByteArray[i] = byteArrayList.get(i);
                String receiveMessage = byteArrayToString(receiveByteArray, 16);
                System.out.println("rec: " + receiveMessage);
                String sendMessage = getResponse(receiveMessage);
                byte[] sendMessageByteArray = toByteArray(sendMessage);
                dout.write(sendMessageByteArray);
                dout.flush();

                count++;

                Thread.sleep(2000);
                //rerun per 2 second

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        NetMaskApplication netMaskApplication = new NetMaskApplication();
        netMaskApplication.run();
    }
}
