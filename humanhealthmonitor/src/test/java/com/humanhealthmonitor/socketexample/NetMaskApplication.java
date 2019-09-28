package com.humanhealthmonitor.socketexample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.humanhealthmonitor.util.ByteUtils.*;

public class NetMaskApplication implements Runnable {
    public NetMaskApplication() {}

    public static int count = 1;
    Socket socket;
    InputStream din;
    OutputStream dout;

    public static String getResponse(String s) {
        s = s.toLowerCase();
        if(s.equals("fefe0400010001aabb")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return "fefe0103000202aabb";
        }
        return "fefe1122334455aabb";
    }

    public void run() {
        while (true) {
            try {
                Socket socket = new Socket("140.143.232.52", 14900);
                System.out.println("Waiting for message from cloud......");
                System.out.println("Connected!");

                InputStream din = socket.getInputStream();
                OutputStream dout = socket.getOutputStream();

                byte[] bytes = new byte[1];
                String info = "";
                List<Byte> byteArrayList = new ArrayList<>();

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
                //网关返回数据
                byte[] receiveByteArray = new byte[byteArrayList.size()];
                for (int i = 0; i < byteArrayList.size(); ++i) {
                    receiveByteArray[i] = byteArrayList.get(i);
                }
                String receiveMessage = byteArrayToString(receiveByteArray, 16);
                System.out.println("rec: " + receiveMessage);
                String sendMessage = getResponse(receiveMessage);
                byte[] sendMessageByteArray = toByteArray(sendMessage);
                dout.write(sendMessageByteArray);
                dout.flush();

                din.close();
                dout.close();
                count++;
                Thread.sleep(30000);

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
