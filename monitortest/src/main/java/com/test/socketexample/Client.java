package com.test.socketexample;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    public Client() {}

    public void run() {
        try {
            // Socket socket = new Socket("140.143.232.52", 8081);
            Socket socket = new Socket("127.0.0.1", 8081);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream din = new DataInputStream(in);
            DataOutputStream dout = new DataOutputStream(out);
            String s = din.readUTF();
            System.out.println(s);
            dout.writeUTF("cyuyuan");
            dout.writeUTF("hello, world!");
            in.close();
            out.close();
            din.close();
            dout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}