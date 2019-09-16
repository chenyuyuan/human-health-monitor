package com.humanhealthmonitor.socketexample;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class NetMaskServerSocket {
    public NetMaskServerSocket() {}

    public byte[] toByteArray(String hexString) {
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

    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }
    public static String getResponse(String s) {
        if(s.equals("FEFE03010501AABB")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return "FEFE0103000202AABB";
        }
        return "00";
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8081);
            System.out.println("Waiting for message from cloud......");
            Socket socket = ss.accept();
            System.out.println("Connected!");

            InputStream din = socket.getInputStream();
            OutputStream dout = socket.getOutputStream();

            //网关接收数据
            int receivedMessageLength = din.read();
            byte[] receivedMessage = new byte[receivedMessageLength];
            din.read(receivedMessage);
            System.out.println("received message: " + byteArrayToString(receivedMessage, 16));

            //网关返回数据
            byte[] responseMessage = toByteArray(getResponse(byteArrayToString(receivedMessage, 16)));
            dout.write(responseMessage.length);
            dout.write(responseMessage);

            dout.flush();
            din.close();
            dout.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        NetMaskServerSocket netMaskServerSocket = new NetMaskServerSocket();
        netMaskServerSocket.run();
    }
}
