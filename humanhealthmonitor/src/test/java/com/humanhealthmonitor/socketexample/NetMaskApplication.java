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
    private int a = 10;

    private static String getResponse(String s) {
        s = s.toLowerCase();
        // order 1
        if(s.equals("fefe040101aabb")) {
            System.out.println("order type 1 return");
            return "fefe0103000202aabb";
        }
        // order 2
        if(s.equals("fefe07020a0006030218aabb")) {
            System.out.println("order type 1 return");
            return "fefe02060a0006030118aabb";
        }
        //add if below


        return "fefe1122334455aabb";
    }

    public void run() {

        try {
            //socket = new Socket("140.143.232.52", 14900);
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
