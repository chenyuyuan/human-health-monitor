package com.test.socketexample;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class CloudSocket {
    public CloudSocket() {}

    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }

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

    public void run() {
        try {
            // Socket socket = new Socket("140.143.232.52", 8081);
            Socket socket = new Socket("127.0.0.1", 8081);
            InputStream din = socket.getInputStream();
            OutputStream dout = socket.getOutputStream();

            //发送数据
            String sendMessage = "FEFE03010501AABB";
            byte[] sendMessageByteArray = toByteArray(sendMessage);
            dout.write(sendMessageByteArray.length);
            dout.write(sendMessageByteArray);
            System.out.println("send message: " + sendMessage);

            //接收数据
            int receivedMessageLength = din.read();
            byte[] receivedMessage = new byte[receivedMessageLength];
            din.read(receivedMessage);
            System.out.println("received message length: " + receivedMessageLength);
            System.out.println("received message: " + byteArrayToString(receivedMessage, 16));

            dout.flush();
            din.close();
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CloudSocket cloudServerSocket = new CloudSocket();
        cloudServerSocket.run();
    }
}
